//����express
const express = require('express')
 const app = express()
 //�йܵľ�̬��Դ
 app.use(express.static('./dist'))
 app.listen(8600, () => {
     console.log("�����������ɹ�!:vue-admin:8600")
 })