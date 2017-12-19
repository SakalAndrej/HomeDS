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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import at.htl.remotexibo.apiClient.AuthentificationHandler;
import at.htl.remotexibo.apiClient.PerformRequest;
import at.htl.remotexibo.enums.RequestTypeEnum;
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

    //10.0.2.2 localhost für android oder 10.0.0.2
    private static final String AUTHORIZE_URL = "http://10.0.2.2:9090/api/authorize/access_token";
    private Future<String> TOKEN;

    private TextView textViewAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAccessToken = findViewById(R.id.access_token);
        AuthentificationHandler authHandler = new AuthentificationHandler();
        TOKEN = authHandler.authenticate();

        //EXAMPLE REQUEST
        //--------------------------------------------------------------------------------------------------------------------------------------------------------------
        //SETUP
        String token = "";
        try {
            token = TOKEN.get().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //POST
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("layoutId","15");
        params.put("changeMode","replace");

        new PerformRequest().executeRequest(RequestTypeEnum.POST,params,"http://10.0.2.2:9090/api/displaygroup/7/action/changeLayout",token);


        //GET

        new PerformRequest().executeRequest(RequestTypeEnum.GET,null,"http://10.0.2.2:9090/api/clock",token);

/*
        // get layout by layout id
        LinkedList<String> params = new LinkedList<>();
        params.add("envelop");
        params.add("14");
        params.add("embed");
        params.add("regions,playlists,widgets");
        executeRequest(params, "http://10.0.2.2:9090/api/layout");*/
    }






}

