package sell;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.campusmonk.vikas.msrit.C0418R;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sell_notes_form extends AppCompatActivity {
    DatabaseReference databaseReference;
    Editor editor;
    FirebaseDatabase firebaseDatabase;
    Intent intent;
    /* renamed from: n */
    NetworkInfo f1572n;
    EditText phone_no;
    EditText price;
    SharedPreferences sharedPreferences;
    Button submit_button;
    Toolbar toolbar;
    EditText unit_name;

    /* renamed from: sell.Sell_notes_form$1 */
    class C23051 implements OnClickListener {
        C23051() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                Sell_notes_form.this.editor.putBoolean("Agree", true);
                Sell_notes_form.this.editor.commit();
                return;
            }
            Sell_notes_form.this.onBackPressed();
        }
    }

    /* renamed from: sell.Sell_notes_form$2 */
    class C23062 implements View.OnClickListener {
        C23062() {
        }

        public void onClick(View view) {
            if (Sell_notes_form.this.f1572n == null || !Sell_notes_form.this.f1572n.isAvailable() || !Sell_notes_form.this.f1572n.isConnected()) {
                Snackbar.make(Sell_notes_form.this.findViewById(C0418R.id.sell_notes_coordinate_layout), "Check your Network", -1).show();
            } else if (Sell_notes_form.this.price.getText().toString().equals("")) {
                Snackbar.make(Sell_notes_form.this.findViewById(C0418R.id.sell_notes_coordinate_layout), "Please Fill all the feilds", -1).show();
            } else {
                SharedPreferences sharedPreferences = Sell_notes_form.this.getSharedPreferences("my_preferences", 0);
                String notes_name = Sell_notes_form.this.intent.getStringExtra("Notes_name");
                Sell_notes_form.this.databaseReference = Sell_notes_form.this.firebaseDatabase.getReference("notes_list");
                Sell_notes_form.this.databaseReference = Sell_notes_form.this.databaseReference.child(notes_name).child(sharedPreferences.getString("USN", " "));
                Sell_notes_form.this.databaseReference.setValue(new Sell_notes_firebase_variables(null, Sell_notes_form.this.price.getText().toString(), sharedPreferences.getString("PHONE_NO", " ")));
                Sell_notes_form.this.databaseReference = Sell_notes_form.this.firebaseDatabase.getReference("notes_seller");
                Sell_notes_form.this.databaseReference = Sell_notes_form.this.databaseReference.child(Sell_notes_form.this.getSharedPreferences("my_preferences", 0).getString("USN", " ")).child("notes").child(notes_name);
                Sell_notes_form.this.databaseReference.setValue(Sell_notes_form.this.price.getText().toString());
                Toast.makeText(Sell_notes_form.this.getApplicationContext(), Sell_notes_form.this.intent.getStringExtra("Notes_name") + " " + "was added successfully", 0).show();
                Sell_notes_form.this.onBackPressed();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0418R.layout.sell_notes_form);
        this.intent = getIntent();
        this.sharedPreferences = getSharedPreferences("my_preferences", 0);
        this.editor = this.sharedPreferences.edit();
        View inflated_view = getLayoutInflater().inflate(C0418R.layout.sell_notes_form, null);
        if (!this.sharedPreferences.getBoolean("Agree", false)) {
            OnClickListener agreement_dialog_listner = new C23051();
            Builder agreement_alert_dialog = new Builder(this, C0418R.style.DialogTheme);
            agreement_alert_dialog.setCancelable(false);
            agreement_alert_dialog.setMessage("Neither College nor Developers are responsible for any conflicts or dispute that may arise between the customer and seller.").setPositiveButton("I Agree", agreement_dialog_listner).setNegativeButton("I Dont Agree", agreement_dialog_listner).show();
        }
        this.unit_name = (EditText) findViewById(C0418R.id.unit_name);
        this.price = (EditText) findViewById(C0418R.id.price_edit_text);
        this.phone_no = (EditText) findViewById(C0418R.id.phone_number);
        this.phone_no.setText(getSharedPreferences("my_preferences", 0).getString("PHONE_NO", " "));
        this.toolbar = (Toolbar) findViewById(C0418R.id.toolbar);
        setSupportActionBar(this.toolbar);
        this.unit_name.setText(this.intent.getStringExtra("Notes_name"));
        this.f1572n = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        RelativeLayout adViewContainer = (RelativeLayout) findViewById(C0418R.id.adViewContainer);
        AdView adView = new AdView(this, "385284181844331_387887034917379", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.submit_button = (Button) findViewById(C0418R.id.submit_button);
        this.submit_button.setOnClickListener(new C23062());
    }
}
