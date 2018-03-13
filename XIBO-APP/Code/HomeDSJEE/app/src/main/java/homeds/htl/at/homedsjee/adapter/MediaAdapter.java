package homeds.htl.at.homedsjee.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.HashMap;
import java.util.LinkedList;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.activity.MainActivityBottomNavigation;
import homeds.htl.at.homedsjee.apiClient.RequestHelper;
import homeds.htl.at.homedsjee.entity.Media;
import homeds.htl.at.homedsjee.enumeration.RequestTypeEnum;
import homeds.htl.at.homedsjee.viewholder.MediaViewHolder;

/**
 * Created by Felix on 04.03.2018.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaViewHolder> {

    LinkedList<Media> medias = new LinkedList<>();


    public MediaAdapter(LinkedList<Media> medias,RecyclerView recyclerView){this.medias = medias;}
    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_list_item,parent,false);
        return new MediaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        holder.updateUI(medias.get(position));

    }

    @Override
    public int getItemCount() {
        return medias.size();
    }





}
