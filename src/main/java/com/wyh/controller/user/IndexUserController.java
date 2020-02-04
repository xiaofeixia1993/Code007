package com.wyh.controller.user;

import com.wyh.Service.ArticleService;
import com.wyh.entity.Article;
import com.wyh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 *用户页面跳转控制器
 * @author wyh
 */
@Controller
public class IndexUserController {
    
    @Autowired
    private ArticleService articleService;

    /**
     * 跳转后台中心页面
     * @return
     */
    @RequestMapping("/toUserCenterPage")
    public ModelAndView toUserCenterPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("userCenter");
        User user = (User) session.getAttribute("currentUser");
        Article s_article = new Article();
        s_article.setUser(user);
        s_article.setUseful(false);
        Long total = articleService.getTotal(s_article);
        mav.addObject("title", "用户中心页面");
        session.setAttribute("unUserfulArticleCount", total);
        return mav;
    }
}
