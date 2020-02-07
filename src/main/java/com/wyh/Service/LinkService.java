package com.wyh.Service;

import com.wyh.entity.Link;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * 友情链接Service接口
 * @author wyh
 */
public interface LinkService {

    /**
     * 查询所有友情链接
     * @param direction
     * @param properties
     * @return
     */
    public List<Link> listAll(Direction direction, String... properties);

    /**
     * 根据条件分页查询友情链接
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Link> list(Integer page, Integer pageSize, Direction direction, String... properties);

    /**
     * 根据条件查询总记录数
     * @return
     */
    public Long getTotal();

    /**
     * 根据id获取友情链接
     * @param id
     * @return
     */
    public Link get(Integer id);

    /**
     * 添加或者修改友情链接
     * @param link
     */
    public void save(Link link);

    /**
     * 根据id删除友情链接
     * @param id
     */
    public void delete(Integer id);
}
