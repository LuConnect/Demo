package com.example.demo.adaptor;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.Comments;
import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.model.post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdaptor extends FirebaseRecyclerAdapter<post, PostAdaptor.myviewholder>
{

    private FirebaseFirestore firestore;
    //String time = String.valueOf(System.currentTimeMillis());

//    public void onAttach(Context context){
//        this.context = context;
//    }

    public PostAdaptor(@NonNull  FirebaseRecyclerOptions<post> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull post model) {


//        holder.postCaption.setText(model.getCaption());
//        System.out.println("caption: "+ model.getCaption());
//        Glide.with(holder.postPic.getContext()).load(model.getImage()).into(holder.postPic);

        firestore = FirebaseFirestore.getInstance();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("Users").document(currentUser);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String userName = documentSnapshot.getString("name");
                    holder.postUsername.setText(userName);
                    Glide.with(holder.profilePic.getContext()).load(documentSnapshot.getString("image")).into(holder.profilePic);

                    holder.postCaption.setText(model.getCaption());
                    System.out.println("caption: "+ model.getCaption());
                    //Glide.with(holder.postPic.getContext()).load(model.getImage()).into(holder.postPic);
                }
            }
        });

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





//public class PostAdaptor extends RecyclerView.Adapter<PostAdaptor.PostViewHolder> {
//
//
//    private List<post> mlist;
//    private Activity context;
//    private FirebaseFirestore firestore;
//
//
//    public PostAdaptor(Activity context, List<post> mlist){
//        this.mlist=mlist;
//        this.context=context;
//    }
//
//    @NonNull
//    @Override
//    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        return null;
//        View v = LayoutInflater.from(context).inflate(R.layout.everypost, parent, false );
//        firestore = FirebaseFirestore.getInstance();
//        return new PostViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
//        post Post =mlist.get(position);
//
//        holder.setPostPic(Post.getImage());
//        holder.setPostCaption(Post.getCaption());
//
//        long milliseconds = Post.getTime().getTime();
//        String date = DateFormat.format("MM/DD/yyyy", new Date(milliseconds)).toString();
//        holder.setPostDate(date);
//
//        String userId = Post.getUser();
//
//        firestore.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    String username = task.getResult().getString("name");
//                    String image = task.getResult().getString("image");
//
//                    holder.setProfilePic(image);
//                    holder.setPostUsername(username);
//
//                }else{
//                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mlist.size();
//    }
//
//    public class PostViewHolder extends RecyclerView.ViewHolder{
//
//        ImageView postPic, commentPic, likePic;
//        CircleImageView profilePic;
//        TextView postUsername, postDate, postCaption, postLikes;
//        View mView;
//
//        public PostViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mView = itemView;
//        }
//
//        public void setPostPic(String urlPost) {
//            postPic = mView.findViewById(R.id.user_post);
//            Glide.with(context).load(urlPost).into(postPic);
//
//        }
//        public void setProfilePic(String urlProfile){
//            profilePic = mView.findViewById(R.id.profile_pic);
//            Glide.with(context).load(urlProfile).into(profilePic);
//        }
//        public void setPostUsername(String username){
//            postUsername = mView.findViewById(R.id.username_tv);
//            postUsername.setText(username);
//        }
//        public  void setPostDate(String date){
//            postDate = mView.findViewById(R.id.date_tv);
//            postDate.setText(date);
//        }
//        public void setPostCaption(String caption){
//            postCaption = mView.findViewById(R.id.caption_tv);
//            postCaption.setText(caption);
//        }
//
//
//    }
//
//
//}
