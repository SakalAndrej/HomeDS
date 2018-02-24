package homeds.htl.at.homedsjee.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import homeds.htl.at.homedsjee.entity.Structure;
import homeds.htl.at.homedsjee.viewholder.StructurePlanViewHolder;

/**
 * Created by Felix on 20.02.2018.
 */

public class StructurePlanAdapter extends RecyclerView.Adapter<StructurePlanViewHolder> {

    LinkedList<Structure> structures = new LinkedList<>();

    public StructurePlanAdapter(LinkedList<Structure> structures){this.structures = structures;}
    @Override
    public StructurePlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new StructurePlanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StructurePlanViewHolder holder, int position) {
        holder.updateUI(structures.get(position));
    }

    @Override
    public int getItemCount() {
        return structures.size();
    }
}
