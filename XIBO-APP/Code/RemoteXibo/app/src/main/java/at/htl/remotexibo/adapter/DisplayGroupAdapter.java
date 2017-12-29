package at.htl.remotexibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import at.htl.remotexibo.R;
import at.htl.remotexibo.activity.MainActivity;
import at.htl.remotexibo.entity.DisplayGroup;
import at.htl.remotexibo.viewholder.DisplayGroupViewHolder;

public class DisplayGroupAdapter extends RecyclerView.Adapter<DisplayGroupViewHolder> {

    private List<DisplayGroup> displayGroups = new LinkedList<>();

    public DisplayGroupAdapter(LinkedList<DisplayGroup> _displayGroups) {
        this.displayGroups = _displayGroups;
    }

    @Override
    public DisplayGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_group_card_view, parent, false);
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
