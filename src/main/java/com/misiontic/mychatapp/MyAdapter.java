package com.misiontic.mychatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Message> messages;

    public MyAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.message,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Message message = messages.get(position);
       holder.user.setText(message.getUser());
       holder.message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView user,message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.message = itemView.findViewById(R.id.message);
            this.user = itemView.findViewById(R.id.user);


        }
    }

}
