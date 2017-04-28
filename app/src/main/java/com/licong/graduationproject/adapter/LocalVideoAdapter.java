package com.licong.graduationproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.licong.graduationproject.R;
import com.licong.graduationproject.bean.LocalVideo;

import java.util.List;

/**
 * Created by licong on 4/28/17.
 */

public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.ViewHolder> {
    private List<LocalVideo>mLocalVideo;

    static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView localVideoImage;
        TextView localVideoName;
        public ViewHolder(View itemView) {
            super(itemView);
            localVideoImage=(ImageView) itemView.findViewById(R.id.collection_image);
            localVideoName=(TextView) itemView.findViewById(R.id.collection_text);
        }
    }
    public LocalVideoAdapter(List<LocalVideo> localVideos){
        mLocalVideo=localVideos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item,parent,false);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocalVideo localVideo=mLocalVideo.get(position);
        holder.localVideoImage.setImageResource(localVideo.getImageId());
        holder.localVideoName.setText(localVideo.getDisplayName());
    }
    @Override
    public int getItemCount() {
        return mLocalVideo.size();
    }
}
