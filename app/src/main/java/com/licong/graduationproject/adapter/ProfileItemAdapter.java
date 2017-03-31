package com.licong.graduationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.licong.graduationproject.R;

import java.util.List;

/**
 * Created by licong on 3/31/17.
 */

public class ProfileItemAdapter extends ArrayAdapter<ProfileItem> {

    private int resourceId;

    public ProfileItemAdapter(Context context, int textViewResourceId,
                        List<ProfileItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProfileItem profileItem = getItem(position); // 获取当前项ProfileItem实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.profileitem = (TextView) view.findViewById(R.id.profile_item);
            viewHolder.profilecontent = (TextView) view.findViewById(R.id.profile_content);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.profileitem.setText(profileItem.getProfileItem());
        viewHolder.profilecontent.setText(profileItem.getProfileContent());
        return view;
    }

    class ViewHolder {

        TextView profileitem;

        TextView profilecontent;

    }
}
