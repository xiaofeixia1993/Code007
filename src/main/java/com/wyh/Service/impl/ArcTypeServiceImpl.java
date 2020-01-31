package com.wyh.Service.impl;

import com.wyh.Service.ArcTypeService;
import com.wyh.entity.ArcType;
import com.wyh.respository.ArcTypeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源类别Service实现类
 * @author wyh
 */
@Service("arcTypeService")
public class ArcTypeServiceImpl implements ArcTypeService{

    @Autowired
    private ArcTypeRespository arcTypeRespository;

    @Override
    public List<ArcType> listAll(Direction direction, String... properties) {
        Sort sort = Sort.by(direction, properties);
        return arcTypeRespository.findAll(sort);
    }

}
