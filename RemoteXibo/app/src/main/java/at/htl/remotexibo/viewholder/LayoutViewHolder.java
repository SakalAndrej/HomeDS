package at.htl.remotexibo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import at.htl.remotexibo.entity.Layout;

/**
 * Created by Felix on 12.12.2017.
 */

public class LayoutViewHolder extends RecyclerView.ViewHolder {

    TextView tv1, tv2;

    public LayoutViewHolder(View itemView) {
        super(itemView);
        tv1 = itemView.findViewById(android.R.id.text1);
        tv2 = itemView.findViewById(android.R.id.text2);

    }


    public void updateUI(Layout layout){
        tv1.setText(String.format(layout.getLayout()));
        tv2.setText(String.format(layout.getStatusMessage()));
    }
}
