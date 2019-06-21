package com.campusmonk.vikas.msrit;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.drive.DriveFile;
import events.events_recycler_view_adapter;
import events.events_variable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import timeTable.timeTableRecyclerViewAdaptor;
import timeTable.timeTableVariable;

public class Search extends Fragment {
    String[] NAVIGATION_ANSWER;
    String[] NAVIGATION_PLACE_KEYWORD_ONE;
    String[] NAVIGATION_PLACE_KEYWORD_TWO;
    /* renamed from: a */
    Activity f15a;
    int check;
    RecyclerView eventsRecyclerView;
    ImageView icon;
    ImageView mic;
    TextView response;
    SearchManager searchManager;
    SearchView searchView;
    TextToSpeech textToSpeech;
    RecyclerView timeTableRecyclerView;
    ArrayList<String> voice_recognized_text;

    /* renamed from: com.campusmonk.vikas.msrit.Search$1 */
    class C04191 implements OnInitListener {
        C04191() {
        }

        public void onInit(int i) {
            if (i == 0) {
                Search.this.check = Search.this.textToSpeech.setLanguage(Locale.US);
            }
        }
    }

    /* renamed from: com.campusmonk.vikas.msrit.Search$2 */
    class C04202 implements OnClickListener {
        C04202() {
        }

        public void onClick(View view) {
            Search.this.setUpVoiceRecognition();
        }
    }

    /* renamed from: com.campusmonk.vikas.msrit.Search$3 */
    class C04213 implements OnQueryTextListener {
        C04213() {
        }

        public boolean onQueryTextSubmit(String query) {
            Search.this.check(query);
            Search.this.setVisibility(false);
            Search.this.searchView.clearFocus();
            return false;
        }

        public boolean onQueryTextChange(String newText) {
            if (newText.length() > 0) {
                Log.v("String", "inside check");
                Search.this.check(newText);
            }
            return false;
        }
    }

    /* renamed from: com.campusmonk.vikas.msrit.Search$4 */
    class C04224 implements OnFocusChangeListener {
        C04224() {
        }

        public void onFocusChange(View view, boolean b) {
            Search.this.setVisibility(b);
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(C0418R.layout.search_main, container, false);
        this.f15a = getActivity();
        this.mic = (ImageView) view.findViewById(C0418R.id.mic);
        LayoutManager layoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext(), 0, false);
        LayoutManager layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext(), 1, false);
        this.timeTableRecyclerView = (RecyclerView) view.findViewById(C0418R.id.timeTableRecyclerView);
        this.timeTableRecyclerView.setLayoutManager(layoutManager1);
        this.eventsRecyclerView = (RecyclerView) view.findViewById(C0418R.id.eventsRecyclerView);
        this.eventsRecyclerView.setLayoutManager(layoutManager2);
        this.timeTableRecyclerView.addItemDecoration(new DividerItemDecoration(this.timeTableRecyclerView.getContext(), 0));
        this.eventsRecyclerView.addItemDecoration(new DividerItemDecoration(this.eventsRecyclerView.getContext(), 1));
        this.searchView = (SearchView) view.findViewById(C0418R.id.search_view);
        RelativeLayout adViewContainer = (RelativeLayout) view.findViewById(C0418R.id.adViewContainer);
        AdView adView = new AdView(getActivity().getApplicationContext(), "385284181844331_387830401589709", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();
        this.NAVIGATION_PLACE_KEYWORD_ONE = getResources().getStringArray(C0418R.array.NAVIGATION_PLACE_KEYWORD_ONE);
        this.NAVIGATION_PLACE_KEYWORD_TWO = getResources().getStringArray(C0418R.array.NAVIGATION_PLACE_KEYWORD_TWO);
        this.NAVIGATION_ANSWER = getResources().getStringArray(C0418R.array.NAVIGATION_ANSWER);
        this.searchView.setIconifiedByDefault(false);
        this.textToSpeech = new TextToSpeech(getContext(), new C04191());
        this.mic.setOnClickListener(new C04202());
        this.searchView.setOnQueryTextListener(new C04213());
        this.searchView.setOnQueryTextFocusChangeListener(new C04224());
        this.searchView.setQueryHint("Search...");
        ImageView searchViewIcon = (ImageView) this.searchView.findViewById(C0418R.id.search_mag_icon);
        ((ViewGroup) searchViewIcon.getParent()).removeView(searchViewIcon);
        if (!getActivity().getApplicationContext().getSharedPreferences("my_preferences", 0).getBoolean("firstlaunch", true)) {
            setUpTimeTableRecylerView();
        }
        setUpEventRecylerView();
        return view;
    }

