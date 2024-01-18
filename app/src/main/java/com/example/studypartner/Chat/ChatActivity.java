package com.example.studypartner.Chat;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studypartner.Matches.MatchesActivity;
import com.example.studypartner.Matches.MatchesAdapter;
import com.example.studypartner.Matches.MatchesObject;
import com.example.studypartner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private String currentUserID,matchId,chatId;
    private EditText mSendEditText;
    private Button mSendButton;
    private RecyclerView.LayoutManager mChatLayoutManager;
    DatabaseReference mDatabaseUser,mDatabaseChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        matchId=getIntent().getExtras().getString("matchId");
        currentUserID= FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("connections").child("matches").child(matchId).child("chatId");
        mDatabaseChat= FirebaseDatabase.getInstance().getReference().child("Chat");
        getChatId();
      mRecyclerView=findViewById(R.id.recyclerView);
       // mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mChatLayoutManager=new LinearLayoutManager(ChatActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter=new ChatAdapter(getDataSetChat(),ChatActivity.this);
        mRecyclerView.setAdapter(mChatAdapter);
        mSendEditText=findViewById(R.id.message);
        mSendButton=findViewById(R.id.send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMesssage();
            }
        });
    }

    private void sendMesssage() {
         String sendMessageText=mSendEditText.getText().toString();
         if(!sendMessageText.isEmpty()){
             DatabaseReference newMessageDb=mDatabaseChat.push();
             Map newMessage=new HashMap<>();
             newMessage.put("CreatedByUser",currentUserID);
             newMessage.put("text",sendMessageText);
             newMessageDb.setValue(newMessage);
         }
         mSendEditText.setText(null);
    }
  private void getChatId(){
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             if (snapshot.exists()){
                 chatId=snapshot.getValue().toString();
                 mDatabaseChat=mDatabaseChat.child(chatId);
                 getChatMessages();
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
  }

    private void getChatMessages() {
        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    String message="";
                    String createdByUser="";


                    if (snapshot.child("text").getValue()!=null){
                        message=snapshot.child("text").getValue().toString();
                    }
                    if (snapshot.child("createdByUser").getValue()!=null){
                        createdByUser=snapshot.child("createdByUser").getValue().toString();
                    }
                    if (message!=null&&createdByUser!=null){
                        Boolean currentUserBoolean=false;
                        if (createdByUser.equals(currentUserID)){
                            currentUserBoolean=true;
                        }
                   ChatObject newMessage=new ChatObject(message, currentUserBoolean);
                        resultChat.add(newMessage);
                        mChatAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<ChatObject> resultChat=new ArrayList<ChatObject>();
    private List<ChatObject> getDataSetChat() {
        return  resultChat;
    }
}