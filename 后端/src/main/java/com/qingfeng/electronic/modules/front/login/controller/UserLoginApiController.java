package com.qingfeng.electronic.modules.front.login.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.base.util.utils.ConstantWxPropertiesUtil;
import com.qingfeng.electronic.base.util.utils.HttpClientUtils;
import com.qingfeng.electronic.base.util.utils.JwtHelper;
import com.qingfeng.electronic.modules.front.login.domain.entity.UserInfo;
import com.qingfeng.electronic.modules.front.login.domain.vo.LoginVo;
import com.qingfeng.electronic.modules.front.login.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台用户登录接口
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/21
 */
@Api(tags = "前台用户登录管理")
@RestController
@RequestMapping("/api/login/user")
@Validated
public class UserLoginApiController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "前台用户登录", notes = "前台用户登录")
    @PostMapping
    @Log(title = "用户登录", businessType = BusinessType.SELECT, message = "前台用户登录")
    public Result<Map<String, Object>> userLogin(@RequestBody LoginVo loginVo) {
        Map<String, Object> info = userInfoService.userLogin(loginVo);
        return Result.ok(info);
    }

    @ApiOperation(value = "获取登录验证码", notes = "获取登录验证码")
    @GetMapping("/code")
    @Log(title = "用户登录", businessType = BusinessType.SELECT, message = "获取登楼验证码")
    public Result getCode(@RequestParam("account") String account) {
        userInfoService.sendCode(account);
        return Result.ok();
    }

    @ApiOperation(value = "生成微信扫描二维码， 返回微信二维码生成需要的参数", notes = "生成微信扫描二维码， 返回微信二维码生成需要的参数")
    @GetMapping("/getLoginParam")
    public Result getQrConnect() {
        try {
            HashMap<String, Object> map = new HashMap<>(10);
            map.put("appid", ConstantWxPropertiesUtil.WX_OPEN_APP_ID);
            map.put("scope", "snsapi_login");
            map.put("redirect_uri", URLEncoder.encode(ConstantWxPropertiesUtil.WX_OPEN_REDIRECT_URL, "utf-8"));
            map.put("state", System.currentTimeMillis() + "");

            return Result.ok(map);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "回调方法，得到扫描人信息", notes = "回调方法，得到扫描人信息")
    @GetMapping("callback")
    public String callback(String code, String state) {
        //第一步  获取临时票据   code
        System.out.println("code:"+code);
        //第二步  拿着code和微信id和秘钥，请求微信固定地址，得到两个值
        //使用code和appid以及appscrect换取access_token
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");

        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantWxPropertiesUtil.WX_OPEN_APP_ID,
                ConstantWxPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        //使用HttpClient请求这个地址
        try {
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo: "+accessTokenInfo);

            //从返回的字符串中获取两个值 openid 和 access_token
            JSONObject jsonObject = JSONObject.parseObject(accessTokenInfo);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");

            //判断数据库中是否已经存在了微信扫码人的信息
            //根据openid进行判断
            UserInfo userInfo = userInfoService.selectWxInfoOpenId(openid);
            if (ObjectUtil.isNull(userInfo)){
                //数据库中不存在微信信息
                //第三步   拿着openid 和 access_token请求微信地址，得到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String resultInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultInfo："+resultInfo);
                JSONObject resultUserInfoJson = JSONObject.parseObject(resultInfo);

                //用户昵称
                String nickname = resultUserInfoJson.getString("nickname");
                //用户头像路径
                String headimgurl = resultUserInfoJson.getString("headimgurl");

                //获取扫码人的信息添加到数据库中
                userInfo = UserInfo.builder()
                        .nickName(nickname)
                        .openId(openid)
                        .userStatus(1)
                        .headImgUrl(headimgurl)
                        .build();
                userInfo.setNickName(nickname);
                userInfo.setOpenId(openid);
                userInfo.setUserStatus(1);
                userInfoService.save(userInfo);
            }



            //返回name和token字符串
            Map<String, Object> map = new HashMap<>(10);
            String name = userInfo.getName();
            if(StrUtil.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if(StrUtil.isEmpty(name)) {
                name = userInfo.getAccount();
            }
            map.put("name", name);
            //判断userInfo是否有手机号，如果手机号为空，返回openid
            //如果手机号不为空，返回openid值是空的字符串
            //前端判断  如果openid不为空，绑定手机号，如果openid为空，不需要绑定手机号
            if(StrUtil.isEmpty(userInfo.getAccount())) {
                map.put("openid", userInfo.getOpenId());
            } else {
                map.put("openid", "");
            }
            //使用JWT生token的字符串
            String token = JwtHelper.createToken(userInfo.getId(), name);
            map.put("token", token);
            //跳转到前端页面中去
            return "redirect:" + ConstantWxPropertiesUtil.MEDICAL_BASE_URL + "/weixin/callback?token="+map.get("token")+"&openid="+map.get("openid")+"&name="+URLEncoder.encode((String) map.get("name"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
