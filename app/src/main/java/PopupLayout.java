package com.shwetak3e.chatappfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PopupLayout extends AppCompatActivity {
    private ImageView emoji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_layout);
        emoji=(ImageView)findViewById(R.id.emojiView);
        Glide.with(PopupLayout.this).load(R.drawable.yes).into(emoji);
    }
}
