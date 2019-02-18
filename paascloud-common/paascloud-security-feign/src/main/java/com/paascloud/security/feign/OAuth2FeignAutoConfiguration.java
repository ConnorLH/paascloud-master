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
 * -XX-这里因为是内部模块认证，没有那么多请求可以这么做
 * 如果是像QQ那样大公司，提供用户的Oauth2认证，那么需要使用@EnableOAuth2Client
 * 以此来隔离每个request，防止认证的信息被共享，每个请求都使用自己的-XX-
 * 我的理解是@EnableOAuth2Client 适用于与浏览器交互服务，这个服务中配置了受限url时，如果用户访问，那么这个功能将会自动引导用户走Oauth2流程。
 * 但是对于app交互的服务，这个功能是不适用的，因为app是没办法自己完成Oauth2流程的，必须用户参与（Browser->A->B->C，其中C资源受限这种情况同样会有问题）。所以在调用方确定被调用方的资源受限时同样也需要在自己的服务中配置调用处的逻辑是受限的，这样一直传递到浏览器交互服务下一级(B,为什么到B就行了，看下面)，再使用@EnableOAuth2Client即可。
 * 关于@EnableOAuth2Client这个注解包含了@EnableOAuth2Sso的功能，即发现要认证的时候帮我们去走Oauth2的授权流程（当然需要配置认证中心等信息）
 *
 *	这个框架的设计是每个客户端的url都必须要经过登录才能访问，这里的登录有两种情况，一个是客户端使用客户端模式在认证中心进行了认证，二是用户走流程到认证中心进行了认证。这两种情况认证中心都会给一个token。
 *直接如何区分这两者，就需要每个资源服务的权限控制配置里面对需要用户登录的设置用户角色（比如所有用户都必须有user角色）。
 * 所以这里只是做了客户端登录
 * 什么时候用户登录？
 * 请求到达网关时，网关（上面的A）配置了Oauth2的Client，它去访问某个资源服务时(上面的B)，发现要求认证，就会把用户302到认证中心去做认证，在认证完成之后将拿到的token与用户session绑订到一起，这样用户第二次发过来的请求就不必再认证了，因为session里面能找到token。这里注意，如果网关是集群部署，要做session共享才行。
 * 总结上面，就是所有除了网关的服务都必须做权限配置（B调用C时，在知道C要求认证时这种情况B就必须配置）。
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
		// 这里第一个参数配置使用的4种oauth2认证模式哪一种
		final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
		// 请求构建工厂使用支持NIO的Netty4ClientHttpRequestFactory提高效率
		// 默认是SimpleClientHttpRequestFactory这个，可能鸡肋
		oAuth2RestTemplate.setRequestFactory(new Netty4ClientHttpRequestFactory());
		// 这里可以配置ClientTokenServices来持久化token，避免服务重启token丢失需要重新授权。应该是必须配置的。
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
