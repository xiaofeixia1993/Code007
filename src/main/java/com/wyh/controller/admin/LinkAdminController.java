package com.wyh.controller.admin;

import com.wyh.Service.LinkService;
import com.wyh.entity.Link;
import com.wyh.init.InitSystem;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员-友情链接控制器
 * @author wyh
 */
@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Resource
    private LinkService linkService;

    @Resource
    private InitSystem initSystem;

    /**
     * 分页查询友情链接信息
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequiresPermissions(value = {"分页查询友情链接"})
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "page", required = false)Integer page,
                                    @RequestParam(value = "limit", required = false)Integer limit) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Link> linkList = linkService.list(page, limit, Sort.Direction.ASC, "sort");
        Long total = linkService.getTotal();
        resultMap.put("code", 0);
        resultMap.put("count", total);
        resultMap.put("data", linkList);
        return resultMap;
    }

    /**
     * 添加或者修改类别信息
     * @param link
     * @param request
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = {"添加或者修改友情链接"})
    @RequestMapping("/save")
    public Map<String, Object> save(Link link, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        linkService.save(link);
        initSystem.loadData(request.getServletContext());
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 删除类别信息
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = {"删除友情链接"})
    @RequestMapping("/delete")
    public Map<String, Object> delete(Integer id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        linkService.delete(id);
        initSystem.loadData(request.getServletContext());
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据id查询友情链接实体
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequiresPermissions(value = {"根据id查询友情链接实体"})
    @RequestMapping("/findById")
    public Map<String, Object> findById(Integer id) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Link link = linkService.get(id);
        resultMap.put("link", link);
        resultMap.put("success", true);
        return resultMap;
    }
}
