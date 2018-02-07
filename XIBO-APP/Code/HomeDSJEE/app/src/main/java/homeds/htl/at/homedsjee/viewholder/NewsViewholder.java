package homeds.htl.at.homedsjee.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Felix on 07.02.2018.
 */

public class NewsViewholder extends RecyclerView.ViewHolder {

    TextView t1,t2;
    public NewsViewholder(View itemView) {
        super(itemView);
        t1 =  itemView.findViewById(android.R.layout.simple_list_item_1);
        t2 = itemView.findViewById(android.R.layout.simple_list_item_2);


    }
}
