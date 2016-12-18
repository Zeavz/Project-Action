package com.example.jenis.tabbedactivityapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Random;

/**
 * Created by Jenis on 8/8/2016.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder>{
    List<Friend> friends;
    private int selectedItem = 0;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView firstName;
        private TextView interactionPoints;
        private ImageView friendsProfilePic;
        private Button bStartAGameWithFriend, bStartAMessageWithFriend;

        public MyViewHolder(View view){
            super(view);
            firstName = (TextView)view.findViewById(R.id.textViewfirst);
            interactionPoints = (TextView)view.findViewById(R.id.textViewinteraction);
            friendsProfilePic = (ImageView)view.findViewById(R.id.imageView);
            bStartAGameWithFriend = (Button)view.findViewById(R.id.buttonStartAGame);
            bStartAMessageWithFriend = (Button)view.findViewById(R.id.buttonStartAMessage);
            context = view.getContext();

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //notifyItemChanged(selectedItem);
                    selectedItem = getLayoutPosition();
                    notifyItemChanged(selectedItem);
                }
            });
            friendsProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getLayoutPosition();
                    friends.get(selectedItem);
                }
            });
            bStartAGameWithFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getLayoutPosition();
                    Random rand = new Random();
                    int randomWordNumber = rand.nextInt(6)+1;
                    CreateAGame.makeANewGame(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),friends.get(selectedItem).getName(), context, randomWordNumber, 1);
                }
            });
        }
    }

    public void StartCamera(){
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }

    public FriendAdapter(List<Friend> friends){
        this.friends = friends;
    }

    public void addMessage(Friend info){
        friends.add(info);
        notifyItemInserted(friends.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendcard,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Friend info = friends.get(position);
        holder.itemView.setSelected(selectedItem == position);
        holder.firstName.setText(info.getName());
        holder.interactionPoints.setText(" " + info.getPoints());
        GliderStuff.profileImage(info.getLink(),holder.friendsProfilePic);
    }


    @Override
    public int getItemCount() {
        return friends.size();
    }
}
