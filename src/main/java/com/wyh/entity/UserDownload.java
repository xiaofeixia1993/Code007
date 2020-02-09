package com.wyh.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户下载实体
 * @author wyh
 */
@Entity
@Table(name = "t_userDownload")
public class UserDownload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//编号

    @ManyToOne
    @JoinColumn(name = "articleId")
    private Article article;//下载资源

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;//下载用户

    @Temporal(TemporalType.TIMESTAMP)
    private Date downloadDate;//下载日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }
}
