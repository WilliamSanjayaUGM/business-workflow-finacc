package com.learn.workflow.config;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {
	
	@Bean
	public ProcessEnginePlugin historyPlugin() {
		return new AbstractProcessEnginePlugin() {
			@Override
			public void preInit(ProcessEngineConfigurationImpl config) {
				config.setHistory("full");
			}
		};
	}
}
