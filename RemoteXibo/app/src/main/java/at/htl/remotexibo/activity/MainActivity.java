package at.htl.remotexibo.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import at.htl.remotexibo.R;
import at.htl.remotexibo.apiClient.AuthentificationHandler;
import at.htl.remotexibo.apiClient.RequestHelper;
import at.htl.remotexibo.enums.RequestTypeEnum;
import at.htl.remotexibo.fragment.LayoutRecyclerViewFragment;

public class MainActivity extends AppCompatActivity {

    private Future<String> TOKEN;

    private TextView textViewAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AuthentificationHandler authHandler = new AuthentificationHandler();
        TOKEN = authHandler.authenticate();

        //GET
        try {
            //Change the Layout
            HashMap<String,String> params = new HashMap<String,String>();
            params.put("layoutId","15");
            params.put("changeMode","replace");

            RequestHelper.getInstance().executeRequest(RequestTypeEnum.POST,params,"http://10.0.2.2:9090/api/displaygroup/7/action/changeLayout",TOKEN.get());

            RequestHelper.getInstance().executeRequest(RequestTypeEnum.GET,null,"http://10.0.2.2:9090/api/clock",TOKEN.get());

            // get layout by layout id
            params = new HashMap<String,String>();
            params.put("envelop","14");
            params.put("embed","regions,playlists,widgets");
            RequestHelper.getInstance().executeRequest(RequestTypeEnum.POST,params,"http://10.0.2.2:9090/api/layout", TOKEN.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        LayoutRecyclerViewFragment layoutRecyclerViewFragment = new LayoutRecyclerViewFragment();

        fragmentManager.beginTransaction().add(R.id.container_main, layoutRecyclerViewFragment, null).commit();


    }
}

