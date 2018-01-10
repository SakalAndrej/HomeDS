package at.htl.utils;

import at.htl.enums.RequestTypeEnum;
import okhttp3.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestHelper {

    public void executeRequest(RequestTypeEnum executeType, HashMap<String, String> params, String url, String TOKEN) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        RequestBody body = null;

        if (executeType == RequestTypeEnum.GET || executeType == RequestTypeEnum.DELETE) {

            if (params != null && params.size() > 0) {
                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry p = (Map.Entry) it.next();
                    urlBuilder.addQueryParameter(p.getKey().toString(), p.getValue().toString());
                }

            }
            urlBuilder.addQueryParameter("access token", TOKEN);

        } else {
            String stringbody = "access token=" + TOKEN;

            if (params != null && params.size() > 0) {
                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry p = (Map.Entry) it.next();
                    stringbody += "&" + p.getKey().toString() + "=" + p.getValue().toString();
                }
            }

            body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), stringbody);

        }

        URL finalUrl = urlBuilder.build().url();

        Request.Builder rb = new Request.Builder();
        switch (executeType) {
            case GET:
                rb = rb.get();
                break;
            case PUT:
                rb = rb.put(body);
                rb = rb.addHeader("Authorization", "Bearer " + TOKEN);
                break;
            case POST:
                rb = rb.post(body);
                break;
            case DELETE:
                rb = rb.delete();
                break;
        }

        final Request request = rb.url(finalUrl).build();
        Callback result = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String resp = response.body().string();
                }
            }
        };
        client.newCall(request).enqueue(result);
    }
}

