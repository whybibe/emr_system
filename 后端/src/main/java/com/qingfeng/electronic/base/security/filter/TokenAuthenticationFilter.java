package com.qingfeng.electronic.base.security.filter;

import com.alibaba.fastjson.JSON;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.base.util.utils.JwtHelper;
import com.qingfeng.electronic.base.util.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 认证解析token过滤器
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/4/5
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public static final List<String>  AUTHORITIES_URL = Arrays.asList(
            "/admin/system/index/login",
            "/file",
            "/static",
            "/anno",
            "/import",
            "/excel",
            "/api"
    );

    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("uri:" + request.getRequestURI());
        //如果是登录接口，直接放行
        if (isIgnoreUrls(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (Objects.nonNull(authentication)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");
        logger.info("token:" + token);
        if (!StringUtils.isEmpty(token)) {
            String useruame = JwtHelper.getUsername(token);
            logger.info("useruame:" + useruame);
            if (!StringUtils.isEmpty(useruame)) {
                String authoritiesString = (String) redisTemplate.opsForValue().get(useruame);
                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);

                return new UsernamePasswordAuthenticationToken(
                        useruame,
                        null,
                        mapList.stream()
                                .map(map -> new SimpleGrantedAuthority((String) map.get("authority")))
                                .collect(Collectors.toList())
                );
            }
        }
        return null;
    }

    /**
     * 是忽略 URL
     *
     * @param currentUrl 当前网址
     * @return boolean
     */
    public static boolean isIgnoreUrls(String currentUrl){
        if (AUTHORITIES_URL.isEmpty()) {
            return false;
        }

        return AUTHORITIES_URL.stream()
                .filter(url -> currentUrl.equals(url) || currentUrl.contains(url) || url.contains(currentUrl))
                .findAny()
                .isPresent();
    }
}
