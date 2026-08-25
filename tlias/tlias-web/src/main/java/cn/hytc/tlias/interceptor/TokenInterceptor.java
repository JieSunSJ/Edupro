package cn.hytc.tlias.interceptor;

import cn.hytc.tlias.utils.CurrentHolder;
import cn.hytc.tlias.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        if(url.contains("login")){
            return true;
        }
        String jwt = request.getHeader("token");
        if (!StringUtils.hasLength(jwt)) {
            log.error("token is null");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try{
            // 解析jwt
            Claims claims = JwtUtils.parseJWT(jwt);
            // 将当前用户ID存入ThreadLocal
            Integer userId = claims.get("id", Integer.class);
            CurrentHolder.setCurrentId(userId);
        } catch (Exception e) {
            log.error("token is invalid, jwt={}, error={}", jwt, e.toString());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        log.info("token is valid");
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除 ThreadLocal，防止内存泄漏（绝对绝对不能忘！）
        CurrentHolder.remove();
    }
}