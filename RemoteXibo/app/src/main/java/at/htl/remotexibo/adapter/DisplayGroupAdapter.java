package at.htl.remotexibo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import at.htl.remotexibo.entity.DisplayGroup;
import at.htl.remotexibo.viewholder.DisplayGroupViewHolder;

public class DisplayGroupAdapter extends RecyclerView.Adapter<DisplayGroupViewHolder> {

    private List<DisplayGroup> displayGroups = new LinkedList<>();

    public DisplayGroupAdapter(LinkedList<DisplayGroup> _displayGroups) {
        this.displayGroups = _displayGroups;
    }

    @Override
    public DisplayGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new DisplayGroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DisplayGroupViewHolder holder, int position) {
        holder.updateUI(displayGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return displayGroups.size();
    }
}
