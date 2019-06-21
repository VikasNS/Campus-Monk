package notes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import com.campusmonk.vikas.msrit.C0418R;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.drive.DriveFile;
import java.util.ArrayList;
import sell.SellTextBookForm;
import sell.Uniform_sell_form;

public class Notes extends Fragment {
    private static final int WRITE_EXTERNAL_STORAGE = 10;
    BroadcastReceiver DownloadStateUpdateBroadcastReceiver;
    private AdView adView;
    ArrayAdapter<String> arrayAdapter;
    AnimationDrawable female_animation_drawable;
    ImageView female_image_view;
    AnimationDrawable male_animation_drawable;
    ImageView male_image_view;
    ArrayList<Notes_variables_template> notes_arraylist;
    RecyclerView notes_recycler_view;
    Notes_recycler_view_adaptor notes_recycler_view_adaptor;
    Notes_variables_template notes_variables_template;
    ArrayList<question_paper_variable_template> question_paper_array_list;
    question_paper_recycler_adapter question_paper_recycler_adapter;
    RecyclerView question_paper_recycler_view;
    Spinner subject;
    String[] subject_name;
    Button textbook_sell_button;
    Button uniform_sell_button;

    /* renamed from: notes.Notes$1 */
    class C22961 implements OnClickListener {
        C22961() {
        }

        public void onClick(View view) {
            Intent SellTextBookFormIntent = new Intent(Notes.this.getContext(), SellTextBookForm.class);
            SellTextBookFormIntent.putExtra("SubjectName", Notes.this.subject_name[Notes.this.subject.getSelectedItemPosition()]);
            SellTextBookFormIntent.addFlags(DriveFile.MODE_READ_ONLY);
            Notes.this.startActivity(SellTextBookFormIntent);
        }
    }

    /* renamed from: notes.Notes$2 */
    class C22972 implements OnClickListener {
        C22972() {
        }

        public void onClick(View v) {
            Notes.this.startActivity(new Intent(Notes.this.getActivity().getApplicationContext(), Uniform_sell_form.class));
        }
    }

