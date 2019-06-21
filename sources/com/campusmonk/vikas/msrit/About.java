package com.campusmonk.vikas.msrit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class About extends AppCompatActivity {
    TextView RegisteredUserNumber;
    RecyclerView about_recycler_view;

    /* renamed from: com.campusmonk.vikas.msrit.About$1 */
    class C04161 implements ValueEventListener {
        C04161() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            About.this.RegisteredUserNumber.setText("Total Registerd Users " + dataSnapshot.getChildrenCount());
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0418R.layout.about_layout);
        this.RegisteredUserNumber = (TextView) findViewById(C0418R.id.RegisteredUserNumber);
        this.about_recycler_view = (RecyclerView) findViewById(C0418R.id.about_recycler_view);
        this.about_recycler_view.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.about_recycler_view.setAdapter(new about_recycler_view_adapter());
        FirebaseDatabase.getInstance().getReference("Login_details").addListenerForSingleValueEvent(new C04161());
    }
}
