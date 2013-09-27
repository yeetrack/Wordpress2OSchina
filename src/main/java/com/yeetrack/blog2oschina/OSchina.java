package com.yeetrack.blog2oschina;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-9-27
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public class OSchina
{
    private CloseableHttpClient httpClient;

    public OSchina()
    {
        httpClient = HttpClientTool.getInstance().getHttpClient();
    }

    /**
     * 登陆开源中国
     * @return
     */
    public boolean loginchina()
    {
        HttpGet loginGet = new HttpGet("https://www.oschina.net/home/login");
        loginGet.addHeader("User-Agent", HttpClientTool.getInstance().getUserAgent());
        try
        {
            HttpResponse loginGetResponse = httpClient.execute(loginGet);
            loginGet.releaseConnection();
            String result = EntityUtils.toString(loginGetResponse.getEntity());
            //System.out.println(result);
            List<BasicNameValuePair> loginParams = new ArrayList<BasicNameValuePair>();
            loginParams.add(new BasicNameValuePair("email", UserInfo.getUsername()));
            loginParams.add(new BasicNameValuePair("pwd", UserInfo.getPassword()));
            loginParams.add(new BasicNameValuePair("save_login", "1"));
            UrlEncodedFormEntity loginFormEntity = new UrlEncodedFormEntity(loginParams, "utf-8");

            HttpPost loginPost = new HttpPost("http://www.oschina.net/action/user/hash_login");
            loginPost.addHeader("User-Agent", HttpClientTool.getInstance().getUserAgent());
            loginPost.setEntity(loginFormEntity);
            HttpResponse postResponse = httpClient.execute(loginPost);
            loginPost.releaseConnection();

            //验证是否通过验证
            boolean flag = false;
            for(Header header : postResponse.getAllHeaders())
            {
                if(header.getValue().contains("Domain=.oschina.net"))
                {
                    flag = true;
                    break;
                }
            }
            return flag;

        } catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return false;
    }

    /**
     * 发送博客,发送表单为multipart/form-data
     */
    public boolean sendBlog2Oschina(Article article)
    {
        //oschina开启了防止csrf攻击功能，需要首先获取user-code这个参数
        HttpGet uIdGet = new HttpGet("http://www.oschina.net/home/go?page=blog");
        uIdGet.addHeader("User-Agent", HttpClientTool.getInstance().getUserAgent());
        uIdGet.addHeader("Referer", "http://www.oschina.net/");
        String userCode = null;
        try
        {
            HttpResponse uIdGetResponse = httpClient.execute(uIdGet);

            String userCodeResult = EntityUtils.toString(uIdGetResponse.getEntity());
            int userCodeStart = userCodeResult.indexOf("user_code");
            int userCOdeEnd = userCodeResult.indexOf("\"", userCodeStart+10);
            userCode = userCodeResult.substring(userCodeStart+10, userCOdeEnd);
            //System.out.println(userCode);
            uIdGet.releaseConnection();


        } catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        HttpPost blogPost = new HttpPost("http://my.oschina.net/action/blog/save?as_top=0");
        blogPost.addHeader("User-Agent", HttpClientTool.getInstance().getUserAgent());
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("user_code", userCode));
        params.add(new BasicNameValuePair("draft", "0"));
        if(article.getTitle() == null || article.getTitle().toString().trim().equals(""))
            article.setTitle("文章标题解析为空，请手动修改");
        params.add(new BasicNameValuePair("title", article.getTitle()));
        params.add(new BasicNameValuePair("catalog", "331499"));
        params.add(new BasicNameValuePair("abstracts", ""));
        params.add(new BasicNameValuePair("content", article.getContent()));
        params.add(new BasicNameValuePair("content_type", "0"));
        //处理标签列表
        String tags = "";
        if(article.getTagList()!=null)
        {
            for(String tag : article.getTagList())
                tags = tags + tag +",";
        }
        params.add(new BasicNameValuePair("tags", tags));
        params.add(new BasicNameValuePair("music_url", ""));
        params.add(new BasicNameValuePair("type", "1"));
        params.add(new BasicNameValuePair("origin_url", article.getLink()));
        params.add(new BasicNameValuePair("privacy", "0"));
        params.add(new BasicNameValuePair("deny_comment", "0"));
        params.add(new BasicNameValuePair("suto_content", "1"));

        try
        {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
            blogPost.setEntity(formEntity);
            HttpResponse response = httpClient.execute(blogPost);
            blogPost.releaseConnection();
            if(EntityUtils.toString(response.getEntity()).contains("id"))
                return true;
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return false;
    }
}
