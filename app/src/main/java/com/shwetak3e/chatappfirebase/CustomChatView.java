package com.shwetak3e.chatappfirebase;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * TODO: document your custom view class.
 */
public class CustomChatView extends LinearLayout {

    private View mainView;
    private ImageView mImage;
    private TextView mEditText;

    public CustomChatView(Context context, String message, String imageMessage) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        mainView = inflate(context,R.layout.sample_custom_chat_view, this);

        mEditText = (TextView) mainView.findViewById(R.id.editTextCustomView);
        mImage = (ImageView) mainView.findViewById(R.id.imageViewCustomView);
        //set the message in the text view
        mEditText.setText(message);


        //if the image is not null you can show image by loading an existing image by identifying the value u got
        try {
            if (imageMessage != null) {
                //if not null show an image
                switch (imageMessage)
                {
                    case "1": mImage.setImageResource(R.drawable.hello);break;
                    case "2":Glide.with(CustomChatView.this).load(R.drawable.how_are_you).into(mImage);break;
                    case "3":Glide.with(CustomChatView.this).load(R.drawable.morning).into(mImage);break;
                    case "4":Glide.with(CustomChatView.this).load(R.drawable.afternoon).into(mImage);break;
                    case "5":Glide.with(CustomChatView.this).load(R.drawable.evening).into(mImage);break;
                    case "6":Glide.with(CustomChatView.this).load(R.drawable.night).into(mImage);break;
                    case "7":Glide.with(CustomChatView.this).load(R.drawable.breakfast).into(mImage);break;
                    case "8":Glide.with(CustomChatView.this).load(R.drawable.lunch).into(mImage);break;
                    case "9":Glide.with(CustomChatView.this).load(R.drawable.dinner).into(mImage);break;
                    default:mImage.setImageResource(R.mipmap.ic_launcher);break;
                }

            } else {
                //if its null hide it
                mImage.setVisibility(GONE);
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
    }

}
