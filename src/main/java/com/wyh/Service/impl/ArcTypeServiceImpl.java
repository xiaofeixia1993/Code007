package com.wyh.Service.impl;

import com.wyh.Service.ArcTypeService;
import com.wyh.entity.ArcType;
import com.wyh.repository.ArcTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private ArcTypeRepository arcTypeRespository;

    @Override
    public List<ArcType> listAll(Direction direction, String... properties) {
        Sort sort = Sort.by(direction, properties);
        return arcTypeRespository.findAll(sort);
    }

    @Override
    public List<ArcType> list(Integer page, Integer pageSize, Direction direction, String... properties) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<ArcType> pageArcType = arcTypeRespository.findAll(pageable);
        return pageArcType.getContent();
    }

    @Override
    public Long getTotal() {
        return arcTypeRespository.count();
    }

    @Override
    public ArcType get(Integer id) {
        return arcTypeRespository.getById(id);
    }

    @Override
    public void save(ArcType arcType) {
        arcTypeRespository.save(arcType);
    }

    @Override
    public void delete(Integer id) {
        arcTypeRespository.deleteById(id);
    }
}
