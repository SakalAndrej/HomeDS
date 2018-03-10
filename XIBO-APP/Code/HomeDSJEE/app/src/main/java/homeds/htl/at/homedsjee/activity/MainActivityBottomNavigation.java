package homeds.htl.at.homedsjee.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.entity.DataSetDataField;
import homeds.htl.at.homedsjee.fragment.HomeScreenFragment;
import homeds.htl.at.homedsjee.fragment.MediaOverviewFragment;
import homeds.htl.at.homedsjee.fragment.NewsEditFragment;
import homeds.htl.at.homedsjee.fragment.NewsOverviewFragment;
import homeds.htl.at.homedsjee.fragment.StructureDetailFragment;
import homeds.htl.at.homedsjee.fragment.StructurePlanFragment;

public class MainActivityBottomNavigation extends AppCompatActivity implements HomeScreenFragment.OnFragmentInteractionListener,
        NewsEditFragment.OnFragmentInteractionListener, NewsOverviewFragment.OnFragmentInteractionListener,
        StructurePlanFragment.OnFragmentInteractionListener, MediaOverviewFragment.OnFragmentInteractionListener,
        StructureDetailFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private BottomNavigationView navbar;
    public static MainActivityBottomNavigation mainActivityBottomNavigation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_navigation);
        mainActivityBottomNavigation = this;

        openHomeScreenFragment();

        navbar = findViewById(R.id.navigation_bar);


        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.editDatasetNavBar:
                        openNewsOverview();
                        break;
                    case R.id.playMediaNavBar:
                        openMediaOverviewFragment();
                        break;
                    case R.id.homeScreenNavBar:
                        openHomeScreenFragment();
                        break;
                    case R.id.structurePlanNavBar:
                        openStructurePlanFragment();
                        break;
                }
                return false;

            }
        });
    }

    public static MainActivityBottomNavigation getInstance(){return mainActivityBottomNavigation;}

    public void openNewsOverview() {
        FragmentManager fm = getSupportFragmentManager();
        NewsOverviewFragment nov = new NewsOverviewFragment();
        fm.beginTransaction().replace(R.id.container_display, nov, null).addToBackStack(null).commit();
    }

    public void openMediaOverviewFragment() {
        MediaOverviewFragment mediaOverviewFragment = new MediaOverviewFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_display, mediaOverviewFragment, null).addToBackStack(null).commit();
    }


    public void openStructurePlanFragment(){
        StructurePlanFragment structurePlanFragment = new StructurePlanFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_display, structurePlanFragment,null).addToBackStack(null).commit();
    }

    public void openHomeScreenFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeScreenFragment hsf = new HomeScreenFragment();
        fragmentManager.beginTransaction().add(R.id.container_display, hsf, null).commit();
    }


    public void openStructureDetailFragment(Bundle bundle){
        StructureDetailFragment structureDetailFragmen = new StructureDetailFragment();
        structureDetailFragmen.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_display,structureDetailFragmen,null).addToBackStack(null).commit();

    }


    public void openEditNewsFragment(DataSetDataField news){


        Bundle bundle = new Bundle();

        bundle.putSerializable("data",news);
        NewsEditFragment newsEditFragment = new NewsEditFragment();
        newsEditFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_display, newsEditFragment,"actEdit").addToBackStack("actEdit").commit();

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
