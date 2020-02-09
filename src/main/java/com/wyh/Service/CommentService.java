package com.wyh.Service;

import com.wyh.entity.Comment;

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

}
