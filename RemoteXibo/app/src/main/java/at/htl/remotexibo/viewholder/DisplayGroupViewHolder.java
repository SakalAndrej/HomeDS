package at.htl.remotexibo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import at.htl.remotexibo.entity.DisplayGroup;
import at.htl.remotexibo.entity.Layout;

public class DisplayGroupViewHolder extends RecyclerView.ViewHolder {

    TextView tv1, tv2;

    public DisplayGroupViewHolder(View itemView) {
        super(itemView);
        tv1 = itemView.findViewById(android.R.id.text1);
        tv2 = itemView.findViewById(android.R.id.text2);

    }


    public void updateUI(DisplayGroup displayGroup){
        tv1.setText(String.format(displayGroup.getDisplayGroupName()));
        tv2.setText(String.format(displayGroup.getDisplayGroupDescription()));
    }
}
