package com.bicyle.bicycle.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bicyle.bicycle.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserChatAdapter extends ArrayAdapter<UserChatData> {
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("a h:mm", Locale.getDefault());

    public UserChatAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listitem_chat, null);

            viewHolder = new ViewHolder();
            viewHolder.mTxtUserName = (TextView) convertView.findViewById(R.id.txt_userName);
            viewHolder.mTxtMessage = (TextView) convertView.findViewById(R.id.txt_message);
            viewHolder.mTxtTime = (TextView) convertView.findViewById(R.id.txt_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserChatData chatData = getItem(position);
        viewHolder.mTxtUserName.setText(chatData.chatUserName);
        viewHolder.mTxtMessage.setText(chatData.chatMessage);
        viewHolder.mTxtTime.setText(mSimpleDateFormat.format(chatData.chatTime));

        return convertView;
    }

    private class ViewHolder {
        private TextView mTxtUserName;
        private TextView mTxtMessage;
        private TextView mTxtTime;
    }
}
