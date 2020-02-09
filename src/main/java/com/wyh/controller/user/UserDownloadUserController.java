package com.wyh.controller.user;

import com.wyh.Service.UserDownloadService;
import com.wyh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 用户-用户下载控制器
 * @author wyh
 */
@Controller
@RequestMapping("/user/userDownload")
public class UserDownloadUserController {

    @Autowired
    private UserDownloadService userDownloadService;

    /**
     * 判断资源是否下载过
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/exist")
    public boolean exist(Integer id, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("currentUser");
        Integer count = userDownloadService.getCountByUserIdAndArticleId(user.getId(), id);
        if (count > 0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断用户积分是否足够下载这个资源
     * @param points
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/enough")
    public boolean enough(Integer points, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("currentUser");
        if (user.getPoints() >= points) {
            return true;
        }else {
            return false;
        }
    }
}
