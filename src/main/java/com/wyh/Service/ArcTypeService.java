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

    /**
     * 根据条件分页查询资源类别
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<ArcType> list(Integer page, Integer pageSize, Direction direction, String... properties);

    /**
     * 根据条件查询总记录数
     * @return
     */
    public Long getTotal();

    /**
     * 根据id获取资源类别
     * @param id
     * @return
     */
    public ArcType get(Integer id);

    /**
     * 添加或者修改资源类别
     * @param arcType
     */
    public void save(ArcType arcType);

    /**
     * 根据id删除资源类别
     * @param id
     */
    public void delete(Integer id);
}
