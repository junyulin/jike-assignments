package io.github.linjn.client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 第二周作业
 * 使用 httpClient 访问 api
 *
 * @author LinJn
 * @since 2021/5/21 19:05
 */
public class MyHttpClient {

    private final CloseableHttpClient httpClient;

    public MyHttpClient() {
        httpClient = HttpClientBuilder
                .create()
                .build();
    }

    public String getAsString(String url, List<Map<String, String>> headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        for (Map<String, String> header : headers) {
            httpGet.addHeader(header.get("name"), header.get("value"));
        }
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return EntityUtils.toString(response.getEntity());
        }
    }

    public static void main(String[] args) throws IOException {
        MyHttpClient httpClient = new MyHttpClient();
        String asString = httpClient.getAsString("http://localhost:8082", Collections.emptyList());
        System.out.println(asString);
    }
}
