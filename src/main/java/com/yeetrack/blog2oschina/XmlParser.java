package com.yeetrack.blog2oschina;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-9-27
 * Time: 下午11:03
 * To change this template use File | Settings | File Templates.
 * 解析wordpress导出的xml文件
 */
public class XmlParser
{
    private String xmlFile = "wordpress.2013-09-27.xml";

    public List<Article> xml2SArticle()
    {
        List<Article> articleList = new ArrayList<Article>();
        File file = new File(xmlFile);
        //如果xml文件不存在，返回空的articleList
        if(!file.exists())
            return articleList;

        System.out.println("+++开始解析xml文件+++");
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            SAXReader saxReader = new SAXReader();

            Document document = saxReader.read(br);
            //root节点是rss节点
            Element rootElement = document.getRootElement();
            //channel节点
            Iterator ite = rootElement.elementIterator();
            Iterator ite2 = ((Element)ite.next()).elementIterator();
            for(int i=1;i<=10;i++)
                ite2.next();
            //开始处理item节点
            while(ite2.hasNext())
            {
                Article article = new Article();
                //文章标签
                List<String> tagList = new ArrayList<String>();
                Element itemRoot = (Element)ite2.next();
                Iterator itemIterator = itemRoot.elementIterator();
                while(itemIterator.hasNext())
                {
                    Element node = (Element)itemIterator.next();
                    String nodeName = node.getName();
                    String nodeData = node.getData().toString();
                    if("title".equals(nodeName))
                        article.setTitle(nodeData);
                    else if("guid".equals(nodeName))
                        article.setLink(nodeData);
                    else if("description".equals(nodeName))
                        article.setDescription(nodeData);
                    else if("encoded".equals(nodeName) && "content".equals(node.getNamespacePrefix()))
                        article.setContent(nodeData);
                    else if("pubDate".equals(nodeName))
                        article.setPubDate(nodeData);
                    else if("creator".equals(nodeName))
                        article.setCreator(nodeData);
                    else if("comment_status".equals(nodeName))
                        article.setComment_status(nodeData);
                    else if("status".equals(nodeName))
                        article.setStatus(nodeData);
                    else if("category".equals(nodeName) && "post_tag".equals(node.attributeValue("domain")))
                        tagList.add(URLDecoder.decode(nodeData, "utf-8"));
                    else if("category".equals(nodeName) && "category".equals(node.attributeValue("domain")))
                        article.setCategory(nodeData);
                }
                article.setTagList(tagList);
                System.out.println("解析文章--->"+article.getTitle());
                articleList.add(article);


            }

        } catch (DocumentException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("+++xml文件解析完毕+++");
        return articleList;
    }
}
