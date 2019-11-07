package com.logindemo.logindemo.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.logindemo.logindemo.system.entity.UserEntity;
import com.logindemo.logindemo.system.repository.UserRepository;
import com.logindemo.logindemo.utils.JwtUtils;
import com.logindemo.logindemo.utils.RedisUtils;
import com.logindemo.logindemo.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ys
 * @date 2019/10/24 11:54
 */
@RestController
@RequestMapping("/api/system")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 新增用户
     * @return
     */
    @RequestMapping("/saveUser")
    public Object saveUser(){
        UserEntity userEntity = new UserEntity();
        //用户名
        userEntity.setUsername("admin");
        //密码 注：使用 Spring Boot 自带加密 对密码进行加密
        userEntity.setPassword( DigestUtils.md5DigestAsHex("123456".getBytes()));
        //真实姓名
        userEntity.setRealname("管理员");
        //创建时间
        userEntity.setCreateTime(new Date());
        //新增用户 这里调用 Spring Data JPA 自带方法进行新增
        UserEntity save = userRepository.save(userEntity);
        //如果不等于 null 返回我们刚刚定义好的工具类
        if (save!=null){
            return ResultUtils.success("操作成功");
        }
        return ResultUtils.error("操作失败");
    }

    /**
     * 登录接口
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody String request){
        JSONObject jsonObject = JSON.parseObject(request);//转 JSON 对象
        if (jsonObject.get("username")==null&&"".equals(jsonObject.get("username").toString())){
            return ResultUtils.error("用户名不能为空");
        }
        if (jsonObject.get("password")==null&&"".equals(jsonObject.get("password").toString())){
            return ResultUtils.error("用户名不能为空");
        }
        String username = jsonObject.get("username").toString();
        String password = jsonObject.get("password").toString();
        if (userRepository.countByUsername(username)>0){//判断用户是否存在
            UserEntity userEntity = userRepository.getPassword(username);//数据库中的密码
            if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(userEntity.getPassword())){//校验密码是否一致
                String token = JwtUtils.genJsonWebToken(userEntity);//得到 Token
                redisUtils.set(token,userEntity.getRealname(),60);//登录成功后 把token放到Redis Key 存 token ，value 存用户真实姓名
                //登陆成功后 把token和真实姓名返回
                Map<String,Object> map = new HashMap<>();
                map.put("realname",userEntity.getRealname());
                map.put("token",token);
                return ResultUtils.success(map,"登录成功");//登录成功

            }
            return ResultUtils.error("密码错误，请重新输入");
        }else {
            return ResultUtils.error("用户名不存在");
        }

    }


    /**
     * 获取所有用户
     * @return
     */
    @RequestMapping("/findAll")
    public Object findAll(){
        List<UserEntity> all = userRepository.findAll();
        for (UserEntity temp:all) {
            temp.setPassword(null);
        }
        return all;
    }

}
