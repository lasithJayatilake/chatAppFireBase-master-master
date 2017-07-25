package com.shwetak3e.chatappfirebase;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.shwetak3e.chatappfirebase.R.id.imageView;
import static com.shwetak3e.chatappfirebase.R.id.imageViewCustomView;

public class ChatActivity extends AppCompatActivity {

    LinearLayout layout;
    ImageView sendButton,mic,speaker;
    EditText messageArea;
    ScrollView scrollView;
    TextToSpeech tts;
    Firebase reference1, reference2;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        mic = (ImageView)findViewById(R.id.micButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i != TextToSpeech.ERROR)
                    {
                        tts.setLanguage(Locale.ENGLISH);
                    }
                }
            });
            speaker = (ImageView)findViewById(R.id.speakerButton);
        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chatassist-2e05e.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chatassist-2e05e.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                final String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetails.username)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                    speaker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            tts.speak(message,TextToSpeech.QUEUE_FLUSH,null);
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onPause()
    {
        if(tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
    public void promptSpeechInput()
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something!");
        try{
            startActivityForResult(i,100);
        }
        catch(ActivityNotFoundException a)
        {
            Toast.makeText(ChatActivity.this,"Sorry! Your device doesn't support speech recognition",Toast.LENGTH_LONG).show();
        }
    }
    public void onActivityResult(int request_code,int result_code,Intent i)
    {
        super.onActivityResult(request_code,result_code,i);
        switch (request_code)
        {
            case 100:
                if(result_code == RESULT_OK && i != null)
                {
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    messageArea.setText(result.get(0));
                }
                break;
        }

    }
    public void addMessageBox(final String message, int type){
        //TextView textView = new TextView(ChatActivity.this);

        String sampleImageMessage = "";
        try {
                if (message.contains("hi") || message.contains("Hi"))
                {
                    sampleImageMessage = "1";
                }
                else if (message.contains("how are you") || message.contains("How are you"))
                {
                    sampleImageMessage = "2";
                }
                else if(message.contains("morning") || message.contains("Morning"))
                {
                    sampleImageMessage = "3";
                }
                else if(message.contains("afternoon") || message.contains("Afternoon"))
                {
                    sampleImageMessage = "4";
                }
                else if(message.contains("evening") || message.contains("Evening"))
                {
                    sampleImageMessage = "5";
                }
                else if(message.contains("night") || message.contains("Night"))
                {
                    sampleImageMessage = "6";
                }
                else if(message.contains("breakfast") || message.contains("Breakfast"))
                {
                    sampleImageMessage = "7";
                }
                else if(message.contains("lunch") || message.contains("Lunch"))
                {
                    sampleImageMessage = "8";
                }
                else if (message.contains("dinner") || message.contains("Dineer"))
                {
                    sampleImageMessage = "9";
                }
                else
                {
                    sampleImageMessage = "";
                }
        }catch (Exception e)
        {
            e.getMessage();
        }
        //Here you pass activity, message(string) and image path or any other image identifier string(string)
        CustomChatView chat = new CustomChatView(ChatActivity.this, message,sampleImageMessage);

        //textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        //textView.setLayoutParams(lp);
        chat.setLayoutParams(lp);
        if(type == 1) {
            //textView.setBackgroundResource(R.drawable.chat_from_me);
            chat.setBackgroundResource(R.drawable.chat_from_me);
        }
        else{
            //textView.setBackgroundResource(R.drawable.chat_from_friend);
            chat.setBackgroundResource(R.drawable.chat_from_friend);
        }

        //layout.addView(textView);
        layout.addView(chat);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}

