package com.tommy.vueadmin.utils;

import java.util.HashMap;
import java.util.Map;

public class ReturnDateUtil {
    public static int CODE_OK = 200;
    public static int CODE_ERROR = 400;//普通错误
    public static int CODE_ERROR_TOKEN = 403;//普通错误
    public static int CODE_ERROR_PARA = 405;//却少参数
    /**
     * 返回规范
     * @param code 200:成功,400:服务器错误
     * @param msg 提示语
     * @param data 返回对象
     * @return
     */
    public static Map<String,Object> returnData(int code,String msg,Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",code!=CODE_ERROR?data:new HashMap<>());
        return map;
    }
}
