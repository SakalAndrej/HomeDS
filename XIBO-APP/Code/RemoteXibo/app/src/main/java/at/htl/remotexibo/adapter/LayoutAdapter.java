package at.htl.remotexibo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.LinkedList;
import java.util.List;

import at.htl.remotexibo.R;
import at.htl.remotexibo.entity.Layout;
import at.htl.remotexibo.viewholder.LayoutViewHolder;

public class LayoutAdapter extends RecyclerView.Adapter<LayoutViewHolder> {
    private List<Layout> layouts = new LinkedList<>();

    public LayoutAdapter(LinkedList<Layout> layouts) {
        this.layouts = layouts;
    }

    @Override
    public LayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_group_card_view, parent, false);
        return new LayoutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LayoutViewHolder holder, int position) {
        holder.updateUI(layouts.get(position));
    }

    @Override
    public int getItemCount() {
        return layouts.size();
    }
}
