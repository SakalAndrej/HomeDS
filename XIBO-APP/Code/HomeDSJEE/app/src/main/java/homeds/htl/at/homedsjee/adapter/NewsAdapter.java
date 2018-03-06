package homeds.htl.at.homedsjee.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import homeds.htl.at.homedsjee.entity.DataSetDataField;
import homeds.htl.at.homedsjee.viewholder.NewsViewholder;

/**
 * Created by Felix on 10.02.2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewholder> {

    LinkedList<DataSetDataField> news = new LinkedList<DataSetDataField>();

    public NewsAdapter(LinkedList<DataSetDataField> news) {
        this.news = news;
    }

    @Override
    public NewsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);
        return new NewsViewholder(v);
    }

    @Override
    public void onBindViewHolder(NewsViewholder holder, int position) {
        holder.updateUI(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
