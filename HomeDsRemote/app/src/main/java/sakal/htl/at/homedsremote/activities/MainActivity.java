package sakal.htl.at.homedsremote.activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sakal.htl.at.homedsremote.R;
import sakal.htl.at.homedsremote.fragments.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_main);

            fragment=new MainFragment();
            manager
                    .beginTransaction()
                    .add(R.id.fragment_main, fragment)
                    .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
