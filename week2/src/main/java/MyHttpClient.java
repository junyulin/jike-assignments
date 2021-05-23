import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 使用 httpClient 访问 api
 *
 * @author LinJn
 * @since 2021/5/21 19:05
 */
public class MyHttpClient {

    private final CloseableHttpClient httpClient;

    private MyHttpClient() {
        httpClient = HttpClientBuilder.create().build();
    }

    public String getAsString(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return EntityUtils.toString(response.getEntity());
        }
    }

    public static void main(String[] args) throws IOException {
        MyHttpClient httpClient = new MyHttpClient();
        String asString = httpClient.getAsString("http://localhost:8082");
        System.out.println(asString);
    }
}