package com.wyh.repository;

import com.wyh.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 友情链接Respository接口
 * @author wyh
 */
public interface LinkRepository extends JpaRepository<Link, Integer>, JpaSpecificationExecutor<Link> {

    /**
     * 根据id查询资源类别
     * @param id
     * @return
     */
    @Query(value = "select * from t_link where id=?1",nativeQuery = true)
    public Link getById(Integer id);
}
