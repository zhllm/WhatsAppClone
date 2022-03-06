package com.example.firebasecrud.view.activities.parallax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.example.firebasecrud.R;
import com.example.firebasecrud.databinding.ActivityParallaxBinding;
import com.example.firebasecrud.view.activities.parallax.fragments.RecycleViewFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ParallaxActivity extends AppCompatActivity {
    private ActivityParallaxBinding binding;
    final String[] labels = new String[]{"个性推荐", "歌单", "主播电台", "排行榜"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parallax);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, labels);
        binding.viewpageView.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewpageView, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(labels[position]);
            }
        }).attach();

        binding.viewpageView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("hello onPageSelected", "" + position);
                super.onPageSelected(position);
            }
        });
    }


    static class ViewPagerAdapter extends FragmentStateAdapter {
        private final String[] labels;

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String[] labels) {
            super(fragmentActivity);
            this.labels = labels;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new RecycleViewFragment();
        }

        @Override
        public int getItemCount() {
            return labels.length;
        }
    }
}