package com.example.firebasecrud.view.activities.parallax;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class ParallaxBehavior extends AppBarLayout.Behavior {
    public final static String TAG = "ParallaxBehaviorLog";

    private static final int INVALID_POINTER = -1;

    private boolean mIsBeginDragged;
    private int mActivePointerId = INVALID_POINTER;
    private int mLastMotionY;
    private int mTouchSlop = -1;
    private MotionEvent mCurrentDownEvent;
    private boolean mNeedDispatchDown;
    private View mScrollingViewBehaviorView;

    public ParallaxBehavior() {

    }

    public ParallaxBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (mTouchSlop < 0) {
            mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: action is " + ev.getAction());
        switch (ev.getActionMasked()) {
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }
}
