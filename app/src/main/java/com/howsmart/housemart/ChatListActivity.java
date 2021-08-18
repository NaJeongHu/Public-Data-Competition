package com.howsmart.housemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.howsmart.housemart.Adapter.ChatListAdapter;
import com.howsmart.housemart.Model.Chat;
import com.howsmart.housemart.Model.Chatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private static final String TAG = "ChatListActivity";

    private ImageView mBack;
    private ImageView mSearch;

    private RecyclerView recyclerView;
    private ChatListAdapter mChatListAdapter;

    private List<Chatter> mChatters;
    private List<Chat> mLastMessages;

    private FirebaseUser fuser;
    private DatabaseReference reference;

    private int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        mBack = findViewById(R.id.chat_list_btn_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSearch = findViewById(R.id.chat_list_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChatListAdapter != null){
                    //TODO : search
                }
            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getList();
    }

    private void getList() {

        mChatters = new ArrayList<>();
        mLastMessages = new ArrayList<>();

        //fuser과 채팅한 사람들의 목록 불러오기
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.orderByChild("users/" + fuser.getUid() + "/id").equalTo(fuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChatters.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot usersSnapshot : dataSnapshot.child("users").getChildren()) {
                        Chatter chatter = usersSnapshot.getValue(Chatter.class);

                        //get chatter chatting with fuser
                        if (!chatter.getId().equals(fuser.getUid())) {
                            mChatters.add(chatter);

                            //get lastMessage
                            Chat lastmessage = dataSnapshot.child("lastMessage").getValue(Chat.class);
                            mLastMessages.add(lastmessage);
                        }
                    }
                }

                mChatListAdapter = new ChatListAdapter(ChatListActivity.this, mChatters, mLastMessages);
                recyclerView.setAdapter(mChatListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        getList();
    }
}