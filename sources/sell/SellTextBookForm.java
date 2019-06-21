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

public class SellTextBookForm extends AppCompatActivity {
    EditText BookName;
    DatabaseReference databaseReference;
    Editor editor;
    FirebaseDatabase firebaseDatabase;
    Intent intent;
    /* renamed from: n */
    NetworkInfo f1571n;
    EditText phone_no;
    EditText price;
    SharedPreferences sharedPreferences;
    Button submit_button;
    Toolbar toolbar;

    /* renamed from: sell.SellTextBookForm$1 */
    class C23031 implements OnClickListener {
        C23031() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                SellTextBookForm.this.editor.putBoolean("Agree", true);
                SellTextBookForm.this.editor.commit();
                return;
            }
            SellTextBookForm.this.onBackPressed();
        }
    }

    /* renamed from: sell.SellTextBookForm$2 */
    class C23042 implements View.OnClickListener {
        C23042() {
        }

        public void onClick(View view) {
            if (SellTextBookForm.this.f1571n == null || !SellTextBookForm.this.f1571n.isAvailable() || !SellTextBookForm.this.f1571n.isConnected()) {
                Snackbar.make(SellTextBookForm.this.findViewById(C0418R.id.sell_notes_coordinate_layout), "Check your Network", -1).show();
            } else if (SellTextBookForm.this.price.getText().toString().equals("") || SellTextBookForm.this.BookName.getText().toString().equals("")) {
                Snackbar.make(SellTextBookForm.this.findViewById(C0418R.id.sell_notes_coordinate_layout), "Please Fill all the feilds", -1).show();
            } else {
                SharedPreferences sharedPreferences = SellTextBookForm.this.getSharedPreferences("my_preferences", 0);
                String subjectName = SellTextBookForm.this.intent.getStringExtra("SubjectName");
                SellTextBookForm.this.databaseReference = SellTextBookForm.this.firebaseDatabase.getReference("TextBookList");
                SellTextBookForm.this.databaseReference = SellTextBookForm.this.databaseReference.child(subjectName).child(SellTextBookForm.this.BookName.getText().toString()).child(sharedPreferences.getString("USN", " "));
                SellTextBookForm.this.databaseReference.setValue(new Sell_notes_firebase_variables(null, SellTextBookForm.this.price.getText().toString(), sharedPreferences.getString("PHONE_NO", " ")));
                SellTextBookForm.this.databaseReference = SellTextBookForm.this.firebaseDatabase.getReference("notes_seller");
                SellTextBookForm.this.databaseReference = SellTextBookForm.this.databaseReference.child(SellTextBookForm.this.getSharedPreferences("my_preferences", 0).getString("USN", " ")).child("TextBook").child(SellTextBookForm.this.BookName.getText().toString());
                SellTextBookForm.this.databaseReference.setValue(SellTextBookForm.this.price.getText().toString());
                Toast.makeText(SellTextBookForm.this.getApplicationContext(), SellTextBookForm.this.intent.getStringExtra("SubjectName") + " " + "was added successfully", 0).show();
                SellTextBookForm.this.onBackPressed();
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
            OnClickListener agreement_dialog_listner = new C23031();
            Builder agreement_alert_dialog = new Builder(this, C0418R.style.DialogTheme);
            agreement_alert_dialog.setCancelable(false);
            agreement_alert_dialog.setMessage("Neither College nor Developers are responsible for any conflicts or dispute that may arise between the customer and seller.").setPositiveButton("I Agree", agreement_dialog_listner).setNegativeButton("I Dont Agree", agreement_dialog_listner).show();
        }
        this.BookName = (EditText) findViewById(C0418R.id.unit_name);
        this.price = (EditText) findViewById(C0418R.id.price_edit_text);
        this.phone_no = (EditText) findViewById(C0418R.id.phone_number);
        this.phone_no.setText(getSharedPreferences("my_preferences", 0).getString("PHONE_NO", " "));
        this.toolbar = (Toolbar) findViewById(C0418R.id.toolbar);
        setSupportActionBar(this.toolbar);
        this.f1571n = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        RelativeLayout adViewContainer = (RelativeLayout) findViewById(C0418R.id.adViewContainer);
        AdView adView = new AdView(this, "385284181844331_387887034917379", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.submit_button = (Button) findViewById(C0418R.id.submit_button);
        this.submit_button.setOnClickListener(new C23042());
    }
}
