package at.htl.remotexibo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.LinkedList;

import at.htl.remotexibo.R;
import at.htl.remotexibo.entity.Layout;

public class LayoutViewHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvDescr;
    CheckBox cb1;


    Layout selectedLayout = null;

    public LayoutViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(at.htl.remotexibo.R.id.tvName);
        tvDescr = itemView.findViewById(R.id.tvDisplayGroupDescription);
        cb1 = itemView.findViewById(R.id.cbPrimary);
    }

    public void updateUI(Layout layout){
       tvName.setText(String.format(layout.getLayout()));
       tvDescr.setText(String.format(layout.getLayoutId()));
       cb1.setChecked(false);

       cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    selectedLayout = layout;
                }
                else{
                    selectedLayout = null;
                }
           }
       });
    }
}
