package com.wyh.Service;

import com.wyh.entity.Article;
import org.springframework.data.domain.Sort.Direction;

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
    public List<Article> list(Article s_article, Integer page, Integer pageSize, Direction direction, String... properties);

    /**
     * 查询所有帖子
     * @return
     */
    public List<Article> listAll();

    /**
     * 根据条件查询总记录数
     * @param s_article
     * @return
     */
    public Long getTotal(Article s_article);

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public Article get(Integer id);

    /**
     * 添加或者修改帖子
     * @param article
     */
    public void save(Article article);

    /**
     * 根据id删除帖子信息
     * @param id
     */
    public void delete(Integer id);
}
