package com.wyh.Service.impl;

import com.wyh.Service.LinkService;
import com.wyh.entity.Link;
import com.wyh.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<Link> list(Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Link> pageLink = linkRepository.findAll(pageable);
        return pageLink.getContent();
    }

    @Override
    public Long getTotal() {
        return linkRepository.count();
    }

    @Override
    public Link get(Integer id) {
        return linkRepository.getById(id);
    }

    @Override
    public void save(Link link) {
        linkRepository.save(link);
    }

    @Override
    public void delete(Integer id) {
        linkRepository.deleteById(id);
    }
}
