package com.wyh.Service;

import com.wyh.entity.Article;
import com.wyh.entity.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 评论Service接口
 * @author wyh
 */
public interface CommentService {

    /**
     * 添加或者修改评论
     * @param comment
     */
    public void save(Comment comment);

    /**
     * 根据条件分页查询评论信息
     * @param s_comment
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Comment> list(Comment s_comment, Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 根据条件获取总记录数
     * @param s_comment
     * @return
     */
    public Long getTotal(Comment s_comment);
}
