package at.htl.remotexibo.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import at.htl.remotexibo.R;
import at.htl.remotexibo.entity.DisplayGroup;

public class DisplayGroupViewHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvDescr;
    CheckBox cb1;

    public DisplayGroupViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(at.htl.remotexibo.R.id.tvName);
        tvDescr = itemView.findViewById(R.id.tvDisplayGroupDescription);
        cb1 = itemView.findViewById(R.id.cbPrimary);
    }


    public void updateUI(DisplayGroup displayGroup){
        tvName.setText(String.format(displayGroup.getDisplayGroupName()));
        tvDescr.setText(String.format(displayGroup.getDisplayGroupDescription()));
        cb1.setChecked(false);

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(itemView.getContext(), "Selected: " + displayGroup.displayGroupName, Toast.LENGTH_LONG).show();
            }
        });
    }
}
