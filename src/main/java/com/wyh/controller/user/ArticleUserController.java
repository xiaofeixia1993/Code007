package com.wyh.controller.user;

import com.wyh.Service.ArticleService;
import com.wyh.entity.Article;
import com.wyh.entity.User;
import com.wyh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户中心-资源帖子控制器
 * @author wyh
 */
@Controller
@RequestMapping("/user/article")
public class ArticleUserController {

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转到发布帖子页面
     * @return
     */
    @RequestMapping("/toPublishArticlePage")
    public ModelAndView toPublishArticlePage() {
        ModelAndView mav = new ModelAndView("publicArticle");
        mav.addObject("title", "发布帖子页面");
        return mav;
    }

    /**
     * 添加帖子
     * @param article
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add(Article article, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("publishArticleSuccess");
        User user = (User) session.getAttribute("currentUser");
        article.setPublishDate(new Date());
        article.setUser(user);
        article.setState(1);
        article.setView(StringUtil.randomInteger());
        articleService.save(article);
        mav.addObject("title", "发布帖子成功页面");
        return mav;
    }
}
