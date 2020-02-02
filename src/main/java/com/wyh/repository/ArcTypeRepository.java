package com.wyh.repository;

import com.wyh.entity.ArcType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 资源类型Respository接口
 * @author wyh
 */
public interface ArcTypeRepository extends JpaRepository<ArcType, Integer>, JpaSpecificationExecutor<ArcType> {
}