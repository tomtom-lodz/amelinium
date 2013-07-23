package com.tomtom.amelinium.confluence.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.tomtom.amelinium.confluence.logic.BacklogPageCorrector;
import com.tomtom.amelinium.confluence.logic.ChartPageCorrector;
import com.tomtom.amelinium.confluence.server.controller.MainController;

@Configuration
public class AppConfig {

	@Bean
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver result = new InternalResourceViewResolver();
		result.setViewClass(JstlView.class);
		result.setPrefix("/WEB-INF/jsp/");
		result.setSuffix(".jsp");
		return result;
	}

	@Bean
	public MainController uploadController() {
		MainController mainController = new MainController();
		return mainController;
	}

	@Bean
	public BacklogPageCorrector backlogPageCorrector() {
		BacklogPageCorrector backlogPageCorrector = new BacklogPageCorrector();
		return backlogPageCorrector;
	}

	@Bean
	public ChartPageCorrector chartPageCorrector() {
		ChartPageCorrector chartPageCorrector = new ChartPageCorrector();
		return chartPageCorrector;
	}
}
