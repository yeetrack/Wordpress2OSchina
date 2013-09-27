package com.yeetrack.blog2oschina;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-9-27
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 * Httpclient设置，Cookie管理等
 */
public class HttpClientTool
{
    private static HttpClientTool httpClientTool = null;
    private static CloseableHttpClient httpClient = null;
    private static String userAgent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)";
    private HttpClientTool()
    {
    }
    public static synchronized HttpClientTool getInstance()
    {
        if(httpClientTool == null)
            httpClientTool = new HttpClientTool();
        return httpClientTool;
    }

    /**
     * 获取HttpClient
     * @return
     */
    public CloseableHttpClient getHttpClient()
    {
        if(httpClient == null)
        {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }

    /**
     * 获取默认的userAgent
     */
    public String getUserAgent()
    {
        return userAgent;
    }
}
