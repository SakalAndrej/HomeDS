package at.htl.remotexibo.apiClient;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import at.htl.remotexibo.activity.MainActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Felix on 19.12.2017.
 */

public class AuthentificationHandler {

    //handles the authentification process with OAuth2 later on the client id/secret will be provided by params
    private static final String LOGTAG = MainActivity.class.getSimpleName();
    private static final String CLIENT_ID = "vwmpjcZ6wrYmNShhOXVQsW54N8PJulK6AEhfRVeF";
    private static final String CLIENT_SECRET = "Z93XJGgZli3yMA0gYdtJjt5qYPEi2zdMMeddzsYbMMWj5AetOuMIfNAn4kAcNtY1GoYSt9dKjfSVIGRGFHnYbf9GEcrgVibliZRNVCPZyvH1cgDJT8vJywAhoWKGQG2wSjOnViXGIqwuOQTi4ojPgX1ZHK4m6sgpbx1micAkY6e7L7xLly7h2gKEHScXOEIhfF9jAmMFxvK1fqQv9o6vsTJCNsEbfRiEKQYYSzkCqfIya9YFWmTAfykGgGsrj0";

    //10.0.2.2 localhost für android oder 10.0.0.2
    private static final String AUTHORIZE_URL = "http://10.0.2.2:9090/api/authorize/access_token";
    //private static final String AUTHORIZE_URL = "http://localhost:9090/api/authorize/access_token";

    //authenticates at the xibo application
    public Future<String> authenticate(){
        final AuthentificationHandler.OkHttpHandler handler = new AuthentificationHandler.OkHttpHandler();
        ExecutorService executor = Executors.newCachedThreadPool();

        return executor.submit(new Callable<String>() {
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
