package com.yeetrack.blog2oschina;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-9-27
 * Time: 下午10:51
 * To change this template use File | Settings | File Templates.
 * wordpress文章类
 */
public class Article
{
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章链接
     */
    private String link;
    /**
     * 文章发表日期
     */
    private String pubDate;
    /**
     * 文章作者
     */
    private String creator;
    /**
     * 文章描述
     */
    private String description;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 是否允许评论
     */
    private String comment_status;
    /**
     * 文章标签
     */
    private List<String> tagList;
    /**
     * 文章分类
     */
    private String category;

    /**
     * 文章状态
     */
    private String status;

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {

        return status;
    }

    public String getTitle()
    {
        return title;
    }

    public String getLink()
    {
        return link;
    }

    public String getPubDate()
    {
        return pubDate;
    }

    public String getCreator()
    {
        return creator;
    }

    public String getDescription()
    {
        return description;
    }

    public String getContent()
    {
        return content;
    }

    public String getComment_status()
    {
        return comment_status;
    }

    public List<String> getTagList()
    {
        return tagList;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public void setPubDate(String pubDate)
    {
        this.pubDate = pubDate;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setComment_status(String comment_status)
    {
        this.comment_status = comment_status;
    }

    public void setTagList(List<String> tagList)
    {
        this.tagList = tagList;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
}
