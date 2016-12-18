package com.example.jenis.tabbedactivityapp;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Jenis on 7/7/2016.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder>{
    List<FeedStruct> games;
    StorageReference storage;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView senderName, recieverName, gameWord, score;
        private Uri videoLink;
        private ImageView senderPicture, recieverPicture;

        public MyViewHolder(View view){
            super(view);
            senderName = (TextView)view.findViewById(R.id.textViewSenderUsername);
            recieverName = (TextView)view.findViewById(R.id.textViewRecieverUsername);
            gameWord = (TextView)view.findViewById(R.id.textViewWord);
            score = (TextView)view.findViewById(R.id.textViewScore);
            senderPicture = (ImageView)view.findViewById(R.id.imageView2);
            recieverPicture = (ImageView)view.findViewById(R.id.imageView3);
        }
    }

    public FeedAdapter(List<FeedStruct> games){
        this.games = games;
    }

    public void addMessage(FeedStruct info){
        games.add(info);
        notifyItemInserted(games.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedcard,parent,false);
        storage = FirebaseStorage.getInstance().getReference();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FeedStruct info = games.get(position);
        holder.senderName.setText(info.getSender());
        holder.recieverName.setText(info.getReciever());
        holder.gameWord.setText(info.getGameWord());
        holder.score.setText(""+info.getScore());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}