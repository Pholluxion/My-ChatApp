package com.misiontic.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FloatingActionButton btnSend;
    private EditText message;
    private String email;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<Message> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.message = findViewById(R.id.txtMessage);
        this.btnSend = findViewById(R.id.send);
        this.recyclerView = findViewById(R.id.messagesList);
        this.databaseReference = FirebaseDatabase.getInstance().getReference("messages");
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messages = new ArrayList<>();
        myAdapter = new MyAdapter(this,messages);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String message = dataSnapshot.getValue(String.class);
                    String user = dataSnapshot.getKey();
                    Message message1 = new  Message(user,message);
                    messages.add(message1);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        this.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basicReadWrite(message.getText().toString());
            }
        });



    }

    public void basicReadWrite(String m) {

        if(getIntent().hasExtra("email")){
            this.email = getIntent().getStringExtra("email").substring(0,7);
        }else{
            this.email = getIntent().getStringExtra("null");
        }
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages").child(this.email);

        myRef.setValue(m);
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }
}