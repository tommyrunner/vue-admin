# vue-admin模板项目

## 1、简述

+ 主要有三块：
  + spring boot+Jpa后端(文件：vue-admin-service)
  + vue+element前端(文件：vue-admin)
  + node生成axios请求sdk（node服务文件：swagger-to-sdk，生成后sdk文件：tommy-vueAdmin-sdk）

## 2、spring boot+Jpa后端

+ 实现的功能
  + spring boot+mysql+Jpa
  + swagger自动生成接口文档
  + 使用rides管理缓存
  + 使用Poi完成excel的导入导出
  + 用户登录管理
    + 密码使用md5暗文加密
    + kaptcha生成图片验证码
      + redis存入并1分钟删除,提供校验
    + 注册用户
      + 使用mail提供验证码，并redis保存一分钟
    + jwt生成token验证
      + redis存入,xx分钟删除,需要校验账号
      + 通过token管理接口访问权限
    + 通过存入路由权限菜单管理用户访问路由菜单
    + 使用aop验证权限的接口，并使用filter监听是需要token的接口
+ 注意事项
  + 使用rides管理缓存
    + 给token，验证码等等做缓存得加上过期时间，时间可用时间戳保存
  + 使用Poi完成excel的导入导出
    + 导出的时候可以时间写出文件流，前端可直接用open下载,但得设置好编码格式
    + 注意给实体类加上@Excel
  + 密码使用md5暗文加密
    + 密码是不可逆的，只能加密匹配
    + 可拿一个密码表存入，以后可以重置密码
  + 使用aop验证权限的接口，并使用filter监听是需要token的接口
    + 这里自定义了一个检测少部分接口必须需要《超级用户》才能调用
    + filter主要监听接口必须携带token并且是正确的token

## 3、vue+element前端

+ 实现功能

  + element+scss属性统一主题色
  + main.js中使用require循环导入全局组件
  + 使用i18n和element国际化(部分国际化，其他工具项目需求)
  + 管理模块
    + 用户管理（只有超级用户才能操作）
      + 添加用户需要唯一的邮箱，并提供验证
    + 菜单管理（只有超级用户才能操作）
      + 同步-将vue中的routes路由中的所有可用路由同步到路由表中，并添加到admin权限
  + 数据列表提供excel导出导出
  + svg管理项目图标-阿里fonticon
  + 使用vuex状态管理

    + token状态管理
    + routes路由菜单管理
    + 使用getters+mapXXX方便调用数据
  + plop模板功能
    + 通过模板生成form表单，form国际化表单模板
      + 列表页（查删改）
      + 编辑
      + 搜索
    + 通过模板生成spring boot模板
      + entity实体类
      + dao操作
        + 搜索
        + 删除
        + 添加/修改
      + service服务
        + 搜索
        + 删除
        + 添加/修改
      + controller接口
        + 搜索
        + 删除
        + 添加/修改

+ 注意事项

  + main.js中使用require循环导入全局组件

    + require.context读取的时候返回的是文件列表，列表里是一个**函数**如果**再次调用并传入文件**

      会返回一个**component**组件，相当于Import导入

  + 管理模块

    + 管理模块的所有接口需要《超级用户》操作
    + 用户模块里的权限列表和菜单模块列表对应
    + 用户的权限列表对应app的router显示菜单，这里涉及到了动态router问题
      + router.js路由文件可以分成两块：
        + 动态路由（根据用户菜单匹配后通过addRouter添加）
        + 静态路由（默认显示的路由，无需权限）
      + 匹配获取后的路由可用过vuex管理，**如果获取到了就无需addRouter添加，否则会重复添加**
      + 使用addRouter添加后得next({ ...to, replace: true })替换

## 4、node生成axios请求sdk

+ 实现功能

  + 获取swagger返回的json，并解析(解析根据自己项目需求)存入hbs模板中，并生成文件
  + 生成后的文件
    + tommy-vueAdmin-sdk:已经生成后的sdk,上传至npm中(tommy-vueAdmin-sdk)
    + 通过npm下载,并使用

+ 注意事项

  + 获取swagger返回的json，并解析(解析根据自己项目需求)存入hbs模板中，并生成文件

    + 因为每个项目的编写接口方式不一样，这里的解析可能就不一样，但

      思路是一样的，解析存入hbs模板中，并导出文件

  + 通过npm下载,并使用

    + 在vue中按模块依次Import自己需要的axios方法