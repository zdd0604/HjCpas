package cn.common.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/30.
 */

public class HttpPostDataFetcher implements DataFetcher<InputStream> {
    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    GlideUrl glideUrl;
    HttpPostModelLoader.OnPostLoaderListener listener;

    public HttpPostDataFetcher(GlideUrl glideUrl) {
        this.glideUrl = glideUrl;

    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request.Builder builder = new Request.Builder();
        MediaType mediaTypeStream = MediaType.parse("application/octet-stream");
        RequestBody requestBody = RequestBody.create(mediaTypeStream, "");
        builder.url(glideUrl.toURL()).post(requestBody);
        Response response = client.newCall(builder.build()).execute();
        if (listener != null) {
            listener.onLoader(response);
        }
        return response.body().byteStream();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public String getId() {
        return glideUrl.getCacheKey() + UUID.randomUUID();
    }

    @Override
    public void cancel() {

    }

    public void setFetchListener(HttpPostModelLoader.OnPostLoaderListener listener) {
        this.listener = listener;
    }
}
