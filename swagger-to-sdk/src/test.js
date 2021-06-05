const api = 'swagger'
const express = require('express')
const app = express() //实例化
const swaggerGen = require('./index')
const jsonData = require(`./example/${api}.json`)
const fs = require('fs')
const path = require('path')
const axios = require('axios')
//通过接口接收swagger json数据
app.get('/toSdk', (req, res) => {
  const { swaggerPath } = req.query
  if (!swaggerPath) res.send({ code: 400, msg: '生成失败-缺少参数' })
  //访问接口,并返回sdk
  axios
    .get(swaggerPath)
    .then((data) => {
      //此时生成sdk
      let opt = {
        swagger: data.data
      }
      //生成代码
      const codeResult = swaggerGen(opt)
      //写出文件
      fs.writeFileSync(path.join(__dirname, `./example/${api}.js`), codeResult)
      //返回用一个 buffer文件
      fs.readFile(path.join(__dirname, `./example/${api}.js`), function (error, file) {
        res.send(file)
      })
    })
    .catch((e) => {
      res.send({ code: 400, msg: '生成失败-' + String(e) })
    })
})
//启动服务
app.listen(8600, () => {
  console.log('swagger-to-sdk:8600')
})