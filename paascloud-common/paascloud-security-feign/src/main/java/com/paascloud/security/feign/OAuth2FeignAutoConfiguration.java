/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OAuth2FeignAutoConfiguration.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.feign;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

/**
 * The class O auth 2 feign auto configuration.
 * 配置Oauth2的Client
 * 这里因为是内部模块认证，没有那么多请求可以这么做
 * 如果是像QQ那样大公司，提供用户的Oauth2认证，那么需要使用@EnableOAuth2Client
 * 以此来隔离每个request，防止认证的信息被共享，每个请求都使用自己的
 * 关于@EnableOAuth2Client这个注解包含了@EnableOAuth2Sso的功能，即发现要认证的时候帮我们去走Oauth2的授权流程（当然需要配置认证中心等信息）
 *
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
@EnableConfigurationProperties(Oauth2ClientProperties.class)
public class OAuth2FeignAutoConfiguration {

	private final Oauth2ClientProperties oauth2ClientProperties;

	/**
	 * Instantiates a new O auth 2 feign auto configuration.
	 *
	 * @param oauth2ClientProperties the oauth 2 client properties
	 */
	@Autowired
	public OAuth2FeignAutoConfiguration(Oauth2ClientProperties oauth2ClientProperties) {
		this.oauth2ClientProperties = oauth2ClientProperties;
	}

	/**
	 * Resource details client credentials resource details.
	 * 这里使用客户端模式作为其他模块在认证中心的认证方式，因为是自己的内部系统
	 * 之间的交流，直接使用配置好的客户端信息进行认证会很方便
	 *
	 * @return the client credentials resource details
	 */
	@Bean("paascloudClientCredentialsResourceDetails")
	public ClientCredentialsResourceDetails resourceDetails() {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setId(oauth2ClientProperties.getId());
		details.setAccessTokenUri(oauth2ClientProperties.getAccessTokenUrl());
		details.setClientId(oauth2ClientProperties.getClientId());
		details.setClientSecret(oauth2ClientProperties.getClientSecret());
		details.setAuthenticationScheme(AuthenticationScheme.valueOf(oauth2ClientProperties.getClientAuthenticationScheme()));
		return details;
	}

	/**
	 * O auth 2 rest template o auth 2 rest template.
	 * 可以看作是标准的OAuth2RestTemplate构建方式吧，配置都是基本的
	 *
	 * @return the o auth 2 rest template
	 */
	@Bean("paascloudOAuth2RestTemplate")
		public OAuth2RestTemplate oAuth2RestTemplate() {
		// 这里使用默认提供的OAuth2ClientContext，用于将每个用户请求的认证信息隔离开。官方文档说如果我们不这么做，那么需要自己维护一个类似的数据结构用于隔离每个用户的请求。
		final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
		// 请求构建工厂使用支持NIO的Netty4ClientHttpRequestFactory提高效率
		// 默认是SimpleClientHttpRequestFactory这个，可能鸡肋
		oAuth2RestTemplate.setRequestFactory(new Netty4ClientHttpRequestFactory());
		return oAuth2RestTemplate;

	}

	/**
	 * Oauth 2 feign request interceptor request interceptor.
	 * 一个服务A要想访问另一个服务B的受限资源，必须要有两个认证信息
	 * 1、用户要认证通过
	 * 2、服务A的信息要认证通过
	 * 所以在访问服务B的时候A需要携带这两方面的信息
	 * 构建一个feign拦截器，将oauth的token放入每个请求中，这样才能访问到后面受限的资源
	 *
	 * @param oAuth2RestTemplate the o auth 2 rest template
	 *
	 * @return the request interceptor
	 */
	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor(@Qualifier("paascloudOAuth2RestTemplate") OAuth2RestTemplate oAuth2RestTemplate) {
		return new OAuth2FeignRequestInterceptor(oAuth2RestTemplate);
	}

	/**
	 * Feign logger level logger . level.
	 *
	 * @return the logger . level
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	/*
	 * To disable Hystrix support on a per-client basis create a vanilla Feign.Builder with the "prototype" scope, e.g.:
	 */
//	@Bean
//	@Scope("prototype")
//	public Feign.Builder feignBuilder() {
//		return Feign.builder();
//	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new Oauth2FeignErrorInterceptor();
	}
}