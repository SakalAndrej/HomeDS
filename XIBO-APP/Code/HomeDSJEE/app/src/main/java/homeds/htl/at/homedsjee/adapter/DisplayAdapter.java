package homeds.htl.at.homedsjee.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.entity.Display;
import homeds.htl.at.homedsjee.viewholder.DisplayViewHolder;

/**
 * Created by Felix on 13.03.2018.
 */

public class DisplayAdapter extends RecyclerView.Adapter<DisplayViewHolder> {
    LinkedList<Display> displays = new LinkedList<>();

    public DisplayAdapter(LinkedList<Display> displays){this.displays = displays;}

    @Override
    public DisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_list_item,parent,false);
        return new DisplayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DisplayViewHolder holder, int position) {
        holder.updateUi(displays.get(position));
    }

    @Override
    public int getItemCount() {
        return displays.size();
    }
}
