package com.example.demo.adaptor;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

public class CommentAdaptor extends RecyclerView.ViewHolder {

    public ImageView commentuserpic;
    public TextView commentusername;
    public TextView comments;


    public CommentAdaptor(@NonNull View itemView) {
        super(itemView);

        commentuserpic=itemView.findViewById(R.id.comment_Profile_pic);
        commentusername=itemView.findViewById(R.id.comment_user);
        comments=itemView.findViewById(R.id.comment_tv);

    }
}
