package start_up_screen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.campusmonk.vikas.msrit.C0418R;
import com.campusmonk.vikas.msrit.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register_form_layout extends Fragment {
    EditText DOB;
    EditText NAME;
    EditText PHONE_NO;
    EditText USN;
    /* renamed from: a */
    Activity f1581a;
    String android_id;
    /* renamed from: p */
    ProgressDialog f1582p;
    EditText section;
    Button submit;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.f1581a = getActivity();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(C0418R.layout.register_form_layout, container, false);
        this.DOB = (EditText) view.findViewById(C0418R.id.editText_DOB);
        this.NAME = (EditText) view.findViewById(C0418R.id.editText_NAME);
        this.USN = (EditText) view.findViewById(C0418R.id.editText_USN);
        this.PHONE_NO = (EditText) view.findViewById(C0418R.id.editText_Phone_no);
        this.submit = (Button) view.findViewById(C0418R.id.submit_button);
        this.section = (EditText) view.findViewById(C0418R.id.section);
        this.android_id = Secure.getString(getActivity().getContentResolver(), "android_id");
        this.f1582p = new ProgressDialog(getActivity());
        this.f1582p.setCancelable(false);
        this.f1582p.setMessage("Registerring");
        this.submit.setOnClickListener(new OnClickListener() {

            /* renamed from: start_up_screen.register_form_layout$1$1 */
            class C23261 implements ValueEventListener {
                C23261() {
                }

                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(register_form_layout.this.getActivity().getApplicationContext().getSharedPreferences("my_preferences", 0).getString("android_id", "")).exists()) {
                        register_form_layout.this.f1582p.dismiss();
                        Snackbar.make(view, "This device is allready registered,Please LOGIN", 0).show();
                        return;
                    }
                    SharedPreferences sharedPreferences = register_form_layout.this.getActivity().getApplicationContext().getSharedPreferences("my_preferences", 0);
                    DatabaseReference d = FirebaseDatabase.getInstance().getReference("Regested_users_Details").child(sharedPreferences.getString("android_id", ""));
                    d.child("USN").setValue(sharedPreferences.getString("USN", ""));
                    d.child("NAME").setValue(sharedPreferences.getString("NAME", ""));
                    d.child("DOB").setValue(sharedPreferences.getString("DOB", ""));
                    d.child("PHONE_NO").setValue(sharedPreferences.getString("PHONE_NO", ""));
                    d.child("section").setValue(sharedPreferences.getString("section", ""));
                    FirebaseDatabase.getInstance().getReference("Login_details").child(sharedPreferences.getString("USN", "")).setValue(sharedPreferences.getString("DOB", ""));
                    Intent i = new Intent(register_form_layout.this.getActivity().getApplicationContext(), MainActivity.class);
                    i.setFlags(268468224);
                    register_form_layout.this.getActivity().getApplicationContext().startActivity(i);
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            }

            public void onClick(View v) {
                register_form_layout.this.f1582p.show();
                if (!online()) {
                    register_form_layout.this.f1582p.dismiss();
                    Snackbar.make(view, "Check your network connection", 0).show();
                } else if (register_form_layout.this.USN.getText().toString().equals("") || register_form_layout.this.NAME.getText().toString().equals("") || register_form_layout.this.DOB.getText().toString().equals("") || register_form_layout.this.PHONE_NO.toString().equals("")) {
                    register_form_layout.this.f1582p.dismiss();
                    Snackbar.make(view, "Please fill all the feilds", 0).show();
                } else {
                    Editor editor = register_form_layout.this.getActivity().getApplicationContext().getSharedPreferences("my_preferences", 0).edit();
                    editor.putString("android_id", register_form_layout.this.android_id);
                    editor.putString("USN", register_form_layout.this.USN.getText().toString().toUpperCase());
                    editor.putString("NAME", register_form_layout.this.NAME.getText().toString().toUpperCase());
                    editor.putString("DOB", register_form_layout.this.DOB.getText().toString());
                    editor.putString("PHONE_NO", register_form_layout.this.PHONE_NO.getText().toString());
                    editor.putBoolean("firstlaunch", false);
                    editor.putString("section", register_form_layout.this.section.getText().toString());
                    if (editor.commit()) {
                        FirebaseDatabase.getInstance().getReference("Regested_users_Details").addListenerForSingleValueEvent(new C23261());
                    }
                }
            }

            private boolean online() {
                NetworkInfo n = ((ConnectivityManager) register_form_layout.this.getActivity().getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
                return n != null && n.isAvailable() && n.isConnected();
            }
        });
        return view;
    }
}
