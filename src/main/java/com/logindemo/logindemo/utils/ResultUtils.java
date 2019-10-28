package com.logindemo.logindemo.utils;

/**
 * 定义返回工具类
 * @date 2019/10/24 11:58
 */
public class ResultUtils {

    /**
     * 成功返回
     * @param data
     * @param msg
     * @return
     */
    public static Object success(Object data,String msg){
        Result result = new Result();
        result.setState(true);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
    public static Object success(String msg){
        Result result = new Result();
        result.setState(true);
        result.setMsg(msg);
        return result;
    }
    public static Object success(Object data){
        Result result = new Result();
        result.setState(true);
        result.setData(data);
        return result;
    }
    public static Object success(){
        Result result = new Result();
        result.setState(true);
        return result;
    }

    /**
     * 错误返回
     * @return
     */
    public static Object error(){
        Result result = new Result();
        result.setState(false);
        return result;
    }
    public static Object error(String msg){
        Result result = new Result();
        result.setState(false);
        result.setMsg(msg);
        return result;
    }


    public static class Result{
        private boolean state;//返回状态
        private Object data;//返回数据
        private String msg;//返回信息

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
