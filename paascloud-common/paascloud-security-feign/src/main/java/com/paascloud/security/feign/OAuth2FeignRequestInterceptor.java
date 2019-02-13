/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OAuth2FeignRequestInterceptor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.Assert;

/**
 * The class O auth 2 feign request interceptor.
 * 构建一个feign拦截器，将oauth的token放入每个请求中
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {
	private static final String BEARER_TOKEN_TYPE = "bearer";

	private final OAuth2RestTemplate oAuth2RestTemplate;

	/**
	 * Instantiates a new O auth 2 feign request interceptor.
	 *
	 * @param oAuth2RestTemplate the o auth 2 rest template
	 */
	OAuth2FeignRequestInterceptor(OAuth2RestTemplate oAuth2RestTemplate) {
		Assert.notNull(oAuth2RestTemplate, "Context can not be null");
		this.oAuth2RestTemplate = oAuth2RestTemplate;
	}

	/**
	 * Apply.
	 * -XX-这里有个疑问，是否所有的调用都必须携带token信息？
	 * 是否应该给出一个可配置的方式（访问某些服务不需要token）-XX-
	 * 这里分两种情况，一种是浏览器交互服务，AccessToken可以直接用oAuth2RestTemplate去拿，拿不到会报错。另一种是app交互服务，需要调用方将AccessToken传递参数过来。所以这里也应该区分Browser依赖和App依赖。
	 * @param template the template
	 */
	@Override
	public void apply(RequestTemplate template) {
		log.debug("Constructing Header {} for Token {}", HttpHeaders.AUTHORIZATION, BEARER_TOKEN_TYPE);
		// oAuth2RestTemplate.getAccessToken()应该肯定能拿到的，登录一次之后就会保存起来
		template.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", BEARER_TOKEN_TYPE, oAuth2RestTemplate.getAccessToken().toString()));
	}
}
