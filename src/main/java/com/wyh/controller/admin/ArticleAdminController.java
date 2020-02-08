package com.wyh.controller.admin;

import com.wyh.Service.ArticleService;
import com.wyh.entity.Article;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员-资源控制器
 * @author wyh
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleAdminController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询资源帖子信息
     * @param s_article
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequiresPermissions(value = {"分页查询资源帖子信息"})
    @RequestMapping("/list")
    public Map<String, Object> list(Article s_article, @RequestParam(value = "page", required = false)Integer page,
                                    @RequestParam(value = "limit", required = false)Integer limit) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Article> articleList = articleService.list(s_article, page, limit, Sort.Direction.DESC, "publishDate");
        Long total = articleService.getTotal(s_article);
        resultMap.put("code", 0);
        resultMap.put("count", total);
        resultMap.put("data", articleList);
        return resultMap;
    }

    /**
     * 跳转到帖子审核页面
     * @param id
     * @return
     */
    @RequiresPermissions(value = {"跳转到帖子审核页面"})
    @RequestMapping("/toReViewArticlePage/{id}")
    public ModelAndView toReViewArticlePage(@PathVariable("id")Integer id) {
        ModelAndView mav = new ModelAndView("admin/reviewArticle");
        mav.addObject("title", "修改帖子页面");
        mav.addObject("article", articleService.get(id));
        return mav;
    }

    /**
     * 修改状态
     * @param article
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequiresPermissions(value = {"修改状态"})
    @RequestMapping("/updateState")
    public Map<String, Object> updateState(Article article) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Article oldArticle = articleService.get(article.getId());
        // TODO 添加一个消息模块
        if (article.getState() == 2) {
            oldArticle.setState(2);
            //TODO 删除redis首页数据缓存
        }else {
            oldArticle.setState(3);
            oldArticle.setReason(article.getReason());
        }
        articleService.save(oldArticle);
        resultMap.put("success", true);
        return resultMap;
    }
}
