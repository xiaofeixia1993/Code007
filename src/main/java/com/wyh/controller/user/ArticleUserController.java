package com.wyh.controller.user;

import com.wyh.Service.ArticleService;
import com.wyh.entity.Article;
import com.wyh.entity.User;
import com.wyh.util.DateUtil;
import com.wyh.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户中心-资源帖子控制器
 * @author wyh
 */
@Controller
@RequestMapping("/user/article")
public class ArticleUserController {

    @Autowired
    private ArticleService articleService;

    @Value("${articleImageFilePath}")
    private String articleImageFilePath;

    /**
     * Layui编辑器图片上传处理
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/uploadImage")
    public Map<String, Object> uploadImage(MultipartFile file) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();//获取文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));//获取文件的后缀
            String newFileName = DateUtil.getCurrentDateStr() + suffixName;//新文件名
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(articleImageFilePath + DateUtil.getCurrentDatePath() + newFileName));
            map.put("code", 0);
            map.put("msg", "上传成功！");
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("src", "/image/" + DateUtil.getCurrentDatePath() + newFileName);
            map2.put("title", newFileName);
            map.put("data", map2);
        }
        return map;
    }

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
