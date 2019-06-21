package sell;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.campusmonk.vikas.msrit.C0418R;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Uniform_sell_form extends AppCompatActivity {
    Editor editor;
    RadioButton female;
    RadioButton male;
    /* renamed from: n */
    NetworkInfo f1573n;
    String pant_size = "";
    String[] pant_size_array = new String[]{"22", "24", "26", "28", "30", "32", "34", "36", "38", "40", "42"};
    Spinner pant_size_spinner;
    String price = "";
    EditText sell_uniform_phone_number_edit_text;
    EditText sell_uniform_price_edit_text;
    Button sell_uniform_submit_button;
    String sex = "";
    SharedPreferences sharedPreferences;
    String shirt_size = "";
    String[] shirt_size_array = new String[]{"22", "24", "26", "28", "30", "32", "34", "36", "38", "40", "42"};
    Spinner shirt_size_spinner;

    /* renamed from: sell.Uniform_sell_form$1 */
    class C23071 implements OnClickListener {
        C23071() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                Uniform_sell_form.this.editor.putBoolean("Agree", true);
                Uniform_sell_form.this.editor.commit();
                return;
            }
            Uniform_sell_form.this.onBackPressed();
        }
    }

    /* renamed from: sell.Uniform_sell_form$2 */
    class C23082 implements OnCheckedChangeListener {
        C23082() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Uniform_sell_form.this.female.setChecked(false);
        }
    }

    /* renamed from: sell.Uniform_sell_form$3 */
    class C23093 implements OnCheckedChangeListener {
        C23093() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Uniform_sell_form.this.male.setChecked(false);
        }
    }

    /* renamed from: sell.Uniform_sell_form$4 */
    class C23104 implements OnItemSelectedListener {
        C23104() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            Uniform_sell_form.this.shirt_size = Uniform_sell_form.this.shirt_size_array[position];
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            Uniform_sell_form.this.shirt_size = Uniform_sell_form.this.shirt_size_array[0];
        }
    }

    /* renamed from: sell.Uniform_sell_form$5 */
    class C23115 implements OnItemSelectedListener {
        C23115() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            Uniform_sell_form.this.pant_size = Uniform_sell_form.this.pant_size_array[position];
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            Uniform_sell_form.this.pant_size = Uniform_sell_form.this.pant_size_array[0];
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0418R.layout.uniform_sell_form);
        setSupportActionBar((Toolbar) findViewById(C0418R.id.toolbar));
        this.sharedPreferences = getSharedPreferences("my_preferences", 0);
        this.editor = this.sharedPreferences.edit();
        if (!this.sharedPreferences.getBoolean("Agree", false)) {
            OnClickListener agreement_dialog_listner = new C23071();
            Builder agreement_alert_dialog = new Builder(this, C0418R.style.DialogTheme);
            agreement_alert_dialog.setCancelable(false);
            agreement_alert_dialog.setMessage("Neither College nor Developers are responsible for any conflicts or dispute that may arise between the customer and seller.").setPositiveButton("I Agree", agreement_dialog_listner).setNegativeButton("I Dont Agree", agreement_dialog_listner).show();
        }
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(C0418R.id.coordinate_layout);
        this.f1573n = ((ConnectivityManager) getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        this.sell_uniform_submit_button = (Button) findViewById(C0418R.id.sell_uniform_submit_button);
        this.male = (RadioButton) findViewById(C0418R.id.radioButton_male);
        this.female = (RadioButton) findViewById(C0418R.id.radioButton_female);
        this.male.setOnCheckedChangeListener(new C23082());
        this.female.setOnCheckedChangeListener(new C23093());
        this.shirt_size_spinner = (Spinner) findViewById(C0418R.id.uniform_shirt_size_spinner);
        this.pant_size_spinner = (Spinner) findViewById(C0418R.id.uniform_pant_size_spinner);
        this.sell_uniform_phone_number_edit_text = (EditText) findViewById(C0418R.id.sell_uniform_phone_number_edit_text);
        this.sell_uniform_price_edit_text = (EditText) findViewById(C0418R.id.sell_uniform_price_edit_text);
        this.sell_uniform_phone_number_edit_text.setText(this.sharedPreferences.getString("PHONE_NO", " "));
        this.shirt_size_spinner.setAdapter(new ArrayAdapter(getApplicationContext(), C0418R.layout.lll, C0418R.id.list4, this.shirt_size_array));
        this.pant_size_spinner.setAdapter(new ArrayAdapter(getApplicationContext(), C0418R.layout.lll, C0418R.id.list4, this.pant_size_array));
        this.shirt_size_spinner.setOnItemSelectedListener(new C23104());
        this.pant_size_spinner.setOnItemSelectedListener(new C23115());
        this.sell_uniform_submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Uniform_sell_form.this.male.isChecked()) {
                    Uniform_sell_form.this.sex = Uniform_sell_form.this.male.getText().toString();
                } else if (Uniform_sell_form.this.female.isChecked()) {
                    Uniform_sell_form.this.sex = Uniform_sell_form.this.female.getText().toString();
                }
                Uniform_sell_form.this.price = Uniform_sell_form.this.sell_uniform_price_edit_text.getText().toString();
                if (Uniform_sell_form.this.f1573n == null || !Uniform_sell_form.this.f1573n.isConnected() || !Uniform_sell_form.this.f1573n.isAvailable()) {
                    Snackbar.make(coordinatorLayout, "Please check your netwok", -1).show();
                } else if (Uniform_sell_form.this.sex.equals("") || Uniform_sell_form.this.price.equals("") || Uniform_sell_form.this.shirt_size.equals("") || Uniform_sell_form.this.pant_size.equals("")) {
                    Snackbar.make(coordinatorLayout, "Please Fill all the feids", -1).show();
                } else {
                    Uniform_sell_form.this.setUniform_data();
                    Toast.makeText(Uniform_sell_form.this.getApplicationContext(), "Successfully Submitted", 0).show();
                    Uniform_sell_form.this.onBackPressed();
                }
            }
        });
    }

    public void setUniform_data() {
        DatabaseReference uniform = FirebaseDatabase.getInstance().getReference("notes_seller").child(this.sharedPreferences.getString("USN", " ")).child("uniform");
        uniform.child("pant_size").setValue(this.pant_size);
        uniform.child("shirt_size").setValue(this.shirt_size);
        uniform.child(Param.PRICE).setValue(this.price);
        uniform.child("sex").setValue(this.sex);
        DatabaseReference uniform_list = FirebaseDatabase.getInstance().getReference("uniform_list").child(getSharedPreferences("my_preferences", 0).getString("USN", ""));
        uniform_list.child("pant_size").setValue(this.pant_size);
        uniform_list.child("shirt_size").setValue(this.shirt_size);
        uniform_list.child(Param.PRICE).setValue(this.price);
        uniform_list.child("sex").setValue(this.sex);
        uniform_list.child("phone_no").setValue(getSharedPreferences("my_preferences", 0).getString("PHONE_NO", ""));
    }
}
