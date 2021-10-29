package com.liangzc.example.start.filter;

import javax.servlet.*;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*",filterName = "filterTest")
public class FilterTest implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("--------------------------doFilter-----------------------");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
