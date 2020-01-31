package com.wyh.respository;

import com.wyh.entity.ArcType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 资源类型Respository接口
 * @author wyh
 */
public interface ArcTypeRespository extends JpaRepository<ArcType, Integer>, JpaSpecificationExecutor<ArcType> {
}
