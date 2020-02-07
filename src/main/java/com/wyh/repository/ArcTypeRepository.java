package com.wyh.repository;

import com.wyh.entity.ArcType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 资源类型Respository接口
 * @author wyh
 */
public interface ArcTypeRepository extends JpaRepository<ArcType, Integer>, JpaSpecificationExecutor<ArcType> {

    /**
     * 根据id查询资源类别
     * @param id
     * @return
     */
    @Query(value = "select * from t_arc_type where id=?1",nativeQuery = true)
    public ArcType getById(Integer id);
}
