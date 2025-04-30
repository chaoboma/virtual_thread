package com.application.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    public static Integer maxTimes = 3600;//最大访问次数
    public static ExpiringCache<String, Integer> cache = new ExpiringCache<>(3600000); // 超时时间1个小时
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getParameter("token");
        //System.out.println("token:"+token);
        synchronized (cache){
            try{
                Integer times = cache.get(token);
                //System.out.println("times:"+times);
                if(times != null){
                    if(times >= maxTimes){
                        httpServletResponse.setStatus(418);
                        httpServletResponse.getWriter().write("Your request is too fast!");
                        return false;
                    }else{
                        times = times + 1;
                        cache.put(token,times);
                    }
                }else{
                    cache.put(token,1);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
