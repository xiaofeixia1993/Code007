package com.wyh.respository;

import com.wyh.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 资源Respository接口
 * @author wyh
 */
public interface ArticleRespository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {
}
