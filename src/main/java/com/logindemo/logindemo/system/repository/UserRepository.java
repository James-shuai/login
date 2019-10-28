package com.logindemo.logindemo.system.repository;

import com.logindemo.logindemo.system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ys
 * @date 2019/10/24 11:39
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * 根据用户名查询是否存在该用户
     * @param username
     * @return
     */
    int countByUsername(String username);


    /**
     * 根据用户名查询密码
     * @param username
     * @return
     */
    @Query(value = "select * from user where username=?1",nativeQuery = true)
    UserEntity getPassword(String username);

}
