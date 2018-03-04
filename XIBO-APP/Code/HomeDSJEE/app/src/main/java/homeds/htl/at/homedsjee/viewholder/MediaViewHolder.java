package homeds.htl.at.homedsjee.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.activity.MainActivity;
import homeds.htl.at.homedsjee.apiClient.RequestHelper;
import homeds.htl.at.homedsjee.entity.DataSetDataField;
import homeds.htl.at.homedsjee.entity.Media;
import homeds.htl.at.homedsjee.enumeration.RequestTypeEnum;

/**
 * Created by Felix on 04.03.2018.
 */

public class MediaViewHolder extends RecyclerView.ViewHolder{

    TextView title,mediaType;
    ImageButton play;


    public MediaViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.tvTitle);
        play = itemView.findViewById(R.id.ibPlay);
        mediaType = itemView.findViewById(R.id.tvMediaType);
    }

    public void updateUI(final Media media){
        title.setText(media.getName());
        mediaType.setText(media.getMediaType());
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> params = new HashMap<>();
                params.put("id",media.getMediaId().toString());
                RequestHelper rh = new RequestHelper();
                String url = "http://10.0.2.2:8080/homeds/rs/media/play";
                rh.executeRequest(RequestTypeEnum.GET,params,url);
            }
        });


    }
}
