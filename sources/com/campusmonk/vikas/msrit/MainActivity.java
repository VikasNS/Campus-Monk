package com.campusmonk.vikas.msrit;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.drive.DriveFile;
import sell.my_products;
import start_up_screen.start_screen;

public class MainActivity extends AppCompatActivity implements OnTabSelectedListener {
    TabLayout tabLayout;
    Toolbar toolbar;
    ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0418R.layout.activity_main);
        getWindow().setSoftInputMode(3);
        this.viewPager = (ViewPager) findViewById(C0418R.id.view_pager);
        this.toolbar = (Toolbar) findViewById(C0418R.id.toolbar);
        this.tabLayout = (TabLayout) findViewById(C0418R.id.tab_layout);
        setSupportActionBar(this.toolbar);
        this.viewPager.setAdapter(new Main_pager_adaptor(getSupportFragmentManager()));
        this.viewPager.setOffscreenPageLimit(2);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.tabLayout.getTabAt(0).setText((CharSequence) "Search");
        this.tabLayout.getTabAt(1).setText((CharSequence) "Shop");
        if (getSharedPreferences("my_preferences", 0).getBoolean("firstlaunch", true)) {
            Intent i = new Intent(getApplicationContext(), start_screen.class);
            i.setFlags(268468224);
            getApplicationContext().startActivity(i);
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0418R.id.my_products:
                startActivity(new Intent(getApplicationContext(), my_products.class));
                return true;
            case C0418R.id.logout:
                Editor e = getSharedPreferences("my_preferences", 0).edit();
                e.remove("USN");
                e.remove("NAME");
                e.remove("DOB");
                e.remove("PHONE_NO");
                e.remove("PHONE_NO");
                e.remove("section");
                e.putBoolean("firstlaunch", true);
                e.putBoolean("Agree", false);
                e.commit();
                Intent i = new Intent(getApplicationContext(), start_screen.class);
                i.setFlags(268468224);
                startActivity(i);
                return true;
            case C0418R.id.about:
                Intent about_intent = new Intent(getApplicationContext(), About.class);
                about_intent.setFlags(DriveFile.MODE_READ_ONLY);
                startActivity(about_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0418R.menu.menu, menu);
        return true;
    }

    public void onTabSelected(Tab tab) {
        this.viewPager.setCurrentItem(tab.getPosition());
    }

    public void onTabUnselected(Tab tab) {
    }

    public void onTabReselected(Tab tab) {
    }
}
