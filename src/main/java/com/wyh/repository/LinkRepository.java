package com.wyh.repository;

import com.wyh.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 友情链接Respository接口
 * @author wyh
 */
public interface LinkRepository extends JpaRepository<Link, Integer>, JpaSpecificationExecutor<Link> {
}
