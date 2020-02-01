package com.wyh.Service.impl;

import com.wyh.Service.LinkService;
import com.wyh.entity.Link;
import com.wyh.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("linkService")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Override
    public List<Link> listAll(Sort.Direction direction, String... properties) {
        Sort sort = Sort.by(direction, properties);
        return linkRepository.findAll(sort);
    }
}
