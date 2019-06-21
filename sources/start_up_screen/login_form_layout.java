package start_up_screen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_form_layout extends Fragment {
    EditText DOB;
    Button Login;
    EditText USN;
    /* renamed from: a */
    Activity f1575a;
    /* renamed from: e */
    Editor f1576e;
    /* renamed from: i */
    int f1577i = 0;
    /* renamed from: n */
    NetworkInfo f1578n;
    /* renamed from: p */
    ProgressDialog f1579p;
    /* renamed from: s */
    SharedPreferences f1580s;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.f1579p = new ProgressDialog(getActivity());
        this.f1579p.setCancelable(false);
        this.f1579p.setMessage("Logging In");
        this.f1579p.setProgressStyle(0);
        this.f1575a = getActivity();
        final View view = inflater.inflate(C0418R.layout.login_form_layout, container, false);
        this.USN = (EditText) view.findViewById(C0418R.id.login_form_usn_edit_text);
        this.DOB = (EditText) view.findViewById(C0418R.id.login_form_DOB_edit_text);
        this.Login = (Button) view.findViewById(C0418R.id.login_form_submit_button);
        this.f1578n = ((ConnectivityManager) getActivity().getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        this.f1580s = getActivity().getApplicationContext().getSharedPreferences("my_preferences", 0);
        this.f1576e = this.f1580s.edit();
        this.Login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                login_form_layout.this.f1579p.show();
                if (login_form_layout.this.f1578n == null || !login_form_layout.this.f1578n.isConnected() || !login_form_layout.this.f1578n.isAvailable()) {
                    login_form_layout.this.f1579p.dismiss();
                    Snackbar.make(view, "Please check your connection", 0).show();
                } else if (login_form_layout.this.USN.getText().toString().equals("") || login_form_layout.this.DOB.getText().toString().equals("")) {
                    login_form_layout.this.f1579p.dismiss();
                    Snackbar.make(view, "Please fill all the feilds", 0).show();
                } else {
                    login_form_layout.this.verifyLogin(login_form_layout.this.USN.getText().toString().toUpperCase(), login_form_layout.this.DOB.getText().toString(), view);
                }
            }
        });
        return view;
    }

    private void verifyLogin(final String USN, final String DOB, final View view) {
        FirebaseDatabase.getInstance().getReference().child("Login_details").addListenerForSingleValueEvent(new ValueEventListener() {

            /* renamed from: start_up_screen.login_form_layout$2$1 */
            class C23231 implements ValueEventListener {
                C23231() {
                }

                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        login_form_layout.this.f1576e.putString("section", child.child("section").getValue().toString()).commit();
                    }
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            }

            /* renamed from: start_up_screen.login_form_layout$2$2 */
            class C23242 implements ValueEventListener {
                C23242() {
                }

                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        login_form_layout.this.f1576e.putString(child.child("DOB").getKey(), child.child("DOB").getValue().toString());
                        login_form_layout.this.f1576e.putString(child.child("NAME").getKey(), child.child("NAME").getValue().toString());
                        login_form_layout.this.f1576e.putString(child.child("PHONE_NO").getKey(), child.child("PHONE_NO").getValue().toString());
                        login_form_layout.this.f1576e.putString(child.child("USN").getKey(), child.child("USN").getValue().toString());
                        login_form_layout.this.f1576e.commit();
                    }
                    Intent i = new Intent(login_form_layout.this.getActivity().getApplicationContext(), MainActivity.class);
                    i.setFlags(268468224);
                    login_form_layout.this.getActivity().getApplicationContext().startActivity(i);
                    login_form_layout.this.f1576e.putBoolean("firstlaunch", false);
                    login_form_layout.this.f1576e.commit();
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(USN).exists()) {
                    login_form_layout.this.f1579p.dismiss();
                    Snackbar.make(view, "This account does not exist.Please Register", -1).show();
                } else if (dataSnapshot.child(USN).getValue().toString().equals(DOB)) {
                    Log.v("Login", "successfull");
                    if (login_form_layout.this.getActivity().getSharedPreferences("my_preferences", 0).getString("section", "false").equals("false")) {
                        FirebaseDatabase.getInstance().getReference("Regested_users_Details").orderByChild("USN").equalTo(USN).addListenerForSingleValueEvent(new C23231());
                    }
                    FirebaseDatabase.getInstance().getReference().child("Regested_users_Details").orderByChild("USN").equalTo(USN).addListenerForSingleValueEvent(new C23242());
                } else {
                    login_form_layout.this.f1579p.dismiss();
                    Snackbar.make(view, "Check Credentials", -1).show();
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
