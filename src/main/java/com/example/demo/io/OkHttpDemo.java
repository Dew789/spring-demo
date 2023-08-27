package com.example.demo.io;

import okhttp3.*;

public class OkHttpDemo {

    public static void main(String[] args) throws Exception{
        String url = "http://wwww.baidu.com";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }

    }


}
