## Spring Cloud 实战项目

### 项目介绍
```
功能点：
    模拟商城，完整的购物流程、后端运营平台对前端业务的支撑，和对项目的运维，有各项的监控指标和运维指标。
技术点：
       核心技术为springcloud+vue两个全家桶实现，采取了取自开源用于开源的目标，所以能用开源绝不用收费框架，整体技术栈只有
    阿里云短信服务是收费的，都是目前java前瞻性的框架，可以为中小企业解决微服务架构难题，可以帮助企业快速建站。由于服务
    器成本较高，尽量降低开发成本的原则，本项目由10个后端项目和3个前端项目共同组成。真正实现了基于RBAC、jwt和oauth2的
    无状态统一权限认证的解决方案，实现了异常和日志的统一管理，实现了MQ落地保证100%到达的解决方案。
	
	核心框架：springcloud Edgware全家桶
	安全框架：Spring Security Spring Cloud Oauth2
	分布式任务调度：elastic-job
	持久层框架：MyBatis、通用Mapper4、Mybatis_PageHelper
	数据库连接池：Alibaba Druid
	日志管理：Logback	前端框架：Vue全家桶以及相关组件
	三方服务： 邮件服务、阿里云短信服务、七牛云文件服务、钉钉机器人服务、高德地图API
```
### 平台目录结构说明


```
├─paascloud-master----------------------------父项目，公共依赖
│  │
│  ├─paascloud-eureka--------------------------微服务注册中心
│  │
│  ├─paascloud-discovery-----------------------微服务配置中心
│  │
│  ├─paascloud-monitor-------------------------微服务监控中心
│  │
│  ├─paascloud-zipkin--------------------------微服务日志采集中心
│  │
│  ├─paascloud-gateway--------------------------微服务网关中心
│  │
│  ├─paascloud-provider
│  │  │
│  │  ├─paascloud-provider-mdc------------------数据服务中心
│  │  │
│  │  ├─paascloud-provider-omc------------------订单服务中心
│  │  │
│  │  ├─paascloud-provider-opc------------------对接服务中心
│  │  │
│  │  ├─paascloud-provider-tpc------------------任务服务中心
│  │  │
│  │  └─paascloud-provider-uac------------------用户服务中心
│  │
│  ├─paascloud-provider-api
│  │  │
│  │  ├─paascloud-provider-mdc-api------------------数据服务中心API
│  │  │
│  │  ├─paascloud-provider-omc-api------------------订单服务中心API
│  │  │
│  │  ├─paascloud-provider-opc-api------------------对接服务中心API
│  │  │
│  │  ├─paascloud-provider-tpc-api------------------任务服务中心API
│  │  │
│  │  ├─paascloud-provider-sdk-api------------------可靠消息服务API
│  │  │
│  │  └─paascloud-provider-uac-api------------------用户服务中心API
│  │
│  ├─paascloud-common
│  │  │
│  │  ├─paascloud-common-base------------------公共POJO基础包
│  │  │
│  │  ├─paascloud-common-config------------------公共配置包
│  │  │
│  │  ├─paascloud-common-core------------------微服务核心依赖包
│  │  │
│  │  ├─paascloud-common-util------------------公共工具包
│  │  │
│  │  ├─paascloud-common-zk------------------zookeeper配置
│  │  │
│  │  ├─paascloud-security-app------------------公共无状态安全认证
│  │  │
│  │  ├─paascloud-security-core------------------安全服务核心包
│  │  │
│  │  └─paascloud-security-feign------------------基于auth2的feign配置
│  │
│  ├─paascloud-generator
│  │  │
│  │  ├─paascloud-generator-mdc------------------数据服务中心Mybatis Generator
│  │  │
│  │  ├─paascloud-generator-omc------------------数据服务中心Mybatis Generator
│  │  │
│  │  ├─paascloud-generator-opc------------------数据服务中心Mybatis Generator
│  │  │
│  │  ├─paascloud-generator-tpc------------------数据服务中心Mybatis Generator
│  │  │
│  │  └─paascloud-generator-uac------------------数据服务中心Mybatis Generator




```


