package com.wyh.controller;

import com.wyh.Service.ArticleService;
import com.wyh.entity.Article;
import com.wyh.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页或者跳转url控制器
 * @author wyh
 */
@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    /**
     * 网站根目录请求
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("index");
        request.getSession().setAttribute("tMenu", "t_0");
        Article s_article = new Article();
        s_article.setState(2);
        List<Article> indexArticleList = articleService.list(s_article, 1, 20, Sort.Direction.DESC, "publishDate");
        Long total = articleService.getTotal(s_article);
        s_article.setHot(true);
        List<Article> indexHotArticleList = articleService.list(s_article, 1, 43, Sort.Direction.DESC, "publishDate");
        mav.addObject("articleList", indexArticleList);
        mav.addObject("hotArticleList", indexHotArticleList);
        mav.addObject("title", "首页");
        mav.addObject("pageCode", PageUtil.genPagination("/article/list", total, 1, 20, ""));
        return mav;
    }
}
