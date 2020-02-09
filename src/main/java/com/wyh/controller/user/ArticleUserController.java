package com.wyh.controller.user;

import com.wyh.Service.ArticleService;
import com.wyh.Service.UserDownloadService;
import com.wyh.Service.UserService;
import com.wyh.entity.Article;
import com.wyh.entity.User;
import com.wyh.entity.UserDownload;
import com.wyh.util.DateUtil;
import com.wyh.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserDownloadService userDownloadService;

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
     * 根据条件分页查询资源帖子信息
     * @param s_article
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(Article s_article, HttpSession session, @RequestParam(value = "page", required = false)Integer page,
                                    @RequestParam(value = "limit", required = false)Integer limit) throws Exception{
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user = (User) session.getAttribute("currentUser");
        s_article.setUser(user);
        List<Article> articleList = articleService.list(s_article, page, limit, Sort.Direction.DESC, "publishDate");
        Long count = articleService.getTotal(s_article);
        resultMap.put("code", 0);
        resultMap.put("count", count);
        resultMap.put("data", articleList);
        return resultMap;
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
     * 跳转到帖子管理页面
     * @return
     */
    @RequestMapping("/toArticleManagePage")
    public ModelAndView toArticleManagePage() {
        ModelAndView mav = new ModelAndView("articleManage");
        mav.addObject("title", "帖子管理");
        return mav;
    }

    /**
     * 跳转到帖子修改页面
     * @return
     */
    @RequestMapping("/toModifyArticlePage/{id}")
    public ModelAndView toModifyArticlePage(@PathVariable("id")Integer id) {
        ModelAndView mav = new ModelAndView("modifyArticle");
        mav.addObject("title", "帖子修改页面");
        mav.addObject("article", articleService.get(id));
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

    /**
     * 更新帖子
     * @param article
     * @return
     * @throws Exception
     */
    @RequestMapping("/update")
    public ModelAndView update(Article article) throws Exception {
        ModelAndView mav = new ModelAndView("modifyArticleSuccess");
        Article oldArticle = articleService.get(article.getId());
        oldArticle.setName(article.getName());
        oldArticle.setArcType(article.getArcType());
        oldArticle.setContent(article.getContent());
        oldArticle.setDownload1(article.getDownload1());
        oldArticle.setPassword1(article.getPassword1());
        oldArticle.setPoints(article.getPoints());
        if (oldArticle.getState() == 3) {// 假如审核未通过，用户点击修改的话，则重新审核
            oldArticle.setState(1);
        }
        articleService.save(oldArticle);
        if (oldArticle.getState() == 2) {
            // TODO 修改Lucene索引
            // redis缓存删除这个索引
        }
        mav.addObject("title", "修改帖子成功页面");
        return mav;
    }


    /**
     * 根据id删除帖子信息
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Map<String, Object> delete(Integer id) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // TODO 删除用户下载帖子信息
        // TODO 删除该帖子下的所有评论
        articleService.delete(id);
        // TODO 删除索引
        // TODO 删除redis索引
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 多选删除
     * @param ids
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/deleteSelected")
    public Map<String, Object> deleteSelected(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            // TODO 删除用户下载帖子信息
            // TODO 删除该帖子下的所有评论
            articleService.delete(Integer.parseInt(idsStr[i]));
            // TODO 删除索引
            // TODO 删除redis索引
        }
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 跳转到资源下载页面
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/toDownLoadPage/{id}")
    public ModelAndView toDownLoadPage(@PathVariable(value = "id")Integer id, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("user/downloadPage");
        UserDownload userDownload = new UserDownload();
        Article article = articleService.get(id);

        User user = (User) session.getAttribute("currentUser");
        boolean isDownload = false;//是否下载过
        Integer count = userDownloadService.getCountByUserIdAndArticleId(user.getId(), id);
        if (count > 0) {
            isDownload = true;
        }
        if (!isDownload) {
            if (user.getPoints() - article.getPoints() < 0) {//用户积分是否够
                return null;
            }

            //扣积分
            user.setPoints(user.getPoints() - article.getPoints());
            userService.save(user);

            //给分享人加积分
            User articleUser = article.getUser();
            articleUser.setPoints(articleUser.getPoints() + article.getPoints());
            userService.save(articleUser);

            //保存用户下载信息
            userDownload.setArticle(article);
            userDownload.setUser(user);
            userDownload.setDownloadDate(new Date());
            userDownloadService.save(userDownload);
        }
        mav.addObject("article", articleService.get(id));
        return mav;
    }

    /**
     * 跳转到Vip资源下载页面
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/toVipDownLoadPage/{id}")
    public ModelAndView toVipDownLoadPage(@PathVariable(value = "id")Integer id, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("user/downloadPage");
        UserDownload userDownload = new UserDownload();
        Article article = articleService.get(id);

        User user = (User) session.getAttribute("currentUser");

        if (!user.isVip()) {//判断是否是Vip
            return null;
        }

        boolean isDownload = false;//是否下载过
        Integer count = userDownloadService.getCountByUserIdAndArticleId(user.getId(), id);
        if (count > 0) {
            isDownload = true;
        }
        if (!isDownload) {
            //保存用户下载信息
            userDownload.setArticle(article);
            userDownload.setUser(user);
            userDownload.setDownloadDate(new Date());
            userDownloadService.save(userDownload);
        }
        mav.addObject("article", articleService.get(id));
        return mav;
    }
}
