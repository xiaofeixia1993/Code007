package com.wyh.Service;

import com.wyh.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 用户Service接口
 * @author wyh
 */
public interface UserService {

    /**
     * 添加或者修改用户信息
     * @param user
     */
    public void save(User user);

    /**
     * 根据用户名查找用户实体
     * @param userName
     * @return
     */
    public User findByUserName(String userName);

    /**
     * 根据邮箱查找用户实体
     * @param email
     * @return
     */
    public User findByEmail(String email);

    /**
     * 根据id查找实体
     * @param id
     * @return
     */
    public User getById(Integer id);

    /**
     * 根据条件分页查询用户信息
     * @param s_user
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<User> list(User s_user, Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 根据条件查询总记录数
     * @param s_user
     * @return
     */
    public Long getTotal(User s_user);
}
