package at.htl.remotexibo.activity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import at.htl.remotexibo.R;
import at.htl.remotexibo.apiClient.AuthentificationHandler;
import at.htl.remotexibo.apiClient.RequestHelper;
import at.htl.remotexibo.entity.DisplayGroup;
import at.htl.remotexibo.enums.RequestTypeEnum;
import at.htl.remotexibo.fragment.DisplayGroupRecyclerViewFragment;
import at.htl.remotexibo.fragment.HomeViewFragment;
import at.htl.remotexibo.fragment.LayoutRecyclerViewFragment;

public class MainActivity extends AppCompatActivity implements HomeViewFragment.OnFragmentInteractionListener, DisplayGroupRecyclerViewFragment.OnFragmentInteractionListener, LayoutRecyclerViewFragment.OnFragmentInteractionListener{

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

            RequestHelper rh = new RequestHelper();
            rh.executeRequest(RequestTypeEnum.GET,null,"http://10.0.2.2:9090/api/clock",TOKEN.get());

            HashMap<String,String> params = new HashMap<String,String>();
            params.put("layoutId","15");
            params.put("changeMode","replace");

            rh.executeRequest(RequestTypeEnum.POST,params,"http://10.0.2.2:9090/api/displaygroup/7/action/changeLayout",TOKEN.get());


             //get layout by layout id
            params = new HashMap<String,String>();
            /*params.put("envelop","14");
            params.put("embed","regions,playlists,widgets");*/
            rh.executeRequest(RequestTypeEnum.POST,params,"http://10.0.2.2:9090/api/layout", TOKEN.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeViewFragment homeViewFragment = new HomeViewFragment();
        fragmentManager.beginTransaction().add(R.id.container_main, homeViewFragment, null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void openDisplayGroupRecyclerViewFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DisplayGroupRecyclerViewFragment displayGroupRecyclerViewFragment = new DisplayGroupRecyclerViewFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, displayGroupRecyclerViewFragment, null).commit();
    }


    public void openLayoutRecyclerViewFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        LayoutRecyclerViewFragment layoutRecyclerViewFragment = new LayoutRecyclerViewFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, layoutRecyclerViewFragment, null).commit();
    }
}

