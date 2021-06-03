export default function lang() {
  let lang = navigator.language //浏览器语言判断
  lang = lang.substr(0, 2)
  switch (lang) {
    case 'zh': //中文
      lang = 'zh'
      break
    case 'en': //英文
      lang = 'en'
      break
    default:
      lang = 'en'
  }
  return lang
}
