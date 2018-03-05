package homeds.htl.at.homedsjee.viewholder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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

        public void updateUI(final JSONObject structure){
            try {
                t1.setText(structure.getString("layout"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    String struct = structure.toString();
                    bundle.putSerializable("actStructure",struct);
                    MainActivity.getInstance().openStructureDetailFragment(bundle);
                }
            });
        }
}
