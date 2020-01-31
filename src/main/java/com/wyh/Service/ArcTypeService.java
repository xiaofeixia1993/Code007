package com.wyh.Service;

import com.wyh.entity.ArcType;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * 资源类别Service接口
 * @author wyh
 */
public interface ArcTypeService {

    /**
     * 查询所有资源类别
     * @param direction
     * @param properties
     * @return
     */
    public List<ArcType> listAll(Direction direction, String...properties);
}
