package com.wyh.controller.user;

import com.wyh.Service.CommentService;
import com.wyh.entity.Comment;
import com.wyh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *用户-评论控制器
 * @author wyh
 */
@Controller
@RequestMapping("/user/comment")
public class CommentUserController {

    @Autowired
    private CommentService commentService;

    /**
     * 保存评论信息
     * @param comment
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/save")
    private Map<String, Object> save(Comment comment, HttpSession session) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        comment.setCommentDate(new Date());
        comment.setState(0);
        comment.setUser((User) session.getAttribute("currentUser"));
        commentService.save(comment);
        map.put("success", true);
        return map;
    }
}











