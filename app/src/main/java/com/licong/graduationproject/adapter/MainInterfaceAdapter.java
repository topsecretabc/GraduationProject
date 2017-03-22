package com.licong.graduationproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.licong.graduationproject.MainActivity;
import com.licong.graduationproject.R;

import java.util.List;

/**
 * Created by licong on 3/22/17.
 */

public class MainInterfaceAdapter extends RecyclerView.Adapter<MainInterfaceAdapter.ViewHolder>{

    private List<MainInterface> mainInterfaceList;

    static  class  ViewHolder extends  RecyclerView.ViewHolder{
        ImageView mainInterfaceImage;
        TextView mainInterfaceText;

        public ViewHolder(View view){
            super(view);
            mainInterfaceImage=(ImageView)view
                    .findViewById(R.id.main_interface_image);
            mainInterfaceText=(TextView) view
                    .findViewById(R.id.main_interface_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_interface,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mainInterfaceList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainInterface mainInterface=mainInterfaceList.get(position);
        holder.mainInterfaceImage.setImageResource(MainInterface.getImageId());
        holder.mainInterfaceText.setText(MainInterface.getImageText());
    }

}



































