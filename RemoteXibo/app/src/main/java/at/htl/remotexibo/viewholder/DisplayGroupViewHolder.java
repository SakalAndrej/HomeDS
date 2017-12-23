package at.htl.remotexibo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import at.htl.remotexibo.R;
import at.htl.remotexibo.entity.DisplayGroup;

public class DisplayGroupViewHolder extends RecyclerView.ViewHolder {

    TextView tv1;
    CheckBox cb1;

    public DisplayGroupViewHolder(View itemView) {
        super(itemView);
        tv1 = itemView.findViewById(at.htl.remotexibo.R.id.tvName);
        cb1 = itemView.findViewById(R.id.cbPrimary);

    }


    public void updateUI(DisplayGroup displayGroup){
        tv1.setText(String.format(displayGroup.getDisplayGroupName()));
        cb1.setChecked(true);
    }
}
