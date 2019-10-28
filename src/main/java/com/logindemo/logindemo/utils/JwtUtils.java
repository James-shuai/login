package com.logindemo.logindemo.utils;
import com.logindemo.logindemo.system.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
/**
 * jwt 工具类
 * @date 2019/10/24 9:38
 */
public class JwtUtils {

    public static final String SUBJECT="test";//签名发行者

    public static final String APPSECRET="logindemo";//签名

    /**
     * 生成token
     * @return
     */
    public static String genJsonWebToken(UserEntity userEntity){
        String token="";
        if (userEntity!=null){
            token=Jwts.builder()
                    .setSubject(SUBJECT)//发行者
                    .claim("username",userEntity.getUsername())
                    .claim("realName",userEntity.getRealname())
                    .setIssuedAt(new Date())//发行日期
                    .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();//签名
        }else {
            token="";
        }
        return token;
    }

    public static void main(String[] args) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setRealname("管理员");
        System.out.println(genJsonWebToken(userEntity));
    }


}
