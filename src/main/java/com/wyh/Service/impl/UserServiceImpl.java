package com.wyh.Service.impl;

import com.wyh.Service.UserService;
import com.wyh.entity.User;
import com.wyh.repository.UserRepository;
import com.wyh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 用户Service实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> list(User s_user, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<User> pageUser = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (s_user != null) {
                    if (StringUtil.isNotEmpty(s_user.getUserName())) {
                        predicate.getExpressions().add(cb.like(root.get("userName"), "%" + s_user.getUserName().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(s_user.getEmail())) {
                        predicate.getExpressions().add(cb.like(root.get("email"), "%" + s_user.getEmail().trim() + "%"));
                    }
                }
                return predicate;
            }
        }, pageable);
        return pageUser.getContent();
    }

    @Override
    public Long getTotal(User s_user) {
        long count = userRepository.count(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (s_user != null) {
                    if (StringUtil.isNotEmpty(s_user.getUserName())) {
                        predicate.getExpressions().add(cb.like(root.get("userName"), "%" + s_user.getUserName().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(s_user.getEmail())) {
                        predicate.getExpressions().add(cb.like(root.get("email"), "%" + s_user.getEmail().trim() + "%"));
                    }
                }
                return predicate;
            }
        });
        return count;
    }
}
