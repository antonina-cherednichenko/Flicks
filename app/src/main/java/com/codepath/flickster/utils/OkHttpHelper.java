package com.codepath.flickster.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by tonya on 3/9/17.
 */

public class OkHttpHelper {

    private static OkHttpClient client = new OkHttpClient();

    public static void httpCall(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(callback);
    }
}
