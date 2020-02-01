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

}
