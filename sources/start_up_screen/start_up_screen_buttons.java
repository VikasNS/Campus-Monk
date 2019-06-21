package start_up_screen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.campusmonk.vikas.msrit.C0418R;

public class start_up_screen_buttons extends Fragment {
    /* renamed from: a */
    Activity f1583a;
    Button login_button;
    Button register_button;

    public interface onButtonClicked {
        void onButtonClickedMethod(int i);
    }

    /* renamed from: start_up_screen.start_up_screen_buttons$1 */
    class C23281 implements OnClickListener {
        C23281() {
        }

        public void onClick(View v) {
            ((onButtonClicked) start_up_screen_buttons.this.f1583a).onButtonClickedMethod(1);
        }
    }

    /* renamed from: start_up_screen.start_up_screen_buttons$2 */
    class C23292 implements OnClickListener {
        C23292() {
        }

        public void onClick(View v) {
            ((onButtonClicked) start_up_screen_buttons.this.f1583a).onButtonClickedMethod(2);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.f1583a = getActivity();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(C0418R.layout.start_screen_buttons, container, false);
        this.login_button = (Button) view.findViewById(C0418R.id.login_button);
        this.register_button = (Button) view.findViewById(C0418R.id.register_button);
        this.login_button.setOnClickListener(new C23281());
        this.register_button.setOnClickListener(new C23292());
        return view;
    }
}
