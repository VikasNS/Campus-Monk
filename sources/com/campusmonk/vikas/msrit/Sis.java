package com.campusmonk.vikas.msrit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.firebase.database.FirebaseDatabase;

public class Sis extends Fragment {
    EditText ed;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C0418R.layout.sis_main, container, false);
    }
}
