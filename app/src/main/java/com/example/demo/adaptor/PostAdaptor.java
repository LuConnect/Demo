package com.example.demo.adaptor;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.Comments;
import com.example.demo.R;
import com.example.demo.model.post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdaptor extends FirebaseRecyclerAdapter<post, PostAdaptor.myviewholder>
{


    public static String count = "";
    private FirebaseFirestore firestore;
    //String time = String.valueOf(System.currentTimeMillis());


    public PostAdaptor(@NonNull  FirebaseRecyclerOptions<post> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull post model) {


        holder.postCaption.setText(model.getCaption());
        System.out.println("caption: "+ model.getCaption());

        holder.postUsername.setText(model.getName());
        Glide.with(holder.profilePic.getContext()).load(model.getImage()).into(holder.profilePic);

        String time = model.getTime();
        //time = String.valueOf(System.currentTimeMillis());
        holder.commentPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comments = new Intent(v.getContext(), Comments.class);
                comments.putExtra("post_id", time);
                v.getContext().startActivity(comments);
            }
        });

        if(count == "1"){
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("Post").child(time).removeValue();
                }
            });
        }
        else if(count == "2"){

            //Toast.makeText(PostAdaptor.this, "Access denied", Toast.LENGTH_SHORT).show();

        }


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.everypost, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView postPic, commentPic, likePic,delete;
        CircleImageView profilePic;
        TextView postUsername, posttime, postCaption, postLikes;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profile_pic);
            //postPic = (ImageView)itemView.findViewById(R.id.user_post);
            postUsername = (TextView)itemView.findViewById(R.id.username_tv);

            postCaption = (TextView)itemView.findViewById(R.id.caption_tv);
            commentPic = (ImageView) itemView.findViewById(R.id.comments_post);
            delete= (ImageView) itemView.findViewById(R.id.delete_btn);


        }
    }

}






