package homeds.htl.at.homedsjee.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import homeds.htl.at.homedsjee.R;

public class MainActivityBottomNavigation extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_item1:
                    mTextMessage.setText(R.string.title_edit_dataset);
                    return true;
                case R.id.action_item2:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.action_item3:
                    mTextMessage.setText(R.string.title_play_Media);
                    return true;
                case R.id.action_item4:
                    mTextMessage.setText(R.string.title_structure_plan);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_navigation);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
