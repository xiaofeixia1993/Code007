package com.wyh.Service.impl;

import com.wyh.Service.ArticleService;
import com.wyh.entity.Article;
import com.wyh.repository.ArticleRepository;
import com.wyh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 资源Service实现类
 * @author wyh
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRespository;

    @Override
    public List<Article> list(Article s_article, Integer page, Integer pageSize, Direction direction, String... properties) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Article> pageArticle = articleRespository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (s_article != null) {
                    if (s_article.getName() != null) {
                        predicate.getExpressions().add(cb.like(root.get("name"), "%" + s_article.getName().trim() + "%"));
                    }
                    if (s_article.getState() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("state"), s_article.getState()));
                    }
                    if (s_article.isHot()) {
                        predicate.getExpressions().add(cb.equal(root.get("isHot"), 1));
                    }
                    if (s_article.getArcType() != null && s_article.getArcType().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("arcType").get("id"), s_article.getArcType().getId()));
                    }
                    if (!s_article.isUseful()) {
                        predicate.getExpressions().add(cb.equal(root.get("isUseful"), false));
                    }
                    if (s_article.getUser() != null && s_article.getUser().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("user").get("id"), s_article.getUser().getId()));
                    }
                    if (s_article.getUser() != null && StringUtil.isNotEmpty(s_article.getUser().getUserName())) {
                        predicate.getExpressions().add(cb.like(root.get("user").get("userName"), "%"+s_article.getUser().getUserName()+"%"));
                    }
                }
                return predicate;
            }
        }, pageable);
        return pageArticle.getContent();
    }

    @Override
    public Long getTotal(Article s_article) {
        long count = articleRespository.count(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (s_article != null) {
                    if (s_article.getName() != null) {
                        predicate.getExpressions().add(cb.like(root.get("name"), "%" + s_article.getName().trim() + "%"));
                    }
                    if (s_article.getState() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("state"), s_article.getState()));
                    }
                    if (s_article.isHot()) {
                        predicate.getExpressions().add(cb.equal(root.get("isHot"), 1));
                    }
                    if (s_article.getArcType() != null && s_article.getArcType().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("arcType").get("id"), s_article.getArcType().getId()));
                    }
                    if (!s_article.isUseful()) {
                        predicate.getExpressions().add(cb.equal(root.get("isUseful"), false));
                    }
                    if (s_article.getUser() != null && s_article.getUser().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("user").get("id"), s_article.getUser().getId()));
                    }
                    if (s_article.getUser() != null && StringUtil.isNotEmpty(s_article.getUser().getUserName())) {
                        predicate.getExpressions().add(cb.like(root.get("user").get("userName"), "%"+s_article.getUser().getUserName()+"%"));
                    }
                }
                return predicate;
            }
        });
        return count;
    }

    @Override
    public Article get(Integer id) {
        return articleRespository.getOne(id);
    }

    @Override
    public void save(Article article) {
        articleRespository.save(article);
    }

    @Override
    public void delete(Integer id) {
        articleRespository.deleteById(id);
    }
}
