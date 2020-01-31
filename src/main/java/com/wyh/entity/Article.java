package com.wyh.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * 资源实体
 * @author wyh
 */
@Entity
@Table(name = "t_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//编号

    @Column(length = 200)
    private String name;//资源名称

    private Date publishDate;//发布日期

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;//所属用户

    @ManyToOne
    @JoinColumn(name = "typeId")
    private ArcType arcType;//所属资源类别

    private Integer points;//积分

    @Lob
    @Column(columnDefinition = "longtext")
    private String content;//资源描述

    @Column(length = 200)
    private String download1;//百度云下载地址

    @Column(length = 10)
    private String password1;//密码

    private boolean isHot = false;//是否是热门资源true 是  false否

    private Integer state;//审核状态 1未审核  2审核通过  3审核未通过

    private String reason;//审核未通过原因

    private Date checkDate;//审核日期

    private boolean isUseful = true;//链接资源是否有效  true有效  false无效  默认有效

    private Integer view;//访问次数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArcType getArcType() {
        return arcType;
    }

    public void setArcType(ArcType arcType) {
        this.arcType = arcType;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDownload1() {
        return download1;
    }

    public void setDownload1(String download1) {
        this.download1 = download1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public boolean isUseful() {
        return isUseful;
    }

    public void setUseful(boolean useful) {
        isUseful = useful;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }
}
