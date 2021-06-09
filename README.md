# vue-admin模板项目

+ swagger-to-sdk:通过swagger生成axios接口访问sdk

  + 自动生成接口axsio访问sdk

+ tommy-vueAdmin-sdk:已经生成后的sdk,上传至npm中(tommy-vueAdmin-sdk)

  + 通过npm下载,并使用

+ vue-admin:客户端

  + vue-element-admin模板基本功能

  + 国际化

  + 登录,登出toekn管理

  + svg管理项目图标-阿里fonticon

  + 通过swgger-to-sdk生成的axios访问sdk模板化管理api

  + 使用vuex状态管理

    + token状态管理
    + routes路由管理
    + 使用getters+mapXXX方便调用数据

  + 路由权限管理

    

+ vue-admin-service:接口服务端

  + spring boot+mysql
  + jpa操作数据库
  + swagger自动生成接口文档
  + 使用rides管理缓存
  + 用户登录管理
    + 密码使用md5暗文加密
    + kaptcha生成图片验证码
      + redis存入并1分钟删除,提供校验
    + jwt生成token验证
      + redis存入,15分钟删除,需要校验账号
      + 通过token管理接口访问权限
    + 通过存入路由权限表管理用户访问路由权限

