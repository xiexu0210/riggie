package com.xiexu.reggie.filtter;


import com.alibaba.fastjson.JSON;
import com.xiexu.reggie.common.BaseContext;
import com.xiexu.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName="LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher(); //路径匹配器

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long id = Thread.currentThread().getId() ;
        log.info("线程id:{}" ,id);

        HttpServletRequest request =  (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURL = request.getRequestURI();

        log.info("拦截到请求：{}", request.getRequestURI());

        System.out.println(requestURL);

        String[] url = new String[]{
          "/employee/login",
          "/employee/logout",
          "/backend/**",
          "/front/**",
                "/user/login",
                "/user/sendMsg"
                ,"/font/page/login.html"
        };
        //这个是控制类的路径
//        System.out.println(PATH_MATCHER.match("/backend/**", requestURL));
        boolean check = check(url, requestURL); //判断此次请求是否要处理
        if(check){
            log.info("本次请求{}不需要处理", requestURL);
            filterChain.doFilter(request, response);
            return;

        }
//        filterChain.doFilter(request,response);
        //判断登录状态，已登录，则放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为{}",request.getSession().getAttribute("employee") );

            BaseContext.setCurrentId( (Long) request.getSession().getAttribute("employee") );
            filterChain.doFilter(request,response);
            return;
        }

        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("user"));

            Long userId= (Long) request.getSession().getAttribute("user");

            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }



        //用户未登录则返回登录界面
        log.info("用户未登录");

        //将对象转化为json 无法自动转化
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")) );
        return;
        //放行


    }

    private boolean check(String[] urls, String requestURL){
        for (String url:urls){
            boolean match = PATH_MATCHER.match(url, requestURL);
            if(match == true){
                return true;
            }
        }
        return false;
    }
}