### 特殊说明


```
这里做一个解释由于微服务的拆分受制于服务器，这里我做了微服务的合并，比如OAuth2的认证服务中心和用户中心合并，
统一的one service服务中心和用户认证中心合并，支付中心和订单中心合并，其实这也是不得已而为之，
只是做了业务微服务中心的合并，并没有将架构中的 注册中心 监控中心 服务发现中心进行合并。
```


### 作者介绍

```
Spring Cloud 爱好者,现就任于鲜易供应链平台研发部.
```

### [QQ群](//shang.qq.com/wpa/qunwpa?idkey=d09a293bcef98e6b5348dfbb3f4587eb80f81bbef3449b250921582cc3c7b3df)
 
 ![这里写图片描述](http://img.paascloud.net/paascloud/doc/paascloud-qq-qun.png)


## 配套项目

```
后端项目：https://github.com/paascloud/paascloud-master 
         https://gitee.com/paascloud/paascloud-master
登录入口：https://github.com/paascloud/paascloud-login-web
         https://gitee.com/paascloud/paascloud-login-web
后端入口：https://github.com/paascloud/paascloud-admin-web
         https://gitee.com/paascloud/paascloud-admin-web
前端入口：https://github.com/paascloud/paascloud-mall-web
         https://gitee.com/paascloud/paascloud-mall-web
```

### 传送门
- 博客入口： http://blog.paascloud.net
- 后端入口： http://admin.paascloud.net
- 模拟商城: http://mall.paascloud.net
- 文档手册: http://document.paascloud.net
- github: https://github.com/paascloud

### 架构图

![项目架构图](http://img.paascloud.net/paascloud/doc/paascloud-project.png)

# paascloud快速搭建企业级分布式微服务平台操作手册（V1.0）

刘兆明

编制单位：个人

起 稿： 刘兆明

日 期： 2018年3月13日

目录

[paascloud快速搭建企业级分布式微服务平台操作手册 （V1.0） 1](#_Toc508749204)

[目录 2](#_Toc508749205)

[1 前言 4](#前言)

[2 简介 4](#简介)

[2.1 系统运行环境 4](#系统运行环境)

[3 统一登录平台 5](#统一登录平台)

[3.1 用户登录 5](#用户登录)

[3.2 用户注册 6](#用户注册)

[3.3 用户忘记密码 7](#用户忘记密码)

[3.3.1 使用邮箱忘记密码 7](#使用邮箱忘记密码)

[3.3.2 使用手机号忘记密码 9](#使用手机号忘记密码)

[4 商城交易平台 10](#商城交易平台)

[4.1 首页 10](#首页)

[4.2 商品列表 11](#商品列表)

[4.3 商品详情 12](#商品详情)

[4.4 我的购物车 13](#我的购物车)

[4.5 订单确认 14](#订单确认)

[4.6 支付订单 15](#支付订单)

[4.7 我的订单 16](#我的订单)

[4.7.1 订单列表 16](#订单列表)

[4.7.2 订单详情 16](#订单详情)

[4.8 个人中心 17](#个人中心)

[5 运营平台 18](#运营平台)

[5.1 用户中心 18](#用户中心)

[5.1.1 角色管理 18](#角色管理)

[5.1.2 用户管理 21](#用户管理)

[5.1.3 菜单管理 26](#菜单管理)

[5.1.4 权限管理 27](#权限管理)

[5.1.5 组织管理 29](#组织管理)

[5.1.6 调用链监控 30](#调用链监控)

[5.1.7 监控报警 31](#监控报警)

[5.1.8 接口文档 32](#接口文档)

[5.1.9 数据库监控 32](#数据库监控)

[5.1.10 操作日志监控 33](#操作日志监控)

[5.1.11 登录密钥监控 33](#登录密钥监控)

[5.1.12 异常日志监控 34](#异常日志监控)

[5.2 订单中心 35](#订单中心)

[5.2.1 商品管理 35](#商品管理)

[5.2.2 订单管理 37](#订单管理)

[5.2.3 收货管理 37](#收货管理)

[5.2.4 商品分类 38](#商品分类)

[5.2.5 购物车管理 39](#购物车管理)

[5.3 数据中心 40](#数据中心)

[5.3.1 数据字典 40](#数据字典)

[5.3.2 Topic管理 41](#topic管理)

[5.3.3 Tag管理 42](#tag管理)

[5.3.4 生产者管理 42](#生产者管理)

[5.3.5 消费者管理 43](#消费者管理)

[5.3.6 发布管理 43](#发布管理)

[5.3.7 订阅管理 44](#订阅管理)

[5.3.8 可靠消息 44](#可靠消息)

[5.3.9 消息记录 45](#消息记录)

前言
====

为了企业能够快速的搭建微服务平台，开发和测试能够快速的了解和使用本系统。

简介
====

系统运行环境
------------

操作系统： Linux/Unix系列、Windows2000/XP/2003系统  
Java虚拟机：SUN J2SDK 8.0  
应用服务器：Tomcat7.X、RocketMQ、RabbitMQ、Zookeeper  
数据库：MySQL、Redis

三方服务: 七牛云存储、阿里云短信服务、钉钉机器人、高德地图

项目地址
--------

后端项目：https://github.com/paascloud/paascloud-master

登录入口：https://github.com/paascloud/paascloud-login-web

后端入口：https://github.com/paascloud/paascloud-admin-web

前端入口：https://github.com/paascloud/paascloud-mall-web

<br>3 统一登录平台
==================

用户登录
--------

![](media/04debb3abad94d3b8e8733d32dc42040.png)

统一登录页面，验证码存储在redis服务器，验证码一次验证，登录成功则失效，默认超时时间是3分钟，无论是商城前端系统登录还是运营平台后端系统统一跳转到域名<http://login.paascloud.net/login>，输入帐号和密码会跳转到上一个页面（登录请求之前的页面），如果直接输入登录域名则登录成功会跳转到商城页面http://mall.paascloud.net/。

<br>用户注册
------------

![](media/24ae31ddda35eddfeb581b7b09317eb8.png)

用户注册，所有字段均为必须字段，邮箱必须输入邮箱格式，手机号码必须输入11位手机号码，手机号码和邮箱不能重复，确认密码和密码必须相同支持数字加字母组合，验证码为一次性校验，超时时间为3分钟，注册成功会有提示登录到对应邮箱进行用户激活，没有激活的用户不能登录到运行平台，可以登录商城进行购买操作。

注册成功示例：

![](media/c3e8061f9432a17aa3642596c15322ec.png)

提示为渐变效果

<br>**用户忘记密码**
--------------------

用户忘记密码功能支持手机模式和邮箱模式，点击用户登录页面的忘记密码按钮会跳转到用户忘记密码页面默认是邮箱忘记密码。

### 使用邮箱忘记密码

![](media/482945926450b4ad165af1798e9e2686.png)

邮箱格式要正确且用户要激活成功的才可以进行提交，提交成功后会发一封重置密码的邮件给用户，示例如下：

![](media/4e3a179ad79cbabd7e73517daa2723ca.png)

![](media/de2b5d8f486a7909a031d777875b6505.jpg)

点击重置密码或者在浏览器输入url会跳转到重置密码页面。示例如下

![](media/5c6b42d735c39c06092e71da79df58f4.png)

输入正确的密码进行提交会收到一封重置密码成功的邮件。

### 使用手机号忘记密码

输入正确的手机号且用户状态为生效，输入正确的验证码，获取验证码按钮会被激活，点击则验证码倒计时开始，并向用户发送验证码，这里有限流控制默认最多一人发送10次，最多100次，然后输入正确的手机验证码，提交按钮会被激活，提交会跳转到3.3.1的修改密码页面，引导用户修改密码

![](media/00c751c61bb8acaecc120fc92a3177d6.png)

<br>商城交易平台
================

 首页
-----

![](media/008fc7d3a270ee2756a69848f8219e4b.png)

登录按钮： 跳转到登录页 *http://login.paascloud.net/index*

注册按钮： 跳转到注册页 <http://login.paascloud.net/register#>

购物车按钮： 跳转到购物车页面 <http://mall.paascloud.net/cart>

我的订单按钮：跳转到我的订单页面（需要登录） <http://mall.paascloud.net/order>

我的PcMall: 跳转到个人中心页面（需要登录） <http://mall.paascloud.net/center>

点击PcMall: 跳转到首页，输入框中可以对商品进行模糊搜索，
滚动鼠标滑轮可以浏览商品分类，点击商品分类可以浏览商品

<br>商品列表
------------

![](media/6467220b47b51e5526609ba41e404ae8.png)

点击商品图片或者信息可以跳转到商品详情页。

<br>商品详情
------------

![](media/e0adde7dcb8ee4d0df31275afc3361b4.png)

点击加入购物车可以把商品加入到购物车中，如果有商品对应的商品数量会+1，如果没有该商品购物车会多出一条商品信息。

![](media/a855a378d212411edf75456e9925d986.png)

加入购物车可以选择进入购物车查看商品付款，也可以继续浏览其他商品

<br>我的购物车 
---------------

![](media/9e9d66ded9e81cef696ef72f485efa4a.png)

删除按钮： 直接删除指定商品的购物车记录，重新计算商品条数和总价。

全选/反选：全部选中/反选商品，重新计算商品条数和总价。

选择： 将指定商品选中，重新计算商品条数和总价。

如果是没有登录状态，商品会存在浏览器的localstorage中，如果是登录状态会同步更新到数据库表中。

去结算：跳转到结算页面

<br>订单确认
------------

![](media/2c26299d8918ab9a574807616e5b29ab.png)

可以选择收货地址或者新增收货地址，或者提交订单。

<br>支付订单
------------

![](media/b39c197e729fad5ec1bfc565e4e1b30d.png)

扫码支付，支付成功会跳转到首页。

<br>我的订单 
-------------

### 订单列表

![](media/4cbb25d9f13fac2d5b7b3e2e52f6f624.png)

### 订单详情

![](media/81c8fa2ffe8c6f9fb84281dbcfb16439.png)

可以支付或者取消订单

<br>个人中心
------------

![](media/8f3eb13de7ba977e2177d531589b6d02.png)

用户基本信息修改和密码修改。

<br>运营平台
============

用户中心
--------

### 角色管理

**角色列表：**

![](media/38aaf4e0f5876ee0e3e2747ab99cec9f.png)

可以根据角色编码和角色名称和角色状态进行查询。提供添加角色、批量删除、启用/禁用、绑定用户、分配菜单、分配权限、修改、删除操作功能，超级管理员角色不允许操作。

**添加角色:**

![](media/5c78ed6a091d6400d6c470fc5e8c5fcb.png)

**绑定用户：**

![](media/99e7eee64d0592c54e07c34a44d87bba.png)

超级管理员用户admin不能操作，如果有数据变化确认按钮会被激活。点击确认进行用户绑定。

**分配菜单:**

![](media/a99de998440f6712a37851047b6d2882.png)

加载页面会把已有权限选中，可以选择菜单进行分配。

**分配权限:**

![](media/b513becae65822b370618f50b5eb6a1a.png)

选择权限进行保存操作。

**修改角色:**

![](media/eb0f620b0186d423ab9528344b5b7bc1.png)

**删除角色：** 会解除用户和角色绑定关系，角色和菜单和权限的绑定关系

**批量删除：**选择角色的激活，功能和删除角色相同。

说明：访客角色是内置角色，测试人员不要进行删除。

### <br>用户管理

**用户列表：**

![](media/998360d5ed03955ca37c60058d445d45.png)

可以对用户进行筛选、新增、禁用/启用、修改、绑定角色、重置密码、和查看在线用户。

**添加用户：**

![](media/a8e1e804f937af6a7b4e2ba113c5b93e.png)

必要的校验，这里只展示了默认校验，这里不一一列举。

**修改用户：**

![](media/3b1fe9ce7c43a4dd32ddad7fd878a532.png)

修改用户有手机号码和邮箱的唯一性校验。

**禁用/启用：**

![](media/fcbbcb7ed7693f2bf17b7376d0a2d5b0.png)

点击确认会对用户状态进行修改。

**角色绑定：**

![](media/ce87641a7b69f1c967637f27168bee25.png)

角色绑定和用户绑定实现功能相同，都是绑定用户和角色的关系，这里如果在角色绑定过会在已绑定角色页面出现。

**重置密码：**

![](media/9396f0e5e5daa8cb4464df35272f8993.png)

![](media/1381524c0f3b7dfdc4bfb167c8dc213f.png)

重置密码会给用户绑定的邮箱发送一份邮件，告诉用户重置后的密码

**在线用户：**

![](media/baf982d6f6189c81c9c673ac88eab552.png)

用于采用jwt无状态登录颁发的token默认是120分钟，没有超时的用户会在这里出现。

**查看用户详情:**

![](media/2db412fbaf6776f3516ab93ec5fb4232.png)

![](media/1733b6f8dfb1988a5e9e705bfc1e8823.png)

这里未列出的业务类型是因为在权限管理里没有配置。

![](media/78cfd516ef96bcbcdad1939b3d0c4095.png)

**个人信息：** 功能和用户详情相同。

**修改密码：**

![](media/641ad60c60a7fb06595e97b97bf62d28.png)

数据的校验这里不详细列举.

### <br>菜单管理

**菜单列表：**

![](media/f7b87e174907316b4aea138d1a90c67c.png)

主要功能：禁用/启用、添加子节点、修改、删除角色。

**禁用/启用：**禁用父节点子节点全部禁用， 启用子节点则父节点自动启用

**添加子节点：**

![](media/cede6c0bb0e5ea4191734a5bf33e0865.png)

参数和唯一值的校验。保存成功会刷新菜单树。

**菜单修改：**

![](media/023f9729c53c3c405f8b2db56a5bc027.png)

有唯一值校验，保存成功后会刷新菜单树。

**删除：**删除会解除与角色的绑定关系和与权限的绑定关系，也会刷新菜单树。

### 权限管理

**权限列表：**

![](media/8c39ce1b5835d7e790177b0418c248b0.png)

功能点：分页查询、添加、修改、删除、批量删除、禁用/启用操作。

**添加权限：**

![](media/994f630a55822f9debbe9db53ed781a9.png)

添加权限成功会刷新分页列表。

**修改权限：**

![](media/67411060e4bb5def14d4dbc3f9eaae53.png)

权限编码不能修改。

**禁用/启用：**修改权限状态。

**删除/批量删除：**解除权限和菜单的绑定关系和与角色的绑定关系。

### 组织管理

![](media/1306e206318d417ec53d4c4beac7ad8f.png)

功能点：禁用/启用、添加子节点、修改、删除、绑定用户操作。

**启用/启用：** 修改组织状态

**删除：** 会解除与用户的绑定关系。

**添加/修改：** 必要校验，刷新组织树

**绑定用户：**

![](media/f8597b56c0323914201ea216b48a3088.png)

### 调用链监控

![](media/436caf7ff558a7ce258f088e20753412.png)

查看各微服务之间的调用关系。

查看组件的调用详情

![](media/2595fd214d93727135b12a1536d3465b.png)

![](media/19dd1e2c8ed38845c4024911b861912f.png)

### 监控报警

**登录：**

![](media/6861f3ff60683a0ef3d3c0f8d3674912.png)

查看各个微服务状态：

![](media/72b3287db633f85783e983f8b511bbfb.png)

![](media/0771694024e086645a1f8eec7660cdf1.png)

各项详细指标不一一列举

### 接口文档

![](media/6f920e34618b3da85b1fd9d62be7c8c8.png)

开发和测试联调的接口文档数据，采用swagger-ui自动生成。

### 数据库监控

![](media/ee3dd198eda7fdba244568062f2e2990.png)

输入用户名密码进行登录。

![](media/408ce36dec6ba32022889080e78413d4.png)

可对数据库各个指标进行监控，这里不穷举。

### 操作日志监控

![](media/fbb3d2d5519f68cd3fba992fe262fc68.png)

根据不同的查询条件进行筛选数据查看用户行为。

### 登录密钥监控

![](media/80b459499c063b34e51580c35316e315.png)

这里是对密钥的统一管理，颁发的密钥都是存在redis数据库的
这里强制退出之后，会失效用户的本次登录，服务器会拒绝用户请求。

### 异常日志监控

![](media/72e104e43498797f3b022ceff6f28176.png)

系统级错误会保存到数据库。

![](media/1530a3eff2ae68a851fb3ae31e65f995.png)

方便开发人员定位问题，这里是一个备份，在出现异常的时候服务器会通过MQ发送到指定的钉钉群（短信、QQ）等，这里暂时未开发短信2.0会补充。

<br>订单中心 
-------------

### 商品管理

![](media/d37c5568919dcd2dec520d645beb0fff.png)

功能点：分页查询、添加、修改、删除商品

**添加商品：**

![](media/7cc377598598d1820a132ca244b096f6.png)

分三部分组成：
基本信息，头图，和商品详情，详情由富文本编译器生成，文件上传到七牛云上。

**修改商品：**

![](media/18a9864eec1e106f11887bea326553b5.png)

修改和新增一样，都是最多添加4张图片，超过4张图片不显示添加图片按钮,每个图片都有放大和删除功能。

![](media/1dcff44932a8995f234b90d2d212b6e4.png)

**查看详情：**

![](media/aa3ce0d038441d9a58c295e3c50ecdf6.png)

图片可以放大

### 订单管理

**订单列表：**

![](media/83080c00cdcfeabf624a6c64f770e50c.png)

**有筛选和查看功能**

![](media/37b881dcdb3e7585a10630ae246fdb5d.png)

### 收货管理

![](media/1b9e911131de450e508fb0665f4b2155.png)

### 商品分类

![](media/b34c6495a93ebb7201f1ed70485ea155.png)

功能点： 查看、 禁用/启用、添加子节点、修改、删除

**添加：**

![](media/6ad60de75a14847d397a91a3d4c3b6f7.png)

**修改：**

![](media/b4fe3781879791322aeb02f019783a32.png)

理论只能维护二级分类，这里权限没有做控制可以无限制增加层级，但是最好不要添加超过二级节点。

### 购物车管理

暂未开发。

<br>数据中心 
-------------

### 数据字典

![](media/2d56775742a19b7ce04cb252e9a4e4e2.png)

功能点：启用/禁用、添加、修改、删除

**添加：**

![](media/7378b384e980dc4b12b7286ff369a31a.png)

**修改：**

![](media/d6cdf868647b769b2e32d9258a1436fb.png)

### Topic管理

![](media/a325f3bb48d42944ae0222dc48795560.png)

说明：数据由后端维护，申请发布和申请订阅暂未开发

### Tag管理

![](media/b09f786a1e516b41d8ea8680268e24d0.png)

可以对数据禁用/启用、删除

### 生产者管理

![](media/fdfab6488f14efb2b891da5eee45946a.png)

可以对数据禁用/启用、删除

### 消费者管理

![](media/9a27671639549a5cd5b5bae9ba2200ee.png)

可以对数据禁用/启用、删除

### 发布管理

![](media/1a399a59af68d36f27576e5fc4e3e862.png)

### 订阅管理

![](media/ad1466280593dbc67472e35d93a87e67.png)

### 可靠消息

![](media/5691e312cf85a8eac7d0aa2a577d9b7a.png)

功能点：分页查询、查看详情、重发消息

**查看详情：**

![](media/304593aa401f385f3836b844a540a2ab.png)

消息重发：一般由开发人员操作。

### 消息记录

![](media/1a84cd976efc0b375563d1333a9c4873.png)

功能同5.3.8 这是一套可靠消息落地的两种实现。

说明：5.3.2到5.3.9是开发人员用于实现可靠消息落地的数据结构，因此这里所有数据由开发或者运维维护。






