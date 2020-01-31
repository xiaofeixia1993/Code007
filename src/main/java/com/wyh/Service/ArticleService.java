package com.wyh.Service;

import com.wyh.entity.Article;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 资源Service接口
 * @author wyh
 */
public interface ArticleService {

    /**
     * 根据条件分页查询资源信息
     * @param s_article
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Article> list(Article s_article, Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 根据条件查询总记录数
     * @param s_article
     * @return
     */
    public Long getTotal(Article s_article);
}
