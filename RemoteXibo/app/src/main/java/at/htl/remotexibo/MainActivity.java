package at.htl.remotexibo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOGTAG = MainActivity.class.getSimpleName();
    private static final String CLIENT_ID = "vwmpjcZ6wrYmNShhOXVQsW54N8PJulK6AEhfRVeF";
    private static final String CLIENT_SECRET = "Z93XJGgZli3yMA0gYdtJjt5qYPEi2zdMMeddzsYbMMWj5AetOuMIfNAn4kAcNtY1GoYSt9dKjfSVIGRGFHnYbf9GEcrgVibliZRNVCPZyvH1cgDJT8vJywAhoWKGQG2wSjOnViXGIqwuOQTi4ojPgX1ZHK4m6sgpbx1micAkY6e7L7xLly7h2gKEHScXOEIhfF9jAmMFxvK1fqQv9o6vsTJCNsEbfRiEKQYYSzkCqfIya9YFWmTAfykGgGsrj0";
    private static final String AUTHORIZE_URL = "http://10.0.2.2:9090/api/authorize/access_token";
    private Future<String> TOKEN;

    private TextView textViewAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAccessToken = findViewById(R.id.access_token);

        final OkHttpHandler handler = new OkHttpHandler();
        ExecutorService executor = Executors.newCachedThreadPool();

        TOKEN = executor.submit(new Callable<String>() {
            public String call() {
                try {
                    return handler.execute(new URL(AUTHORIZE_URL)).get().getString("access_token");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(LOGTAG, "No Token was got!!!");
                return "noToken";
            }
        });

        // get time from xibo
  //      executeRequest(new LinkedList<String>(), "http://10.0.2.2:9090/api/displaygroup?displaygroupid=7/version");

        LinkedList<String> param = new LinkedList<>();
        //param.add("displayGroupId");
        //param.add("7");
        param.add("layoutId");
        param.add("14");
        param.add("changeMode");
        param.add("replace");

        executeRequest(param, "http://10.0.2.2:9090/api/displayGroup/7/action/changeLayout");

/*
        // get layout by layout id
        LinkedList<String> params = new LinkedList<>();
        params.add("envelop");
        params.add("14");
        params.add("embed");
        params.add("regions,playlists,widgets");
        executeRequest(params, "http://10.0.2.2:9090/api/layout");*/
    }

    private void executeRequest(LinkedList<String> params, String url) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        for (int i = 0; i < params.size() - 1; i = i + 2) {
            urlBuilder.addQueryParameter(params.get(i), params.get(i + 1));
        }

        try {
            urlBuilder.addQueryParameter("access token", TOKEN.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        URL finalUrl = urlBuilder.build().url();
        Log.i(LOGTAG, "FinalUrl: " + finalUrl.toString());

        final Request request = new Request.Builder()
                .get()
                .url(finalUrl)
                .build();
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
                    textViewAccessToken.setText(resp);
                }
            }
        };
        client.newCall(request).enqueue(result);

    }

    //https://community.xibo.org.uk/t/1-8-0-rc1-xibo-api-angularjs-http-request-400-error/8371/4
    private class OkHttpHandler extends AsyncTask<URL, Void, JSONObject> {

        OkHttpClient client = new OkHttpClient();
        String access_token;

        @Override
        protected JSONObject doInBackground(URL... urls) {

            String stringBody = "client_id=" + CLIENT_ID
                    + "&client_secret=" + CLIENT_SECRET
                    + "&grant_type=client_credentials";
            System.out.println(stringBody);
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), stringBody);


            Request request = new Request.Builder()
                    .url(urls[0])
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .build();

            Log.i(LOGTAG, "**************************************************************************************************");
            Log.i(LOGTAG, request.headers().toString());
            Log.i(LOGTAG, "-------------------------------------------------------------------------------------------------");
            Log.i(LOGTAG, request.body().toString());
            Log.i(LOGTAG, "**************************************************************************************************");

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response == null) {
                Log.e(LOGTAG, "********************** Response is NULL");
                return null;
            } else {
                Log.i(LOGTAG, "********************** Response erhalten - StatusCode " + response.code() + " " + response.message());
            }

            JSONObject json = null;
            try {
                String responseData = response.body().string();

                json = new JSONObject(responseData);
                access_token = json.getString("access_token");
                Log.i(LOGTAG, "Access Token: " + access_token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
    }
}