    private void setUpEventRecylerView() {
        String[] date = null;
        String[] day = null;
        String[] title = null;
        switch (Calendar.getInstance().get(2)) {
            case 0:
                date = getResources().getStringArray(C0418R.array.January_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.January_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.January_EVENT_NAME);
                break;
            case 1:
                date = getResources().getStringArray(C0418R.array.February_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.February_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.February_EVENT_NAME);
                break;
            case 2:
                date = getResources().getStringArray(C0418R.array.March_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.March_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.March_EVENT_NAME);
                break;
            case 3:
                date = getResources().getStringArray(C0418R.array.April_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.April_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.April_EVENT_NAME);
                break;
            case 4:
                date = getResources().getStringArray(C0418R.array.May_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.May_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.May_EVENT_NAME);
                break;
            case 5:
                date = getResources().getStringArray(C0418R.array.June_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.June_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.June_EVENT_NAME);
                break;
            case 6:
                date = getResources().getStringArray(C0418R.array.July_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.July_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.July_EVENT_NAME);
                break;
            case 7:
                date = getResources().getStringArray(C0418R.array.August_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.August_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.August_EVENT_NAME);
                break;
            case 9:
                date = getResources().getStringArray(C0418R.array.October_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.October_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.October_EVENT_NAME);
                break;
            case 10:
                date = getResources().getStringArray(C0418R.array.November_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.November_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.November_EVENT_NAME);
                break;
            case 11:
                date = getResources().getStringArray(C0418R.array.December_EVENT_DATE);
                day = getResources().getStringArray(C0418R.array.December_EVENT_DAY);
                title = getResources().getStringArray(C0418R.array.December_EVENT_NAME);
                break;
        }
        ArrayList<events_variable> events_arraylist = new ArrayList();
        for (int i = 0; i < date.length; i++) {
            events_arraylist.add(new events_variable(date[i], day[i], title[i]));
        }
        this.eventsRecyclerView.setAdapter(new events_recycler_view_adapter(events_arraylist));
    }

