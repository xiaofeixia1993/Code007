package com.wyh.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * 评论实体
 * @author wyh
 */
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//编号

    @ManyToOne
    @JoinColumn(name = "articleId")
    private Article article;//帖子

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;//评论人

    @Column(length = 1000)
    private String content;//评论内容

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;//评论日期

    private Integer state;//审核状态0  1审核通过   2审核未通过

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
