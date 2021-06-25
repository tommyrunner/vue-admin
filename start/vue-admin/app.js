//导入express
const express = require('express')
 const app = express()
 //托管的静态资源
 app.use(express.static('./dist'))
 app.listen(8600, () => {
     console.log("服务器启动成功!:vue-admin:8600")
 })