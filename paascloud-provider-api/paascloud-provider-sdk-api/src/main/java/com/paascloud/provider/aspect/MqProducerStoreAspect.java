/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqProducerStoreAspect.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.aspect;

import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.provider.annotation.MqProducerStore;
import com.paascloud.provider.exceptions.TpcBizException;
import com.paascloud.provider.model.domain.MqMessageData;
import com.paascloud.provider.model.enums.DelayLevelEnum;
import com.paascloud.provider.model.enums.MqSendTypeEnum;
import com.paascloud.provider.service.MqMessageService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;
import java.lang.reflect.Method;


/**
 * The class Mq producer store aspect.
 * 可靠消息{@link MqProducerStore}注解生产者AOP功能实现
 * 被该注解的方法会按照如下步骤执行
 * 1、上游应用发送待确认消息（方法参数中的MqMessageData为消息内容）到可靠消息系统。(本地消息落地)
 * 2、可靠消息系统保存待确认消息并返回。
 * 3、上游应用执行本地业务。
 * 4、上游应用通知可靠消息系统确认业务已执行并发送消息。
 * 5、可靠消息系统修改消息状态为发送状态并将消息投递到 MQ 中间件。
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Aspect
public class MqProducerStoreAspect {
	@Resource
	private MqMessageService mqMessageService;

	/**
	 * 生产者组，每个服务是一个生产者组，通过这个可以来回查消息状态
	 */
	@Value("${paascloud.aliyun.rocketMq.producerGroup}")
	private String producerGroup;

	@Resource
	private TaskExecutor taskExecutor;

	/**
	 * Add exe time annotation pointcut.
	 * 切面
	 */
	@Pointcut("@annotation(com.paascloud.provider.annotation.MqProducerStore)")
	public void mqProducerStoreAnnotationPointcut() {

	}

	/**
	 * Add exe time method object.
	 *
	 * @param joinPoint the join point
	 *
	 * @return the object
	 */
	@Around(value = "mqProducerStoreAnnotationPointcut()")
	public Object processMqProducerStoreJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("processMqProducerStoreJoinPoint - 线程id={}", Thread.currentThread().getId());
		Object result;
		Object[] args = joinPoint.getArgs();
		MqProducerStore annotation = getAnnotation(joinPoint);
		// 获取发送类型，默认为待确认消息
		MqSendTypeEnum type = annotation.sendType();
		int orderType = annotation.orderType().orderType();
		// 获取延迟级别
		DelayLevelEnum delayLevelEnum = annotation.delayLevel();
		if (args.length == 0) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050005);
		}
		// 从方法参数中拿到要发送的消息内容，这必须要求方法参数中要有MqMessageData一项
		MqMessageData domain = null;
		for (Object object : args) {
			if (object instanceof MqMessageData) {
				domain = (MqMessageData) object;
				break;
			}
		}

		if (domain == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050005);
		}

		domain.setOrderType(orderType);
		domain.setProducerGroup(producerGroup);
		// 待确认消息才在方法执行前发送，也必须在方法执行前发送，如果后续方法执行出错（本地事务出错），后续消息回查会自动确认这里发送的消息失效
		if (type == MqSendTypeEnum.WAIT_CONFIRM) {
			if (delayLevelEnum != DelayLevelEnum.ZERO) {
				domain.setDelayLevel(delayLevelEnum.delayLevel());
			}
			mqMessageService.saveWaitConfirmMessage(domain);
		}
		// 执行方法（本地事务）
		result = joinPoint.proceed();
		// 直接发送和正式发送在方法执行完之后再发送，出错则方法回滚，注意直接发送不是可靠消息
		if (type == MqSendTypeEnum.SAVE_AND_SEND) {
			// 预发送消息，发送消息的准备工作
			mqMessageService.saveAndSendMessage(domain);
		} else if (type == MqSendTypeEnum.DIRECT_SEND) {
			mqMessageService.directSendMessage(domain);
		} else {
			// 正式逻辑已经处理完成，正式发送
			final MqMessageData finalDomain = domain; // 这个lambda必须final的小技巧，搞一个复制变量
			// 注意这里采用异步发送，这时已经不用关心能不能发送成功，不成功有消息回查保证一致性
			// 这里的实现和rocketmq的事务消息差不多，是把前面的预消息发送出去，如果消息内容依赖本地业务执行后的结果怎么办？？？能否在这里再发送一个消息，预消息只起到回查标记的作用。
			taskExecutor.execute(() -> mqMessageService.confirmAndSendMessage(finalDomain.getMessageKey()));
		}
		return result;
	}

	/**
	 * 从切入点拿到方法描述
	 * @param joinPoint
	 * @return
	 */
	private static MqProducerStore getAnnotation(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(MqProducerStore.class);
	}
}
