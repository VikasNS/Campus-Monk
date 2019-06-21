package sell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.campusmonk.vikas.msrit.C0418R;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class my_products extends AppCompatActivity {
    ArrayList<Sell_notes_firebase_variables> arraylist;
    Button edit_product_button;
    /* renamed from: m */
    my_products_recycler_view_adaptor f1574m;
    TextView pant_size_text_view;
    ProgressDialog progressDialog;
    RecyclerView recyclerview;
    TextView sex_text_view;
    SharedPreferences sharedPreferences;
    TextView shirt_size_text_view;
    Toolbar toolbar;
    Button uniform_delete_button;
    TextView uniform_price_text_view;

    /* renamed from: sell.my_products$1 */
    class C23131 implements OnClickListener {
        C23131() {
        }

        public void onClick(View v) {
            my_products.this.startActivity(new Intent(my_products.this.getApplicationContext(), Uniform_sell_form.class));
        }
    }

    /* renamed from: sell.my_products$2 */
    class C23142 implements OnClickListener {
        C23142() {
        }

        public void onClick(View view) {
            FirebaseDatabase.getInstance().getReference("notes_seller").child(my_products.this.sharedPreferences.getString("USN", " ")).child("uniform").removeValue();
            FirebaseDatabase.getInstance().getReference("uniform_list").child(my_products.this.getSharedPreferences("my_preferences", 0).getString("USN", "")).removeValue();
            my_products.this.sex_text_view.setText("");
            my_products.this.pant_size_text_view.setText("");
            my_products.this.shirt_size_text_view.setText("");
            my_products.this.uniform_price_text_view.setText("");
        }
    }

    /* renamed from: sell.my_products$3 */
    class C23153 implements ValueEventListener {
        C23153() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                if (child.getKey().equals("pant_size")) {
                    my_products.this.pant_size_text_view.setText(child.getValue().toString());
                } else if (child.getKey().equals(Param.PRICE)) {
                    my_products.this.uniform_price_text_view.setText(child.getValue().toString());
                } else if (child.getKey().equals("sex")) {
                    my_products.this.sex_text_view.setText(child.getValue().toString());
                } else {
                    my_products.this.shirt_size_text_view.setText(child.getValue().toString());
                }
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    /* renamed from: sell.my_products$4 */
    class C23164 implements ValueEventListener {
        C23164() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getChildrenCount() == 0) {
                my_products.this.progressDialog.dismiss();
                Snackbar.make(my_products.this.sex_text_view.getRootView(), "Nothing to show", 0).show();
            }
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                my_products.this.arraylist.add(new Sell_notes_firebase_variables(child.getKey(), child.getValue().toString(), null));
                my_products.this.f1574m.notifyDataSetChanged();
                my_products.this.progressDialog.dismiss();
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    /* renamed from: sell.my_products$5 */
    class C23175 implements ValueEventListener {
        C23175() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getChildrenCount() == 0) {
                my_products.this.progressDialog.dismiss();
                Snackbar.make(my_products.this.sex_text_view.getRootView(), "Nothing to show", 0).show();
            }
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                my_products.this.arraylist.add(new Sell_notes_firebase_variables(child.getKey(), child.getValue().toString(), null));
                my_products.this.f1574m.notifyDataSetChanged();
                my_products.this.progressDialog.dismiss();
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0418R.layout.my_products);
        this.toolbar = (Toolbar) findViewById(C0418R.id.toolbar);
        this.uniform_delete_button = (Button) findViewById(C0418R.id.uniform_delete_button);
        this.sharedPreferences = getSharedPreferences("my_preferences", 0);
        this.sex_text_view = (TextView) findViewById(C0418R.id.sex_text_view);
        this.pant_size_text_view = (TextView) findViewById(C0418R.id.pant_size_text_view);
        this.shirt_size_text_view = (TextView) findViewById(C0418R.id.shirt_size_text_view);
        this.uniform_price_text_view = (TextView) findViewById(C0418R.id.uniform_price_text_view);
        this.edit_product_button = (Button) findViewById(C0418R.id.uniform_edit_button);
        this.edit_product_button.setOnClickListener(new C23131());
        this.uniform_delete_button.setOnClickListener(new C23142());
        this.recyclerview = (RecyclerView) findViewById(C0418R.id.my_products_recycler_view);
        setSupportActionBar(this.toolbar);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Loading Data");
        this.progressDialog.setProgressStyle(0);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        this.arraylist = new ArrayList();
        getFirebaseData();
        this.f1574m = new my_products_recycler_view_adaptor(this.arraylist, this.sharedPreferences.getString("USN", ""), this.recyclerview);
        this.recyclerview.setAdapter(this.f1574m);
        this.recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.f1574m.notifyDataSetChanged();
        this.recyclerview.invalidate();
    }

    public void getFirebaseData() {
        DatabaseReference usn = FirebaseDatabase.getInstance().getReference("notes_seller").child(getSharedPreferences("my_preferences", 0).getString("USN", " "));
        DatabaseReference notes = usn.child("notes");
        DatabaseReference textbook = usn.child("TextBook");
        FirebaseDatabase.getInstance().getReference("notes_seller").child(this.sharedPreferences.getString("USN", "")).child("uniform").addListenerForSingleValueEvent(new C23153());
        notes.addListenerForSingleValueEvent(new C23164());
        textbook.addListenerForSingleValueEvent(new C23175());
    }
}
