package com.wyh.Service.impl;

import com.wyh.Service.UserDownloadService;
import com.wyh.entity.UserDownload;
import com.wyh.repository.UserDownloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 用户下载Service实现类
 * @author wyh
 */
@Service("userDownloadService")
@Transactional
public class UserDownloadServiceImpl implements UserDownloadService {

    @Autowired
    private UserDownloadRepository userDownloadRepository;

    @Override
    public Integer getCountByUserIdAndArticleId(Integer userId, Integer articleId) {
        return userDownloadRepository.getCountByUserIdAndArticleId(userId, articleId);
    }

    @Override
    public void save(UserDownload userDownload) {
        userDownloadRepository.save(userDownload);
    }
}
