package com.example.firebasecrud.view.activities.display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.firebasecrud.R;
import com.example.firebasecrud.common.Common;
import com.example.firebasecrud.databinding.ActivityViewImageBinding;

public class ViewImageActivity extends AppCompatActivity {
    private ActivityViewImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_image);
        binding.imageView.setImageBitmap(Common.IMAGE_BITMAP);
    }
}