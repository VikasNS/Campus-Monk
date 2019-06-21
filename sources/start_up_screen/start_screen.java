package start_up_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.campusmonk.vikas.msrit.C0418R;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import start_up_screen.start_up_screen_buttons.onButtonClicked;

public class start_screen extends AppCompatActivity implements onButtonClicked {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0418R.layout.start_screen_layout);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(C0418R.id.startup_holder, new start_up_screen_buttons(), "start_screen_buttons_fragment");
        fragmentTransaction.commit();
    }

    public void onButtonClickedMethod(int button_type) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (button_type == 1) {
            fragmentTransaction.replace(C0418R.id.startup_holder, new login_form_layout(), Event.LOGIN);
            fragmentTransaction.addToBackStack(Event.LOGIN);
        } else {
            fragmentTransaction.replace(C0418R.id.startup_holder, new register_form_layout(), "register");
            fragmentTransaction.addToBackStack("register");
        }
        fragmentTransaction.addToBackStack(Event.LOGIN);
        fragmentTransaction.commit();
    }
}
