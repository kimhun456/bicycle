package com.bicyle.bicycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class MakeFriend extends AppCompatActivity {

    String frdNickname = "", myNickname = "";

    String frdUid = "", senderUID ="";
    String myUid;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_friend);

        Intent intent = getIntent();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myNickname = intent.getStringExtra("myNickName");
        senderUID = intent.getStringExtra("senderUid");
        frdNickname = intent.getStringExtra("senderNickName");

        Button positiveBtn = (Button) findViewById(R.id.postiveBtn);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Query query = mDatabase.child("Profiles").orderByChild("uid").equalTo(senderUID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean existUser = dataSnapshot.exists();
                        if(existUser) {
                            for(DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                frdUid = dataSnap.child("uid").getValue().toString();
                                mDatabase.child("FrdRelship").child(myUid).push().setValue(frdUid);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                mDatabase.child("FrdRelship").child(senderUID).push().setValue(myUid);
                finish();
            }
        });

        Button negativeBtn = (Button) findViewById(R.id.negativeBtn);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                finish();
            }
        });
    }

}