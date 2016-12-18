package com.example.jenis.tabbedactivityapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jenis on 8/26/2016.
 */
public class SingleFeedAdapter extends RecyclerView.Adapter<SingleFeedAdapter.MyViewHolder> {
    List<singleNameFeedClass> feedStuff;
    private int selectedItem = 0;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView otherPlayersName;
        private ImageView otherPlayersProfile;
        private String GameId;
        private String sentOrRecieved;

        public MyViewHolder (View view){
            super(view);
            otherPlayersName = (TextView)view.findViewById(R.id.textViewOtherPlayersName);
            otherPlayersProfile = (ImageView)view.findViewById(R.id.imageViewOtherPlayersProfile);
            context = view.getContext();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sentOrRecieved == "recieved") {
                        selectedItem = getLayoutPosition();
                        CreateAGuessingGame.CreateGame(GameId, context);
                    }
                    if(sentOrRecieved == "pending") {
                        selectedItem = getLayoutPosition();
                        //CreateAGame.startNewIntent();
                        //Put in link to continue game activity
                    }
                }
            });
        }
    }

    public SingleFeedAdapter(List<singleNameFeedClass> feedStuff){this.feedStuff = feedStuff;}

    public void addMessage(singleNameFeedClass info){
        feedStuff.add(info);
        notifyItemInserted(feedStuff.size());
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlenamefeed,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        singleNameFeedClass info = feedStuff.get(position);
        holder.otherPlayersName.setText(info.getName());
        holder.GameId = info.getGameId();
        holder.sentOrRecieved = info.getSentOrRecieved();
    }

    @Override
    public int getItemCount() {
        return feedStuff.size();
    }
}
