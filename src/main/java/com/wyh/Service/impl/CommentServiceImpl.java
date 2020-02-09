package com.wyh.Service.impl;

import com.wyh.Service.CommentService;
import com.wyh.entity.Comment;
import com.wyh.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 评论Service实现类
 * @@author wyh
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
