package com.thftgr.werp;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Werp {

    boolean generator(String referrer_id) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        String installId = new Werp().rand(11, false);

        JsonObject data = new JsonObject();
        data.addProperty("key", (new Werp().rand(43, false) + "="));
        data.addProperty("install_id", installId);
        data.addProperty("fcm_token", installId + ":APA91b" + new Werp().rand(134, false));
        data.addProperty("referrer", referrer_id);
        data.addProperty("warp_enabled", false);
        data.addProperty("tos", new Werp().iosdate());
        data.addProperty("type", "Android");
        data.addProperty("locale", "ko-KR");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data.toString());

        Request request = new Request.Builder()
                .url("https://api.cloudflareclient.com/v0a" + new Werp().rand(3, true) + "/reg")
                .addHeader("'Content-Type'", " 'application/json; charset=UTF-8',")
                .addHeader("'Host'", " 'api.cloudflareclient.com',")
                .addHeader("'Connection'", " 'Keep-Alive',")
                .addHeader("'Accept-Encoding'", " 'gzip',")
                .addHeader("'User-Agent'", " 'okhttp/3.12.1'")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        client.connectionPool().evictAll();
        System.out.println(response.code());
        return response.body().string().contains(referrer_id);
    }



    String rand(int length, boolean intOnly) {
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            int rIndex = rnd.nextInt(3);
            if (intOnly) rIndex = 2;
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();

    }

    String iosdate() {
        String iostime = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        return iostime.substring(0, iostime.length() - 1) + "+09:00";
    }


}
