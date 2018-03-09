package homeds.htl.at.homedsjee.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import homeds.htl.at.homedsjee.activity.MainActivity;
import homeds.htl.at.homedsjee.activity.MainActivityBottomNavigation;
import homeds.htl.at.homedsjee.entity.DataSetDataField;

/**
 * Created by Felix on 07.02.2018.
 */

public class NewsViewholder extends RecyclerView.ViewHolder {

    TextView t1,t2;
    public NewsViewholder(View itemView) {
        super(itemView);
        t1 = itemView.findViewById(android.R.id.text1);
        t2 = itemView.findViewById(android.R.id.text2);


    }

    public void updateUI(final DataSetDataField news){


        t1.setText(String.valueOf(news.getTitle()));
        t2.setText(String.valueOf(news.getFromDate() +" - "+ news.getToDate()));



        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivityBottomNavigation.getInstance().openEditNewsFragment(news);

            }
        });

    }
}
