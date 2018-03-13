package homeds.htl.at.homedsjee.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.LinkedList;

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
        return null;
    }

    @Override
    public void onBindViewHolder(DisplayViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
