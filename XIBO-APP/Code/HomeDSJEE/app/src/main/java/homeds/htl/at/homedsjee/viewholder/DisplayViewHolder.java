package homeds.htl.at.homedsjee.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.activity.MainActivityBottomNavigation;
import homeds.htl.at.homedsjee.entity.Display;

/**
 * Created by Felix on 13.03.2018.
 */

public class DisplayViewHolder extends RecyclerView.ViewHolder {

    TextView tvDisplayName;
    Button btCooseDisplay;

    public DisplayViewHolder(View itemView) {
        super(itemView);
        tvDisplayName = itemView.findViewById(R.id.tvDisplayName);
        btCooseDisplay = itemView.findViewById(R.id.btChooseDisplay);
    }

    public void updateUi(final Display display) {
        tvDisplayName.setText(display.getDisplay());
        btCooseDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityBottomNavigation.getInstance().display = display;
                MainActivityBottomNavigation.getInstance().openMediaOverviewFragment();

            }
        });

    }
}
