package com.example.elena.mergesort;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Elena on 10/10/2017.
 */

public class SortedListAdapter extends RecyclerView.Adapter<SortedListAdapter.SortedListViewHolder>{
    private List<String> sortedValues;

    public void setData(List<String> data){
        sortedValues = data;
        notifyDataSetChanged();
    }

    @Override
    public SortedListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.sorted_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);
        return new SortedListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SortedListViewHolder holder, int position) {
        holder.valueTextView.setText(sortedValues.get(position));
    }

    @Override
    public int getItemCount() {
        if (sortedValues == null )return 0;
        return sortedValues.size();
    }

    class SortedListViewHolder extends RecyclerView.ViewHolder{
        TextView valueTextView;
        public SortedListViewHolder(View itemView) {
            super(itemView);
            valueTextView = (TextView) itemView.findViewById(R.id.text_value);
        }
    }
}