    /* renamed from: notes.Notes$3 */
    class C22983 implements OnItemSelectedListener {
        C22983() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Engineering_Physics), Notes.this.getResources().getStringArray(C0418R.array.Engineering_Physics_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.physics_quesion_papers), Notes.this.subject_name[i]);
            } else if (i == 1) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Basic_Civil_Engineering_and_Mechanics), Notes.this.getResources().getStringArray(C0418R.array.Basic_Civil_Engineering_and_Mechanics_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.civil_question_papers), Notes.this.subject_name[i]);
            } else if (i == 2) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Basic_Electrical_Engineering), Notes.this.getResources().getStringArray(C0418R.array.Basic_Electrical_Engineering_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.electrical_question_papers), Notes.this.subject_name[i]);
            } else if (i == 3) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Fundamentals_of_Computing), Notes.this.getResources().getStringArray(C0418R.array.Fundamentals_of_Computing_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.computer_question_papers), Notes.this.subject_name[i]);
            } else if (i == 4) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Professional_Communication), Notes.this.getResources().getStringArray(C0418R.array.Professional_Communication_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.professional_communication_question_papers), Notes.this.subject_name[i]);
            } else if (i == 5) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Engineering_Mathematics_II), Notes.this.getResources().getStringArray(C0418R.array.Engineering_Mathematics_II_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.maths2_question_papers), Notes.this.subject_name[i]);
            } else if (i == 6) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Engineering_Chemistry), Notes.this.getResources().getStringArray(C0418R.array.Engineering_Chemistry_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.chemisty_question_papers), Notes.this.subject_name[i]);
            } else if (i == 7) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Elements_of_Mechanical_Engineering), Notes.this.getResources().getStringArray(C0418R.array.Elements_of_Mechanical_Engineering_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.mechanical_question_papers), Notes.this.subject_name[i]);
            } else if (i == 8) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Basic_Electronics_Electronics_Communication_Engineering), Notes.this.getResources().getStringArray(C0418R.array.Basic_Electronics_Electronics_Communication_Engineering_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.electronics_question_papers), Notes.this.subject_name[i]);
            } else if (i == 9) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Constitution_of_India_Professional_Ethics), Notes.this.getResources().getStringArray(C0418R.array.Constitution_of_India_Professional_Ethics_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.consitution_question_papers), Notes.this.subject_name[i]);
            } else if (i == 10) {
                Notes.this.setUpAdapter(Notes.this.getResources().getStringArray(C0418R.array.Environmental_Studies), Notes.this.getResources().getStringArray(C0418R.array.Environmental_Studies_Notes), Notes.this.subject_name[i]);
                Notes.this.setUpQuestionPaperAdapter(Notes.this.getResources().getStringArray(C0418R.array.Question_paper_title), Notes.this.getResources().getStringArray(C0418R.array.evs_question_papers), Notes.this.subject_name[i]);
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: notes.Notes$4 */
    class C22994 extends BroadcastReceiver {
        C22994() {
        }

        public void onReceive(Context context, Intent intent) {
            Notes.this.notes_recycler_view_adaptor.notifyDataSetChanged();
            Notes.this.question_paper_recycler_adapter.notifyDataSetChanged();
            Log.v("BCR status", "received");
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.male_animation_drawable.start();
        this.female_animation_drawable.start();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(C0418R.layout.notes_main, container, false);
        RelativeLayout adViewContainer = (RelativeLayout) view.findViewById(C0418R.id.adViewContainer);
        Button textbook_sell_button = (Button) view.findViewById(C0418R.id.textbook_sell_button);
        this.adView = new AdView(getActivity().getApplication(), "385284181844331_387924098247006", AdSize.BANNER_320_50);
        adViewContainer.addView(this.adView);
        this.adView.loadAd();
        this.male_image_view = (ImageView) view.findViewById(C0418R.id.male_image_view);
        this.female_image_view = (ImageView) view.findViewById(C0418R.id.female_image_view);
        this.male_image_view.setBackgroundResource(C0418R.drawable.animator_male);
        this.female_image_view.setBackgroundResource(C0418R.drawable.animator_female);
        this.male_animation_drawable = (AnimationDrawable) this.male_image_view.getBackground();
        this.female_animation_drawable = (AnimationDrawable) this.female_image_view.getBackground();
        textbook_sell_button.setOnClickListener(new C22961());
        this.uniform_sell_button = (Button) view.findViewById(C0418R.id.sell_uniform_button);
        this.uniform_sell_button.setOnClickListener(new C22972());
        this.notes_arraylist = new ArrayList();
        this.question_paper_array_list = new ArrayList();
        this.subject_name = getResources().getStringArray(C0418R.array.subject_names);
        this.subject = (Spinner) view.findViewById(C0418R.id.subjects_spinner);
        LayoutManager notes_recycler_view_layoutManager = new LinearLayoutManager(getActivity().getApplication(), 0, false);
        LayoutManager question_paper_recycler_view_layoutManager = new LinearLayoutManager(getActivity().getApplication(), 0, false);
        this.notes_recycler_view = (RecyclerView) view.findViewById(C0418R.id.notes_recycler_view);
        this.question_paper_recycler_view = (RecyclerView) view.findViewById(C0418R.id.question_paper_recycler_view);
        this.notes_recycler_view.setNestedScrollingEnabled(false);
        this.notes_recycler_view.setLayoutManager(notes_recycler_view_layoutManager);
        this.question_paper_recycler_view.setLayoutManager(question_paper_recycler_view_layoutManager);
        this.arrayAdapter = new ArrayAdapter(getContext(), C0418R.layout.spinner_layout, C0418R.id.text, this.subject_name);
        this.subject.setAdapter(this.arrayAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.notes_recycler_view.getContext(), 0);
        this.notes_recycler_view.addItemDecoration(dividerItemDecoration);
        this.question_paper_recycler_view.addItemDecoration(dividerItemDecoration);
        this.subject.setOnItemSelectedListener(new C22983());
        this.DownloadStateUpdateBroadcastReceiver = new C22994();
        return view;
    }

    public void onPause() {
        super.onPause();
        getActivity().getApplicationContext().unregisterReceiver(this.DownloadStateUpdateBroadcastReceiver);
    }

    public void onResume() {
        super.onResume();
        getActivity().getApplicationContext().registerReceiver(this.DownloadStateUpdateBroadcastReceiver, new IntentFilter("Download State Update"));
    }

    public void setUpAdapter(String[] sub_name_array, String[] download_link, String subject_name) {
        this.notes_arraylist.clear();
        for (int q = 0; q < download_link.length; q++) {
            this.notes_variables_template = new Notes_variables_template(sub_name_array[q], download_link[q]);
            this.notes_arraylist.add(this.notes_variables_template);
        }
        this.notes_recycler_view_adaptor = new Notes_recycler_view_adaptor(this.notes_arraylist, getActivity(), subject_name);
        this.notes_recycler_view.setAdapter(this.notes_recycler_view_adaptor);
        this.notes_recycler_view_adaptor.notifyDataSetChanged();
    }

    public void setUpQuestionPaperAdapter(String[] sub_title, String[] download_link, String subject_name) {
        this.question_paper_array_list.clear();
        for (int p = 0; p < sub_title.length; p++) {
            this.question_paper_array_list.add(new question_paper_variable_template(getActivity(), sub_title[p], download_link[p]));
        }
        this.question_paper_recycler_adapter = new question_paper_recycler_adapter(this.question_paper_array_list, getActivity(), subject_name);
        this.question_paper_recycler_view.setAdapter(this.question_paper_recycler_adapter);
        this.question_paper_recycler_adapter.notifyDataSetChanged();
    }
}
