package com.jantoniora16.videoclub;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

@Override
public void addViewControllers (ViewControllerRegistry registry) {
	registry.addViewController("/").setViewName("index");
	registry.addViewController("/quienes-somos").setViewName("somos");
	registry.addViewController("/contacto").setViewName("contacto");
}


}
