package homeds.htl.at.homedsjee.activity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.entity.DataSetDataField;
import homeds.htl.at.homedsjee.fragment.HomeScreenFragment;
import homeds.htl.at.homedsjee.fragment.NewsEditFragment;
import homeds.htl.at.homedsjee.fragment.NewsOverviewFragment;

public class MainActivity extends AppCompatActivity implements HomeScreenFragment.OnFragmentInteractionListener, NewsEditFragment.OnFragmentInteractionListener,NewsOverviewFragment.OnFragmentInteractionListener {

    public static MainActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeScreenFragment hsf = new HomeScreenFragment();
        fragmentManager.beginTransaction().add(R.id.container_main, hsf, null).commit();
        instance = this;


    }
    public static MainActivity getInstance(){
        return instance;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }


    public void openNewsOverview(){
        FragmentManager fm = getSupportFragmentManager();
        NewsOverviewFragment nov = new NewsOverviewFragment();
        fm.beginTransaction().replace(R.id.container_main, nov ,null).commit();
    }

    public void openEditNewsFragment(DataSetDataField news){


        Bundle bundle = new Bundle();

        bundle.putSerializable("data",news);
        NewsEditFragment newsEditFragment = new NewsEditFragment();
        newsEditFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_main, newsEditFragment,null).commit();
    }

}
