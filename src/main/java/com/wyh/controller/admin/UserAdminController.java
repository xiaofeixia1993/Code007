package com.wyh.controller.admin;

import com.wyh.Service.UserService;
import com.wyh.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员-用户控制器
 * @author wyh
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    @Autowired
    private UserService userService;

    /**
     * 根据条件分页查询用户信息
     * @param s_user
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequiresPermissions(value = {"分页查询用户信息"})
    @RequestMapping("/list")
    public Map<String, Object> list(User s_user,  @RequestParam(value = "page", required = false)Integer page,
                                    @RequestParam(value = "limit", required = false)Integer limit) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<User> userList = userService.list(s_user, page, limit, Sort.Direction.DESC, "registerDate");
        Long total = userService.getTotal(s_user);
        resultMap.put("code", 0);
        resultMap.put("count", total);
        resultMap.put("data", userList);
        return resultMap;
    }

}
