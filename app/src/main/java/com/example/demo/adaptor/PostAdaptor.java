package com.example.demo.adaptor;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.model.post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdaptor extends RecyclerView.Adapter<PostAdaptor.PostViewHolder> {


    private List<post> mlist;
    private Activity context;
    private FirebaseFirestore firestore;


    public PostAdaptor(Activity context, List<post> mlist){
        this.mlist=mlist;
        this.context=context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        View v = LayoutInflater.from(context).inflate(R.layout.everypost, parent, false );
        firestore = FirebaseFirestore.getInstance();
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        post Post =mlist.get(position);

        holder.setPostPic(Post.getImage());
        holder.setPostCaption(Post.getCaption());

        long milliseconds = Post.getTime().getTime();
        String date = DateFormat.format("MM/DD/yyyy", new Date(milliseconds)).toString();
        holder.setPostDate(date);

        String userId = Post.getUser();

        firestore.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    String username = task.getResult().getString("name");
                    String image = task.getResult().getString("image");

                    holder.setProfilePic(image);
                    holder.setPostUsername(username);

                }else{
                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        ImageView postPic, commentPic, likePic;
        CircleImageView profilePic;
        TextView postUsername, postDate, postCaption, postLikes;
        View mView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setPostPic(String urlPost) {
            postPic = mView.findViewById(R.id.user_post);
            Glide.with(context).load(urlPost).into(postPic);

        }
        public void setProfilePic(String urlProfile){
            profilePic = mView.findViewById(R.id.profile_pic);
            Glide.with(context).load(urlProfile).into(profilePic);
        }
        public void setPostUsername(String username){
            postUsername = mView.findViewById(R.id.username_tv);
            postUsername.setText(username);
        }
        public  void setPostDate(String date){
            postDate = mView.findViewById(R.id.date_tv);
            postDate.setText(date);
        }
        public void setPostCaption(String caption){
            postCaption = mView.findViewById(R.id.caption_tv);
            postCaption.setText(caption);
        }


    }


}
