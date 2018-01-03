package at.htl.remotexibo.activity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import at.htl.remotexibo.R;
import at.htl.remotexibo.apiClient.AuthentificationHandler;
import at.htl.remotexibo.apiClient.RequestHelper;
import at.htl.remotexibo.enums.RequestTypeEnum;
import at.htl.remotexibo.fragment.DisplayGroupRecyclerViewFragment;
import at.htl.remotexibo.fragment.DisplayGroupRecyclerViewFragment.OnFragmentInteractionListener;
import at.htl.remotexibo.fragment.HomeViewFragment;

public class MainActivity extends AppCompatActivity implements HomeViewFragment.OnFragmentInteractionListener, DisplayGroupRecyclerViewFragment.OnFragmentInteractionListener{

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

            RequestHelper.getInstance().executeRequest(RequestTypeEnum.GET,null,"http://10.0.2.2:9090/api/clock",TOKEN.get());
            RequestHelper.getInstance().getDisplayGroups();
            HashMap<String,String> params = new HashMap<String,String>();
            params.put("layoutId","15");
            params.put("changeMode","replace");

            RequestHelper.getInstance().executeRequest(RequestTypeEnum.POST,params,"http://10.0.2.2:9090/api/displaygroup/7/action/changeLayout",TOKEN.get());


            // get layout by layout id
            /*params = new HashMap<String,String>();
            params.put("envelop","14");
            params.put("embed","regions,playlists,widgets");
            RequestHelper.getInstance().executeRequest(RequestTypeEnum.POST,params,"http://10.0.2.2:9090/api/layout", TOKEN.get());
            */
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
}

