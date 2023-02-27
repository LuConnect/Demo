
package com.example.demo.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.model.Mcomment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class CommentAdaptor extends FirebaseRecyclerAdapter<Mcomment, CommentAdaptor.ViewHolder> {


    private FirebaseFirestore firestore;

    public CommentAdaptor(FirebaseRecyclerOptions<Mcomment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Mcomment model) {

        //Glide.with(holder.commentImage.getContext()).load(documentSnapshot.getString("image")).into(holder.commentImage);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    String M = snapshot1.child("Comment").getValue().toString();
                    holder.commentTextView.setText(M);
                    System.out.println("caption: "+ M);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Glide.with(holder.commentImage.getContext()).load(model.getImage()).into(holder.commentImage);
        holder.usernameTextView.setText(model.getName());
//        holder.commentTextView.setText(model.getComments());
//        System.out.println("caption: "+ model.getComments());
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout file containing multiple child views
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.everycomment, parent, false);
        return new ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define child views
        TextView usernameTextView;
        TextView commentTextView;

        ImageView commentImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find child views by ID
            usernameTextView = itemView.findViewById(R.id.comment_user);
            commentTextView = itemView.findViewById(R.id.comment_tv);
            commentImage = itemView.findViewById(R.id.comment_Profile_pic);

        }
    }
}

