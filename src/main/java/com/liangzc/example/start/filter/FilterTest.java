package com.liangzc.example.start.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/filter/*",filterName = "filterTest")
public class FilterTest implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("--------------------------doFilter-----------------------");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
