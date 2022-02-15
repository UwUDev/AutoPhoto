package me.uwu.autophoto;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import me.uwu.autophoto.data.DataManager;
import me.uwu.autophoto.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!DataManager.hasSetup(getApplicationContext()))
            DataManager.setup(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        final FloatingActionButton fab = findViewById(R.id.fab);

        if (DataManager.isEnabled(getApplicationContext())) {
            fab.setBackgroundTintList(ColorStateList.valueOf(0xff00c853));
            fab.setImageResource(R.drawable.ic_baseline_check_24);
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(0xfffbc02d));
            fab.setImageResource(R.drawable.ic_baseline_clear_24);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataManager.toggle(getApplicationContext());

                if (DataManager.isEnabled(getApplicationContext())) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(0xff00c853));
                    fab.setImageResource(R.drawable.ic_baseline_check_24);
                    Toast.makeText(getApplicationContext(), "Enabled", Toast.LENGTH_LONG).show();
                } else {
                    fab.setBackgroundTintList(ColorStateList.valueOf(0xfffbc02d));
                    fab.setImageResource(R.drawable.ic_baseline_clear_24);
                    Toast.makeText(getApplicationContext(), "Disabled", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}