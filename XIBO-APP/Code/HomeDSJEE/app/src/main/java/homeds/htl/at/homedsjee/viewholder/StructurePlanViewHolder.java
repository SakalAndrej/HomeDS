package homeds.htl.at.homedsjee.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import homeds.htl.at.homedsjee.activity.MainActivity;
import homeds.htl.at.homedsjee.entity.Structure;

/**
 * Created by Felix on 20.02.2018.
 */

public class StructurePlanViewHolder extends RecyclerView.ViewHolder {
    TextView t1,t2;
    public StructurePlanViewHolder(View itemView) {
        super(itemView);
        t1 =  itemView.findViewById(android.R.id.text1);
        t2 = itemView.findViewById(android.R.id.text2);;
    }

        public void updateUI(final Structure structure){
            t1.setText(String.valueOf(structure.getName()));
            t2.setText(String.valueOf( "Unterstrukturen: " + structure.getSubStructuresCount()));

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });
        }
}
