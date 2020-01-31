package com.wyh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页或者跳转url控制器
 * @author wyh
 */
@Controller
public class IndexController {

    /**
     * 网站根目录请求
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }
}
