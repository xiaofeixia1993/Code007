package com.wyh.Service.impl;

import com.wyh.Service.CommentService;
import com.wyh.entity.Comment;
import com.wyh.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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

    @Override
    public List<Comment> list(Comment s_comment, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Comment> pageComment = commentRepository.findAll(new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (s_comment != null) {
                    if (s_comment.getState() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("state"), s_comment.getState()));
                    }
                    if (s_comment.getArticle() != null && s_comment.getArticle().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("article").get("id"), s_comment.getArticle().getId()));
                    }
                }
                return predicate;
            }
        }, pageable);
        return pageComment.getContent();
    }

    @Override
    public Long getTotal(Comment s_comment) {
        long count = commentRepository.count(new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (s_comment != null) {
                    if (s_comment.getState() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("state"), s_comment.getState()));
                    }
                    if (s_comment.getArticle() != null && s_comment.getArticle().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("article").get("id"), s_comment.getArticle().getId()));
                    }
                }
                return predicate;
            }
        });
        return count;
    }
}
