package com.wyh.Service;

import com.wyh.entity.UserDownload;

/**
 * 用户下载Service接口
 * @author wyh
 */
public interface UserDownloadService {

    /**
     * 查询某个用户下载某个资源的次数
     * @param userId
     * @param articleId
     * @return
     */
    public Integer getCountByUserIdAndArticleId(Integer userId, Integer articleId);

    /**
     * 添加或者修改用户下载信息
     * @param userDownload
     */
    public void save(UserDownload userDownload);
}
