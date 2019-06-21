package com.campusmonk.vikas.msrit;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.Toast;

public class Date_picker_fragment extends DialogFragment {

    /* renamed from: com.campusmonk.vikas.msrit.Date_picker_fragment$1 */
    class C04171 implements OnDateSetListener {
        C04171() {
        }

        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Toast.makeText(Date_picker_fragment.this.getActivity(), i + "-" + i1 + "-" + i2, 1).show();
        }
    }

    @RequiresApi(api = 24)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), C0418R.style.DialogTheme, new C04171(), c.get(5), c.get(2), c.get(1));
    }
}
