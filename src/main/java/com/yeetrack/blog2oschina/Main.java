package com.yeetrack.blog2oschina;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-9-27
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 * 默认加载项目根目录中的wordpress导出文件 wordpress.2013-09-27.xml，会解析此文件，将文件中的文章全部发表到oschina博客
 * 还会加载项目根目录中的article.html文件，该文件应该是用markdown导出的html文件，也会同步该篇文章到oschina
 */
public class Main
{
    public static void main(String[] args)
    {
        OSchina oSchina = new OSchina();
        XmlParser xmlParser = new XmlParser();
        //登陆oschina
        if(!oSchina.loginchina())
        {
            System.out.println("登陆失败");
            return;
        }
        //解析xml文件，获取文章列表
        List<Article> articleList = xmlParser.xml2SArticle();

        //解析xml文件，发表文章。该xml包含很多文章
        int count = 0;
        for(Article article : articleList)
        {
            if (oSchina.sendBlog2Oschina(article))
            {
                System.out.println("成功--->"+article.getTitle());
                count++;
            }
            else
            {
                System.out.println("失败--->"+article.getTitle());
            }
            //不要发表过快，避免对oschina服务器过大压力，可能会被断开连接
            try
            {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("成功发表" + count + "篇文章");
        if (articleList.size() > count)
        {
            System.out.println("失败"+(articleList.size()-count)+"篇");
        }

        //解析html文件，发表文章，该html文件中只包含一篇文章
        HtmlParser htmlParser = new HtmlParser();
        Article article = htmlParser.htmlParser();
        if (oSchina.sendBlog2Oschina(article))
            System.out.println("成功--->"+article.getTitle());
        else
            System.out.println("失败--->"+article.getTitle());
    }
}
