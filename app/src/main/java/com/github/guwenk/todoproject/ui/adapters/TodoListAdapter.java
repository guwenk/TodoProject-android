package com.github.guwenk.todoproject.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.guwenk.todoproject.R;
import com.github.guwenk.todoproject.mvp.presenters.MainPresenter;
import com.github.guwenk.todoproject.ui.items.TodoItem;

import java.util.ArrayList;
import java.util.TreeSet;

public class TodoListAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<TodoItem> mData = new ArrayList<TodoItem>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    private MainPresenter mMainPresenter;

    public TodoListAdapter(Context context, MainPresenter mMainPresenter) {
        this.mMainPresenter = mMainPresenter;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final TodoItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final TodoItem item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    public void clearData() {
        sectionHeader.clear();
        mData.clear();
    }

    public void updateItem(int id, boolean isComplete) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getId() == id) {
                TodoItem item = mData.get(i);
                item.setCompleted(isComplete);
                mData.set(i, item);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public TodoItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        int rowType = getItemViewType(position);

        switch (rowType) {
            case TYPE_ITEM:
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.lv_item, null);
                    holder.checkBox = (CheckBox) convertView.findViewById(R.id.lv_item_checkbox);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.checkBox.setText(mData.get(position).getText());
                holder.checkBox.setChecked(mData.get(position).isCompleted());
                if (mData.get(position).isCompleted())
                    holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                else
                    holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!buttonView.isPressed()) return;

                        if (isChecked)
                            buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        else
                            buttonView.setPaintFlags(buttonView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        TodoItem item = new TodoItem().clone(mData.get(position));
                        item.setCompleted(isChecked);
                        mMainPresenter.requestUpdateTodoStatus(item.getId(), item.isCompleted());
                    }
                });
                break;
            case TYPE_SEPARATOR:
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.lv_separator, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.lv_separator_text);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.textView.setText(mData.get(position).getText());
                break;
        }
        return convertView;
    }


    public static class ViewHolder {
        TextView textView;
        CheckBox checkBox;
    }

}