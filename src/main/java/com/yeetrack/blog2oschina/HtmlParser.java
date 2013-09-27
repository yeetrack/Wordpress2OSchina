package com.yeetrack.blog2oschina;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-9-28
 * Time: 上午1:50
 * To change this template use File | Settings | File Templates.
 * 同步一篇文章到oschina
 * 要求：该文章以article.html文件的形式保存在项目根目录，最好是使用markdown导出的html文件
 */
public class HtmlParser
{
    private String htmlFile = "article.html";
    /**
     * 一般来说，linux系统使用utf-8编码，windows中文系统是gbk2312，如果乱码，请自行修改
     */
    private String encoding = "utf-8";

    public Article htmlParser()
    {
        File file = new File(htmlFile);
        if(!file.exists())
            return null;
        Article article = new Article();
        try
        {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while((line=bufferedReader.readLine())!=null)
            {
                stringBuffer.append(line);
            }
            reader.close();
            bufferedReader.close();

            //解析html，有第三方类库htmlparser，这里比较简单，直接手动操作了
            String result = stringBuffer.toString();
            //获取title,这里获取h标签中的文本当做标题，还有可能<title></title>标签中的
            String title = null;
            for(int i=1;i<=5;i++)
            {
                int titleStart = result.indexOf("<h"+i+">");
                int titleEnd = result.indexOf("</h"+i+">", titleStart);
                if(titleStart != -1 && titleEnd != -1)
                {
                    title = result.substring(titleStart+4, titleEnd);
                    break;
                }
            }
            if(title != null)
                article.setTitle(title);
            else
                article.setTitle("未找到标题,请手动修改");

            //提取body标签中的内容作为文章内容
            int bodyStart = result.indexOf("<body>");
            int bodyEnd = result.indexOf("</body>", bodyStart+6);
            if(bodyStart!=-1 && bodyEnd!=-1)
                article.setContent(result.substring(bodyStart+6, bodyEnd));
            else
                article.setContent("文章连内容都没有么，确定？");

            //自定义功能，添加原文链接
            int siteStart = result.indexOf("<yeetrack-url>");
            int siteENd = result.indexOf("</yeetrack-url>", siteStart);
            if(siteStart!=-1 && siteENd!=-1)
                article.setLink(result.substring(siteStart+14, siteENd));


        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return article;
    }
}