    public void setUpTimeTableRecylerView() {
        String[] C2_array = null;
        String[] C3_array = null;
        int day = Calendar.getInstance().get(7);
        String[] C1_array = getResources().getStringArray(C0418R.array.C1);
        String string = getActivity().getSharedPreferences("my_preferences", 0).getString("section", "I");
        Object obj = -1;
        switch (string.hashCode()) {
            case 65:
                if (string.equals("A")) {
                    obj = null;
                    break;
                }
                break;
            case 66:
                if (string.equals("B")) {
                    obj = 5;
                    break;
                }
                break;
            case 67:
                if (string.equals("C")) {
                    obj = 16;
                    break;
                }
                break;
            case 68:
                if (string.equals("D")) {
                    obj = 6;
                    break;
                }
                break;
            case 69:
                if (string.equals("E")) {
                    obj = 17;
                    break;
                }
                break;
            case 70:
                if (string.equals("F")) {
                    obj = 8;
                    break;
                }
                break;
            case 71:
                if (string.equals("G")) {
                    obj = 9;
                    break;
                }
                break;
            case 72:
                if (string.equals("H")) {
                    obj = 11;
                    break;
                }
                break;
            case 73:
                if (string.equals("I")) {
                    obj = 10;
                    break;
                }
                break;
            case 74:
                if (string.equals("J")) {
                    obj = 3;
                    break;
                }
                break;
            case 75:
                if (string.equals("K")) {
                    obj = 13;
                    break;
                }
                break;
            case 76:
                if (string.equals("L")) {
                    obj = 15;
                    break;
                }
                break;
            case 77:
                if (string.equals("M")) {
                    obj = 7;
                    break;
                }
                break;
            case 78:
                if (string.equals("N")) {
                    obj = 2;
                    break;
                }
                break;
            case 79:
                if (string.equals("O")) {
                    obj = 1;
                    break;
                }
                break;
            case 80:
                if (string.equals("P")) {
                    obj = 12;
                    break;
                }
                break;
            case 81:
                if (string.equals("Q")) {
                    obj = 14;
                    break;
                }
                break;
            case 82:
                if (string.equals("R")) {
                    obj = 4;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_A);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_A);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_A);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_A);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_A);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_A);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_A);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_A);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_A);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_A);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_A);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_A);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_A);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_A);
                        break;
                    default:
                        break;
                }
            case 1:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_O);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_O);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_O);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_O);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_O);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_O);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_O);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_O);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_O);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_O);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_O);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_O);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_O);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_O);
                        break;
                    default:
                        break;
                }
            case 2:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_N);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_N);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_N);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_N);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_N);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_N);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_N);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_N);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_N);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_N);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_N);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_N);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_N);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_N);
                        break;
                    default:
                        break;
                }
            case 3:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_J);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_J);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_J);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_J);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_J);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_J);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_J);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_J);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_J);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_J);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_J);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_J);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_J);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_J);
                        break;
                    default:
                        break;
                }
            case 4:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_R);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_R);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_R);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_R);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_R);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_R);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_R);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_R);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_R);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_R);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_R);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_R);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_R);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_R);
                        break;
                    default:
                        break;
                }
            case 5:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_B);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_B);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_B);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_B);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_B);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_B);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_B);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_B);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_B);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_B);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_B);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_B);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_B);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_B);
                        break;
                    default:
                        break;
                }
            case 6:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_D);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_D);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_D);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_D);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_D);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_D);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_D);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_D);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_D);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_D);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_D);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_D);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_D);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_D);
                        break;
                    default:
                        break;
                }
            case 7:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_M);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_M);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_M);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_M);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_M);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_M);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_M);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_M);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_M);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_M);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_M);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_M);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_M);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_M);
                        break;
                    default:
                        break;
                }
            case 8:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_F);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_F);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_F);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_F);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_F);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_F);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_F);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_F);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_F);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_F);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_F);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_F);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_F);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_F);
                        break;
                    default:
                        break;
                }
            case 9:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_G);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_G);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_G);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_G);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_G);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_G);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_G);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_G);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_G);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_G);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_G);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_G);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_G);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_G);
                        break;
                    default:
                        break;
                }
            case 10:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_I);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_I);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_I);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_I);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_I);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_I);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_I);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_I);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_I);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_I);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_I);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_I);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_I);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_I);
                        break;
                    default:
                        break;
                }
            case 11:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_H);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_H);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_H);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_H);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_H);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_H);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_H);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_H);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_H);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_H);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_H);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_H);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_H);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_H);
                        break;
                    default:
                        break;
                }
            case 12:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_P);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_P);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_P);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_P);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_P);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_P);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_P);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_P);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_P);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_P);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_P);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_P);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_P);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_P);
                        break;
                    default:
                        break;
                }
            case 13:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_K);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_K);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_K);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_K);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_K);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_K);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_K);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_K);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_K);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_K);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_K);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_K);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_K);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_K);
                        break;
                    default:
                        break;
                }
            case 14:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_Q);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_Q);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_Q);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_Q);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_Q);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_Q);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_Q);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_Q);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_Q);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_Q);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_Q);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_Q);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_Q);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_Q);
                        break;
                    default:
                        break;
                }
            case 15:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_L);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_L);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_L);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_L);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_L);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_L);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_L);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_L);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_L);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_L);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_L);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_L);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_L);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_L);
                        break;
                    default:
                        break;
                }
            case 16:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_C);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_C);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_C);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_C);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_C);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_C);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_C);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_C);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_C);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_C);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_C);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_C);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_C);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_C);
                        break;
                    default:
                        break;
                }
            case 17:
                switch (day) {
                    case 1:
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_E);
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_E);
                        break;
                    case 2:
                        C2_array = getResources().getStringArray(C0418R.array.MONDAY_E);
                        C3_array = getResources().getStringArray(C0418R.array.TUESDAY_E);
                        break;
                    case 3:
                        C2_array = getResources().getStringArray(C0418R.array.TUESDAY_E);
                        C3_array = getResources().getStringArray(C0418R.array.WEDNESDAY_E);
                        break;
                    case 4:
                        C2_array = getResources().getStringArray(C0418R.array.WEDNESDAY_E);
                        C3_array = getResources().getStringArray(C0418R.array.THURSDAY_E);
                        break;
                    case 5:
                        C2_array = getResources().getStringArray(C0418R.array.THURSDAY_E);
                        C3_array = getResources().getStringArray(C0418R.array.FRIDAY_E);
                        break;
                    case 6:
                        C2_array = getResources().getStringArray(C0418R.array.FRIDAY_E);
                        C3_array = getResources().getStringArray(C0418R.array.SATURDAY_E);
                        break;
                    case 7:
                        C2_array = getResources().getStringArray(C0418R.array.SATURDAY_E);
                        C3_array = getResources().getStringArray(C0418R.array.MONDAY_E);
                        break;
                    default:
                        break;
                }
        }
        ArrayList<timeTableVariable> arraylist = new ArrayList();
        for (int i = 0; i < C1_array.length; i++) {
            arraylist.add(new timeTableVariable(C1_array[i], C2_array[i], C3_array[i]));
        }
        this.timeTableRecyclerView.setAdapter(new timeTableRecyclerViewAdaptor(arraylist));
    }

    private String check(String newText) {
        newText = newText.toUpperCase();
        boolean found = false;
        if (newText.contains("Wi-Fi") || newText.contains("WI-FI") || newText.contains("WIFI")) {
            this.textToSpeech.speak("Here you go", 0, null);
            Intent searchResponseIntent = new Intent(this.f15a, searchResponse.class);
            searchResponseIntent.addFlags(DriveFile.MODE_READ_ONLY);
            this.f15a.startActivity(searchResponseIntent);
            found = true;
        }
        if (!found) {
            for (int i = 0; i < this.NAVIGATION_ANSWER.length; i++) {
                Log.v("test", "text=" + newText + " " + "question" + this.NAVIGATION_PLACE_KEYWORD_ONE[i] + " " + "anwer=" + this.NAVIGATION_ANSWER[i]);
                if (newText.contains(this.NAVIGATION_PLACE_KEYWORD_ONE[i])) {
                    found = true;
                    Log.v("String", "inside check3");
                    this.textToSpeech.speak(this.NAVIGATION_ANSWER[i], 0, null);
                    Log.v("test", "text=" + newText + " " + "question" + this.NAVIGATION_PLACE_KEYWORD_ONE[i] + " " + "anwer=" + this.NAVIGATION_ANSWER[i]);
                }
            }
        }
        if (!found) {
            this.textToSpeech.speak("Sorry,Could you repeat me the question", 0, null);
        }
        return this.NAVIGATION_ANSWER[1];
    }

    private void setVisibility(boolean b) {
        if (!b) {
        }
    }

    private void setUpVoiceRecognition() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.PROMPT", "What can I do for you");
        intent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extras.SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS", 2000);
        startActivityForResult(intent, 100);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null) {
            this.voice_recognized_text = data.getStringArrayListExtra("android.speech.extra.RESULTS");
            check(((String) this.voice_recognized_text.get(0)).toString());
            if (true) {
                this.searchView.setQuery((CharSequence) this.voice_recognized_text.get(0), true);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.textToSpeech != null) {
            this.textToSpeech.stop();
            this.textToSpeech.shutdown();
        }
    }
}
