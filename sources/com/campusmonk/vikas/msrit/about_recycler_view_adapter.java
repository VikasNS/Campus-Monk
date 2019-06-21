package com.campusmonk.vikas.msrit;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class about_recycler_view_adapter extends Adapter<view_holder> {
    String[] names = new String[]{"Vikas", "Siva", "Rishikesh", "Varun", "Sourav", "Rishab"};

    public class view_holder extends ViewHolder {
        TextView about_name;

        public view_holder(View itemView) {
            super(itemView);
            this.about_name = (TextView) itemView.findViewById(C0418R.id.about_names);
        }
    }

    public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new view_holder(LayoutInflater.from(parent.getContext()).inflate(C0418R.layout.about_recycler_view_adapter_layout, parent, false));
    }

    public void onBindViewHolder(view_holder holder, int position) {
        holder.about_name.setText(this.names[position % 6]);
    }

    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
