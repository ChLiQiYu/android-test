package com.example.experiment03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private final LayoutInflater inflater;
    private final int resource;
    private final List<String> objects;
    private ListView listView; // 添加对ListView的引用

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.objects = objects;
    }

    // 添加设置ListView的方法
    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.item_icon);
            holder.text = convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        // 设置文本
        String item = objects.get(position);
        holder.text.setText(item);
        
        // 设置图标
        // 图标已经在XML中设置为机器人图标
        
        // 关键修复：根据ListView的选中状态更新背景
        if (listView != null) {
            // 使用setActivated而不是setSelected，因为我们的drawable应该使用state_activated
            convertView.setActivated(listView.isItemChecked(position));
        }
        
        return convertView;
    }
    
    // 为了支持多选模式，我们需要重写getView方法来处理选中状态
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    
    // 当数据发生变化时，确保UI得到更新
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    
    static class ViewHolder {
        ImageView icon;
        TextView text;
    }
}