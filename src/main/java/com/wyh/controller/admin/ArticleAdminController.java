package com.wyh.controller.admin;

import com.wyh.Service.ArticleService;
import com.wyh.entity.Article;
import com.wyh.lucene.ArticleIndex;
import com.wyh.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import java.io.File;
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

    @Value(value = "${articleImageFilePath}")
    private String articleImageFilePath;

    @Autowired
    private ArticleIndex articleIndex;

    /**
     * 生成所有帖子索引
     * @return
     */
    @ResponseBody
    @RequiresPermissions("生成所有帖子索引")
    @RequestMapping(value = "genAllIndex")
    public boolean genAllIndex() {
        List<Article> articleList = articleService.listAll();
        for (Article article : articleList){
            if (!articleIndex.addIndex(article)){
                return false;
            }
        }
        return true;
    }

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
        mav.addObject("title", "审核帖子页面");
        mav.addObject("article", articleService.get(id));
        return mav;
    }

    /**
     * 跳转到修改帖子页面
     * @param id
     * @return
     */
    @RequiresPermissions(value = {"跳转到修改帖子页面"})
    @RequestMapping("/toModifyArticlePage/{id}")
    public ModelAndView toModifyArticlePage(@PathVariable("id")Integer id) {
        ModelAndView mav = new ModelAndView("admin/modifyArticle");
        mav.addObject("title", "修改帖子页面");
        mav.addObject("article", articleService.get(id));
        return mav;
    }

    /**
     * Layui编辑器图片上传处理
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequiresPermissions(value = {"图片上传"})
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
     * 修改帖子
     * @param article
     * @return
     * @throws Exception
     */
    @RequiresPermissions(value = {"修改帖子"})
    @RequestMapping("/update")
    public ModelAndView update(Article article) throws Exception {
        ModelAndView mav = new ModelAndView("admin/modifyArticleSuccess");
        Article oldArticle = articleService.get(article.getId());
        oldArticle.setName(article.getName());
        oldArticle.setArcType(article.getArcType());
        oldArticle.setContent(article.getContent());
        oldArticle.setDownload1(article.getDownload1());
        oldArticle.setPassword1(article.getPassword1());
        oldArticle.setPoints(article.getPoints());
        if (oldArticle.getState() == 2) {// 当审核通过的时候，需要更新下lucene索引
            // TODO 更新lucene索引
        }
        articleService.save(oldArticle);
        // TODO 删除该帖子的缓存
        mav.addObject("title", "修改帖子成功页面");
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

    /**
     * 根据id删除帖子信息
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequiresPermissions(value = {"删除帖子"})
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
    @RequiresPermissions(value = {"删除帖子"})
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

    @ResponseBody
    @RequiresPermissions(value = {"修改热门状态"})
    @RequestMapping("/updateHotState")
    public Map<String, Object> updateHotState(Article article) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Article oldArticle = articleService.get(article.getId());
        oldArticle.setHot(article.isHot());
        articleService.save(oldArticle);
        // TODO 修改该帖子对应的redis索引
        resultMap.put("success", true);
        return resultMap;
    }
}
