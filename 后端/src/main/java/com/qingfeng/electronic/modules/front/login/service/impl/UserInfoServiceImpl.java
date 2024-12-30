package com.qingfeng.electronic.modules.front.login.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qcloudsms.SmsSingleSender;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.base.util.utils.ConstantTengPropertiesUtils;
import com.qingfeng.electronic.base.util.utils.EmailUtils;
import com.qingfeng.electronic.base.util.utils.JwtHelper;
import com.qingfeng.electronic.base.util.utils.RandomUtil;
import com.qingfeng.electronic.modules.front.login.dao.UserInfoMapper;
import com.qingfeng.electronic.modules.front.login.domain.dto.UserInfoUpdateDTO;
import com.qingfeng.electronic.modules.front.login.domain.entity.UserInfo;
import com.qingfeng.electronic.modules.front.login.domain.vo.LoginVo;
import com.qingfeng.electronic.modules.front.login.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 患者用户表
 *
 * @author 王淮洋
 * @date 2023-04-21 23:03:12
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 发送验证码，判断是手机号，还是邮箱
     *
     * @param account
     * @return
     */
    @Override
    public void sendCode(String account) {
        //从redis中获取验证码，如果获取到，返回OK
        //key：手机号   value：验证码
        String code = redisTemplate.opsForValue().get(account);
        if (StrUtil.isNotEmpty(code)) {
            return;
        }

        //如果从redis中获取不到，生成验证码，通过整合阿里云短信服务进行发送
        code = RandomUtil.getSixBitRandom();
        System.out.println(code);
        //调用service方法，判断是电话还是邮箱，使用不同的通知方式
        boolean isSend = false;
        if (account.contains("@")) {
            //说明是邮箱
            isSend = this.sendEmail(account, code);
        } else {
            isSend = this.sendCodeByTeng(account, code);
        }


        //生成验证码放到redis里面，设置有效时间
        if (isSend) {
            //将验证码存入redis，5分钟内有效
            redisTemplate.opsForValue().set(account, code, 1, TimeUnit.MINUTES);
        } else {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "验证码发送失败");
        }
    }

    /**
     * 前台用户登录
     *
     * @param loginVo
     * @return
     */
    @Override
    public Map<String, Object> userLogin(LoginVo loginVo) {
        //从loginVo获取输入的手机号 和 验证码(密码)
        String account = loginVo.getAccount();
        String code = loginVo.getCode();

        //判断账号和验证码是否为空
        if (StrUtil.isEmpty(account) || StrUtil.isEmpty(code)) {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "账号或者验证码为空！");
        }

        //判断验证码和输入的验证码是否一致
        String redisCode = redisTemplate.opsForValue().get(account);
        if (StrUtil.isEmpty(redisCode) || !code.equals(redisCode)) {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "验证码输入错误！");
        }

        //绑定账号
        UserInfo userInfo = null;
        if (StrUtil.isNotEmpty(loginVo.getOpenid())) {
            userInfo = this.selectWxInfoOpenId(loginVo.getOpenid());
            if (ObjectUtil.isNotNull(userInfo)) {
                // 设置账号
                userInfo.setAccount(account);
                // 如果是手机号，再存一份
                if (!account.contains("@")) {
                    userInfo.setPhone(account);
                }
                this.updateById(userInfo);
            } else {
                throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "数据异常！");
            }
        }

        /**
         * 如果userinfo为空，进行正常的手机登录
         */
        if (ObjectUtil.isNull(userInfo)) {
            //判断是否是第一次登录：根据手机号或邮箱查询数据库，如果不存在相同手机号就是第一次登录
            LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<UserInfo>();

            if (account.contains("@")) {
                queryWrapper.eq(UserInfo::getAccount, account);
            } else {
                queryWrapper.eq(UserInfo::getPhone, account);
            }
            userInfo = baseMapper.selectOne(queryWrapper);
            //第一次使用这个手机号登录
            if (ObjectUtil.isNull(userInfo)) {
                //添加信息到数据库
                userInfo = UserInfo.builder()
                        .name("")
                        .account(account)
                        .userStatus(1)
                        .build();

                if (!account.contains("@")) {
                    userInfo.setPhone(account);
                }

                baseMapper.insert(userInfo);
            }
        }

        //校验是否被禁用
        if (userInfo.getUserStatus() == 0) {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "用户已禁用！");
        }

        //不是第一次，直接登录   返回登录信息，用户名，token信息
        HashMap<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if (StrUtil.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if (StrUtil.isEmpty(name)) {
            name = userInfo.getAccount();
        }

        map.put("userId", userInfo.getId());
        map.put("name", name);
        //使用JWT生成token字符串
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);

        //返回登录信息
        return map;
    }

    /**
     * 根据openid查询信息是否已经存在
     *
     * @param openid
     * @return
     */
    @Override
    public UserInfo selectWxInfoOpenId(String openid) {
        UserInfo userInfo = baseMapper.selectOne(
                new LambdaQueryWrapper<UserInfo>()
                        .eq(UserInfo::getOpenId, openid)
        );
        return userInfo;
    }

    /**
     * 分页查询用户信息
     *
     * @param userInfoPage
     * @param certificatesType
     * @param authStatus
     * @return
     */
    @Override
    public Page<UserInfo> userInfoPage(Page<UserInfo> userInfoPage, String certificatesType, Integer authStatus) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<UserInfo>()
                .orderByAsc(UserInfo::getAuthStatus);
        if (StrUtil.isNotBlank(certificatesType)) {
            queryWrapper.eq(UserInfo::getCertificatesType, certificatesType);
        }
        if (ObjectUtil.isNotNull(authStatus)) {
            queryWrapper.eq(UserInfo::getAuthStatus, authStatus);
        }
        return baseMapper.selectPage(userInfoPage, queryWrapper);
    }

    /**
     * 使用邮箱发送验证码
     *
     * @param account
     * @param code
     * @return
     */
    private boolean sendEmail(String account, String code) {
        int i = emailUtils.sendEmail(
                account,
                "【塔城地区医院预约挂号服务平台】",
                "你本次登录或注册的验证码是：" + code + "\r\n注意：有效时间1分钟！！！");
        return i == 1 ? true : false;
    }

    /**
     * 使用腾讯元短信服务发送验证码
     *
     * @param account
     * @param code
     * @return
     */
    private boolean sendCodeByTeng(String account, String code) {
        try {
            String[] params = {code, Integer.toString(5)};
            SmsSingleSender sender = new SmsSingleSender(
                    ConstantTengPropertiesUtils.APP_ID,
                    ConstantTengPropertiesUtils.APP_KEY
            );
            //封装发送信息并返回结果
            sender.sendWithParam("86",
                    account,
                    ConstantTengPropertiesUtils.TEMPLATE_ID,
                    params,
                    ConstantTengPropertiesUtils.SMS_SINGE,
                    "",
                    "");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户认证
     *
     * @param userInfoUpdateDTO
     */
    @Override
    public void userAuth(UserInfoUpdateDTO userInfoUpdateDTO) {
        // 对认证信息进行修改
        baseMapper.updateById(dozerUtils.map2(userInfoUpdateDTO, UserInfo.class));

        // 用户认证之后进行消息通知
        String title = "用户认证审核"
                + (userInfoUpdateDTO.getAuthStatus() == 1 ? "通过" : "不通过")
                + "通知";
        String content = "尊敬的用户，您在塔城地区医院预约挂号系统的认证信息已于"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
                + "审核"
                + (userInfoUpdateDTO.getAuthStatus() == 1 ? "通过" : "不通过");

        // 判断账号是邮箱还是手机号
        if (userInfoUpdateDTO.getAccount().contains("@")) {
            // 邮箱
            emailUtils.sendEmail(userInfoUpdateDTO.getAccount(), title, content);
        } else {
            // TODO 手机号，发送短信
        }
    }
}