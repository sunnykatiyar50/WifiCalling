package com.sunnykatiyar.wificalling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.sunnykatiyar.wificalling.MainActivity.context;

/**
 * Created by Sunny Katiyar on 09-02-2018.
 */

public class MessageAdapter extends BaseAdapter {

    List<String> msgs = new ArrayList<>();

    public MessageAdapter(List<String> msgs) {
        this.msgs = msgs;
    }

public class ViewHolder{
        TextView msgline;
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder =new ViewHolder();
        if(convertView==null){
            convertView = inflater.inflate(R.layout.chat_row_layout, parent, false);
                viewHolder.msgline = convertView.findViewById(R.id.textView2);
                convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        String new_msg = msgs.get(position);
        viewHolder.msgline.setText(new_msg);

        return convertView;
    }


    @Override
    public int getCount() {
        if(msgs==null)
            return 0;
        else
            return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}