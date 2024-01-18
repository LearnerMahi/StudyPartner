package com.example.studypartner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studypartner.Matches.MatchesActivity;
import com.example.studypartner.cards.arrayAdapter;
import com.example.studypartner.cards.cards;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private cards cards_data[];
    private com.example.studypartner.cards.arrayAdapter arrayAdapter;
    private FirebaseAuth mAuth;
    private String currentUid;
    private DatabaseReference usersDb;
    private List<cards> rowItems;
    private ListView listView;
    private String oneUser;

    private String anoUser;
//    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        checkSubjectUser();
        rowItems = new ArrayList<>();
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);
        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
//        bnv=findViewById(R.id.bottom_nav);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                Log.d("List", "Removed object: ");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                cards obj = (cards) o;
                String userId = obj.getUserId();
                usersDb.child(userId).child("connections").child("nope").child(currentUid).setValue(true);
                Toast.makeText(MainActivity.this, "Left!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                cards obj = (cards) o;
                String userId = obj.getUserId();
                usersDb.child(userId).child("connections").child("yepp").child(currentUid).setValue(true);
                isConnectionMatch(userId);
                Toast.makeText(MainActivity.this, "Right!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int item) {
                // Handle when the adapter is about to become empty
            }

            @Override
            public void onScroll(float v) {
                // Handle scrolling
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int i, Object o) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });


    }
        // Moved this here to ensure mAuth is initialized


    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDb=usersDb.child(currentUid).child("connections").child("yepp").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if (snapshot.exists()){
                       Toast.makeText(MainActivity.this, "new connection", Toast.LENGTH_SHORT).show();
                       String key=FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                       usersDb.child(snapshot.getKey()).child("connections").child("matches").child(currentUid).child("chatId").setValue(key);
                       usersDb.child(currentUid).child("connections").child("matches").child(snapshot.getKey()).child("chatId").setValue(key);
                   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkSubjectUser() {
        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userdb = usersDb.child(user.getUid());
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getKey().equals(user.getUid())) {
                    if (snapshot.exists()){
                        if (snapshot.child("subject")!=null){
                            oneUser = snapshot.child("subject").getValue().toString();
                            anoUser = snapshot.child("subject").getValue().toString();
                            switch (oneUser){
                                case "Microprocessors and Microcontrollers":
                                    anoUser="Microprocessors and Microcontrollers";
                                    break;
                                case "Advanced Programming":
                                    anoUser="Advanced Programming";
                                    break;
                            }
                            getAnotherSubjectUser();
                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }


    public void getAnotherSubjectUser() {


            usersDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if (snapshot.child("subject").getValue() != null) {
                        if (snapshot.exists() && !snapshot.child("connections").child("nope").hasChild(currentUid) && !snapshot.child("connections").child("yepp").hasChild(currentUid) && snapshot.child("subject").getValue().toString().equals(anoUser)) {
                            String profileImageUrl = "default";
                            if (!snapshot.child("profileImageUrl").getValue().equals("default")) {
                                profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                            }
                            cards item = new cards(snapshot.getKey(), snapshot.child("name").getValue().toString(), profileImageUrl);
                            rowItems.add(item);
                            arrayAdapter.notifyDataSetChanged();
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

                // Other ChildEventListener methods

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            Toast.makeText(this, "logout clicked", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, ChooseActivity.class));
            finish();

        }
        else if(item.getItemId()==R.id.settings){
            Intent intent=new Intent(MainActivity.this, SettingsActivity.class);

            startActivity(intent);

        }
        return true;
    }

    public void goToMatches(View view) {
        startActivity(new Intent(MainActivity.this, MatchesActivity.class));
        //return;
    }
}
