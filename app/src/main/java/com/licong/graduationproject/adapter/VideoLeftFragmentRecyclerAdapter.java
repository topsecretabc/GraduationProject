package com.licong.graduationproject.adapter;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.licong.graduationproject.R;

/**
 * Created by licong on 3/29/17.
 */

public class VideoLeftFragmentRecyclerAdapter extends RecyclerView.Adapter<VideoLeftFragmentRecyclerAdapter.ViewHolder> {

private Context mContext;

public VideoLeftFragmentRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        }

@Override
public VideoLeftFragmentRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.video_discuss,parent,false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(final VideoLeftFragmentRecyclerAdapter.ViewHolder holder, int position) {
        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
@Override
         public void onClick(View v) {

        }
        });
}

@Override
public int getItemCount() {
        return 10;
        }

public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;

    public ViewHolder(View view) {
        super(view);
        mView = view;
    }
}
}
