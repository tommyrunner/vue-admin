# vue-admin模板项目1.1.2

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
  
+ 上线注意

  + 如果你是使用linux系统部署,发送邮箱可能解析不了ip,需要加上配置

    ```properties
    #linux系统兼容 无法解析域名的问题
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    spring.mail.properties.mail.smtp.starttls.required=true
    spring.mail.port=465
    spring.mail.properties.mail.smtp.socketFactory.port = 465
    spring.mail.properties.mail.smtp.socketFactory.class = javax.net.ssl.SSLSocketFactory
    spring.mail.properties.mail.smtp.socketFactory.fallback = false
    ```

    

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
  + 打包混淆，加密:UglifyJS Webpack Plugin

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
    
  + svg管理项目图标-阿里fonticon
  
    + 如果需要线上使用fonticon只需要再publish里引入就行
    + 如果需要本底直接使用(比如路由),需要将svg下载在icon本地文件夹里
  
+ 上线注意事项

  + 注意打包后的porxy时效,需要受动在拦截器中修改请求头

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

## 5、使用方法

### 5.1启动vue-admin客户端

+ 下载依赖
  + npm run install
+ 启动服务
  + npm run dev

#### 5.1.1、文件目录结构

+ 大致与vue-element-admin（因为是在它基础上写的）

  + [vue-element-admin官网][https://panjiachen.github.io/vue-element-admin-site/zh/guide/#%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84]

  ```sh
  ├── build                      # 构建相关
  ├── plop-templates             # 基本模板
  ├── public                     # 静态资源
  │   │── favicon.ico            # favicon图标
  │   └── index.html             # html模板
  ├── src                        # 源代码
  │   ├── api                    # 所有请求
  │   ├── assets                 # 主题 字体等静态资源
  │   ├── components             # 全局公用组件
  │   ├── directive              # 全局指令
  │   ├── filters                # 全局 filter
  │   ├── icons                  # 项目所有 svg icons
  │   ├── lang                   # 国际化 language
  │   ├── layout                 # 全局 layout
  │   ├── router                 # 路由
  │   ├── store                  # 全局 store管理
  │   ├── styles                 # 全局样式
  │   ├── utils                  # 全局公用方法
  │   ├── views                  # views 所有页面
  │   ├── App.vue                # 入口页面
  │   ├── main.js                # 入口文件 加载组件 初始化等
  │   └── permission.js          # 权限管理
  ├── .env.xxx                   # 环境变量配置
  ├── .eslintrc.js               # eslint 配置项
  ├── .babelrc                   # babel-loader 配置
  ├── .travis.yml                # 自动化CI配置
  ├── vue.config.js              # vue-cli 配置
  ├── postcss.config.js          # postcss 配置
  └── package.json               # package.json
  ```

#### 5.1.2、plop快速生成

+ 首先查看模板是否是自己所需要的内容

+ 模板介绍

+ plop-templates模板介绍

  + form表单模块

    + api：生成调用api的**方法**（这里的方法是以及通过sdk离的axios封装后的）

    + config：核心json文件，通过这个生成内容离需要的值

      ```json
      {
          "form": [
              { "label": "值", "key": "value" },
              { "label": "备注", "key": "note" }
          ],
          "edit": [
              { "label": "值", "key": "value" },
              { "label": "备注", "key": "note" }
          ],
          "formName": "测试",
          "Api": "Test",
          "api": "test"
      }
      ```

      

    + edit：生成表单的编辑组件模块

    + search：生成表单的搜索组件模块

    + prompt：plop的核心文件，通过这个文件，传输数据给模板

      ```js
      const { notEmpty } = require('../utils') // 自己定义的一个工具方法-后面会说
      // 导入数据
      const formJson = require('./config.json')
      
      module.exports = {
        description: '自动按模板生成 表单', // 描述这个generate的作用
        prompts: [
          {
            type: 'input', // 问题的类型
            name: 'fileName', // 问题对应得到答案的变量名，可以在acitons中使用该变量
            message: '请输入模块名:', // 在命令行中的问题
            validate: notEmpty('fileName') // 验证输入的值，notEmpty自定义的工具方法里验证
          }
          // 这里可以多个，代表多个问题，依次执行
          /**    {
            type: 'input', // 问题的类型
            name: 'pathName2', // 问题对应得到答案的变量名，可以在acitons中使用该变量
            message: '文件名称2' // 在命令行中的问题
          }**/
        ],
        // 执行的动作
        actions: (data) => {
          // 这里可以通过data获取输入的fileName
          const actions = [
            // 创建index.js文件
            {
              type: 'add', // 操作类型 添加文件
              path: `src/${data.fileName}/index.vue`, // 添加的文件的路径
              templateFile: 'plop-templates/form/index.hbs', // 模版文件的路径（***这里就是想要生成的模板）
              data: {
                form: formJson.form,
                formName: formJson.formName,
                api: formJson.api,
                Api: formJson.Api
              }
            },
            // 创建edit文件
            {
              type: 'add', // 操作类型 添加文件
              path: `src/${data.fileName}/component/edit-${data.fileName}.vue`, // 添加的文件的路径
              templateFile: 'plop-templates/form/edit.hbs', // 模版文件的路径（***这里就是想要生成的模板）
              data: {
                edit: formJson.edit,
                formName: formJson.formName,
                api: formJson.api,
                Api: formJson.Api
              }
            },
            // 创建search文件
            {
              type: 'add', // 操作类型 添加文件
              path: `src/${data.fileName}/component/search-${data.fileName}.vue`, // 添加的文件的路径
              templateFile: 'plop-templates/form/search.hbs', // 模版文件的路径（***这里就是想要生成的模板）
              data: {
                form: formJson.form,
                formName: formJson.formName,
                api: formJson.api,
                Api: formJson.Api
              }
            },
            // 创建api文件
            {
              type: 'add', // 操作类型 添加文件
              path: `src/${data.fileName}/${data.fileName}.js`, // 添加的文件的路径
              templateFile: 'plop-templates/form/api.hbs', // 模版文件的路径（***这里就是想要生成的模板）
              data: {
                api: formJson.api,
                formName: formJson.formName,
                Api: formJson.Api
              }
            }
          ]
          return actions
        }
      }
      
      ```

  + formLang：和form模块类似，带有一点点lang的国际化

  + spring：后端代码模块

    + config

      ```json
      {
          "entity": [
              { "label": "值", "key": "value", "Key": "Value", "type": "String", "nullable": "false" },
              { "isLast": true, "label": "备注", "key": "note", "Key": "Note", "type": "String", "nullable": "false" }
          ],
          "Module": "Test",
          "module": "test"
      }
      
      ```

      > 注意：
      >
      > + 这里的isLast是必须有的，判断是否是最后一个属性值

    + entity：模板-后端实体类

    + dao：模板-后端的数据库操作，这里使用了JPA实现

    + service：模板-后端接口服务

    + controller：模板-后端接口

    + prompt：核心数据传输与动作
    
    > 注意:
    >
    > + 这里生成所有代码都是在vue-admin项目中的src里,生成后端代码后,需自行复制倒vue-admin-service项目中

+ 使用plop模板还需要在跟目录创建一个，主文件plopfile.js

  ```js
  const formGenerator = require('./plop-templates/form/prompt') // 生成表单模块
  const formLangGenerator = require('./plop-templates/formLang/prompt') // 生成表单模块-国际化
  const springGenerator = require('./plop-templates/spring/prompt') // 后端4大模块
  
  module.exports = function(plop) {
      plop.setGenerator('form', formGenerator)
      plop.setGenerator('formLang', formLangGenerator)
      plop.setGenerator('spring', springGenerator)
      // 如果模块多个，启动时，控制台可以选择
  }
  ```

+ 最后启动plop生成器

  1. package.json配置启动

     ```json
     "scripts": {
         "plop": "plop"
       },
     ```

  2. 启动:npm run plop

  3. 此时需要选择模块-选择form

  4. 给生成模块文件取名

+ 最后附上，hbs文件的基本操作

  + 简单获取值

    ```handlebars
    {{obj}} or {{obj.key}}
    ```

  + 循环遍历

    ```handlebars
    {{#each list}}{{this.key}}{{/each}}
    ```

    > 循环list，list的每个对象值默认是this

  + 添加判断显示

    ```handlebars
    {{#if this.isLast}}是{{else}}否{{/if}}
    ```

    > 注意的是else后面才是否定的值

### 5.2、启动vue-admin-service后端

#### 5.2.1、文件目录结构

```sh
├── target                      # 打包文件
├── public                     # 静态资源
│   │── favicon.ico            # favicon图标
│   └── index.html             # html模板
├── src                        # 源代码
│   ├── main>java>包名         # 所有请求
│   │    ├── aop			 # aop切入
│   │    ├── config			 # spring配置文件
│   │    ├── entity			 # 实体类文件
│   │    ├── dao			 # 数据库操作层（这里使用JPA）
│   │    ├── service		 # 服务层
│   │    ├── controller		 # 接口层
│   │    ├── utils		 	 # 工具包
│   │    ├── xxxApplication.java	 # 启动文件
├── pom.xml                     # 依赖文件
├──...
```

> 注意：代码只是模板，可以根据自己项目修改

### 5.3、启动swagger-to-sdk，axios生成器

#### 5.3.1、文件目录结构

```sh
├── src                        # 源代码
│   ├── example                # 测试swagger.json的静态文件
│   ├── template               # 模板文件
│   ├── codegen.js             # 模板替换
│   ├── test.js             # 入口启动文件
│   ├── index              	# 功能与test.js一致
│   ├── parse                # 解析器
├── postcss.config.js          # postcss 配置
└── package.json               # package.json
```

> 注意：
>
> + node项目，启动index和test一个效果
> + 该模板以及解析器都是根据自己项目写的
> + 启动好项目后**xxxx:8602/toSdk?swaggerPath=http://localhost:xx/项目/v2/api-docs**
>   + 这里注意传入的**是本地服务能够访问到的swagger的json地址/v2/adpi.docs是swagger默认的**

#### 5.3.2、生成后的sdk使用

+ 文件简述

  ```js
  //-- 使用axios访问接口模板代码,自己定义
  ```
  
  > 注意：
  >
  > + 这个只是模板-可以根据自己项目后端的接口访问习惯定
  
+ 为了让前端更方便使用，将此文件，上传到npm

+ 前端使用

  + index.js

    ```js
    
    export function setApi(http, setBaseUrl, path) {
        setBaseUrl('/api' + path)
        http().interceptors.request.use(
            config => {
                // 拦截器，为后端每一个请求加上authorization
                return config
            },
            error => {
                return Promise.reject(error)
            }
        )
        // 拦截器 401
        http().interceptors.response.use(
            response => {
                return response
            },
            async (res) => {
                return Promise.reject(response) // 返回接口返回的错误信息
            }
        )
    }
    
    ```

  + 模板使用

    ```js
    import { getAxiosInstance, setDomain, xxxSaveUser } from 'xxxx'
    import { setApi } from './index'
    // 模块导入时就设置好监听器以及请求头
    setApi(getAxiosInstance, setDomain, '')
    
    // 保存用户
    const XxxSaveUser = params => {
        return xxxSaveUser(params)
    }
    
    export default {
        XxxSaveUser
    }
    
    ```

  + 界面调用

    ```js
    import { XxxSaveUser } from '@/api/user'
    XxxSaveUser({prarms})
        .then(res=>{
    
    })
        .catch(e=>{
    
    })
    ```

    

  