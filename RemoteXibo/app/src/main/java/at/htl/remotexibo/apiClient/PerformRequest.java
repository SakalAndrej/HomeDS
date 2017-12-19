package at.htl.remotexibo.apiClient;

import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import at.htl.remotexibo.MainActivity;
import at.htl.remotexibo.enums.RequestTypeEnum;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Felix on 19.12.2017.
 */

public class PerformRequest {
    private static final String LOGTAG = PerformRequest.class.getSimpleName();


    /**
     *
     * @param executeType there is a enum with the options GET POST PUT DELETE
     * @param params hash map with the key value pairs of the parameters for the body or the header depending on request type
     * @param url the final request url !!! the url parameters have to be inserted !!! example http://10.0.2.2:9090/api/displaygroup/7/action/changeLayout
     * @param TOKEN access_token for the xibo application
     */
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
        Log.i(LOGTAG, "FinalUrl: " + finalUrl.toString());

        Request.Builder rb = new Request.Builder();
        switch (executeType) {
            case GET:
                rb = rb.get();
                break;
            case PUT:
                rb = rb.put(body);
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
                    Log.i(LOGTAG, response.message());
                    throw new IOException("Unexpected code " + response);
                } else {
                    String resp = response.body().string();
                    Log.i(LOGTAG, "Response Body:" + resp);
                    Log.i(LOGTAG, "code: " + response.code());
                }
            }
        };
        client.newCall(request).enqueue(result);

    }
}
