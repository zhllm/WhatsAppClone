package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.firebasecrud.databinding.ActivityMainBinding;
import com.example.firebasecrud.fragments.RotateFragment;
import com.example.firebasecrud.fragments.ScaleFragment;
import com.example.firebasecrud.fragments.TranslateFragment;
import com.example.firebasecrud.view.activities.contact.ContactActivity;
import com.example.firebasecrud.view.activities.settings.SettingsActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolBar);
        binding.viewpage2.setAdapter(new SectionAdapter(this));
        new TabLayoutMediator(binding.tabLayout, binding.viewpage2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("旋转");
                        break;
                    case 1:
                        tab.setText("缩放");
                        break;
                    case 2:
                    default:
                        tab.setText("偏移");
                }
            }
        }).attach();
        binding.viewpage2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("hello onPageSelected", "" + position);
                super.onPageSelected(position);
                changeFloatingButton(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_search:
                Toast.makeText(this, "menu_search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_new_group:
                Toast.makeText(this, "scale_fragment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_new_broadcast:
                Toast.makeText(this, "action_new_broadcast", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_wa_web:
                Toast.makeText(this, "action_wa_web", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_starred_message:
                Toast.makeText(this, "action_starred_message", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_setting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            default:
                Toast.makeText(this, "other", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFloatingButton(final int index) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.floating.hide();
                switch (index) {
                    case 0:
                        binding.floating.setImageDrawable(getDrawable(R.drawable.ic_baseline_call_24));
                        binding.floating.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, ContactActivity.class));
                            }
                        });
                        break;
                    case 1:
                        binding.floating.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_alt_24));
                        break;
                    case 2:
                        binding.floating.setImageDrawable(getDrawable(R.drawable.ic_baseline_chat_bubble_24));
                }
                binding.floating.show();
            }
        }, 400);
    }

    private static class SectionAdapter extends FragmentStateAdapter {


        public SectionAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Log.d("hello", "" + position);
            switch (position) {
                case 0:
                    return new RotateFragment();
                case 1:
                    return new ScaleFragment();
                case 2:
                default:
                    return new TranslateFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}