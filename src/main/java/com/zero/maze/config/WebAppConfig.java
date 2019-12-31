package com.zero.maze.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zero.maze.interceptor.BaseInterceptor;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Resource
	private BaseInterceptor baseInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		List<String> excludeParams = new ArrayList<String>();
		excludeParams.add("/static/css/**");
		excludeParams.add("/static/js/**");
		registry.addInterceptor(baseInterceptor).addPathPatterns("*/**").excludePathPatterns(excludeParams);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		WebMvcConfigurer.super.addViewControllers(registry);
		registry.addViewController("/").setViewName("MazeShowing");
	}
}
