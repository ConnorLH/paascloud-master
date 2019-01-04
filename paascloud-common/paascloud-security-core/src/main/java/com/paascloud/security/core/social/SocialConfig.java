/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：SocialConfig.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.paascloud.security.core.properties.SecurityProperties;
import com.paascloud.security.core.social.support.PcSpringSocialConfigurer;
import com.paascloud.security.core.social.support.SocialAuthenticationFilterPostProcessor;

/**
 * 社交登录配置主类
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	@Autowired(required = false)
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

	/**
	 * Gets users connection repository.
	 *
	 * @param connectionFactoryLocator the connection factory locator
	 *
	 * @return the users connection repository
	 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setTablePrefix("pc_uac_");
		if (connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}

	/**
	 * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
	 * 这里构建一个PcSpringSocialConfigurer（SpringSocialConfigurer）
	 * 在security拦截主配置处添加这个配置
	 * 作用是在social登录成功后，自动redirect到设置的callback url时对其进行拦截处理
	 * 这样就可以实现登录成功后的逻辑处理
	 * 疑问：在这里直接构建SpringSocialConfigurer的实现类会不会有问题，电脑端和手机端的登录成功处理
	 * 方式可能不同，这里应该配置多个bean将不同客户端的配置都构建出来再注册到security的拦截主配置中
	 * @return spring social configurer
	 */
	@Bean
	public SpringSocialConfigurer pcSocialSecurityConfig() {
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		PcSpringSocialConfigurer configurer = new PcSpringSocialConfigurer(filterProcessesUrl);
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
		configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
		return configurer;
	}

	/**
	 * 用来处理注册流程的工具类
	 *
	 * @param connectionFactoryLocator the connection factory locator
	 *
	 * @return provider sign in utils
	 */
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}
}
