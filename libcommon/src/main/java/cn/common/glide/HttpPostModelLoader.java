package cn.common.glide;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/30.
 */

public class HttpPostModelLoader implements StreamModelLoader<String> {
    private OnPostLoaderListener listener;

    public HttpPostModelLoader(OnPostLoaderListener listener) {
        this.listener = listener;
    }

    public HttpPostModelLoader() {
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
        HttpPostDataFetcher fetcher = new HttpPostDataFetcher(new GlideUrl(model));
        fetcher.setFetchListener(listener);
        return fetcher;
    }

    public interface OnPostLoaderListener {
        void onLoader(Response response);
    }
}
