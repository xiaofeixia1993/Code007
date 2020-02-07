package com.wyh.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页或者跳转url控制器
 */
@Controller
public class IndexAdminController {

    /**
     * 跳转到管理员主页面
     * @return
     */
    @RequiresPermissions(value = {"进入管理员主页"})
    @RequestMapping("/toAdminUserCenterPage")
    public ModelAndView toAdminUserCenterPage() {
        ModelAndView mav = new ModelAndView("adminUserCenter");
        mav.addObject("title", "管理员主页页面");
        return mav;
    }
}
