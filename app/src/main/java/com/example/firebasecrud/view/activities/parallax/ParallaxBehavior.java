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
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: action is " + ev.getAction());
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mIsBeginDragged = false;
                mNeedDispatchDown = true;
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                if (parent.isPointInChildBounds(child, x, y)) {
                    mLastMotionY = y;
                    mActivePointerId = ev.getPointerId(0);
                    if (mCurrentDownEvent != null) {
                        mCurrentDownEvent.recycle();
                    }
                    mCurrentDownEvent = MotionEvent.obtain(ev);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) {
                    break;
                }
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1) {
                    break;
                }
                final int _y = (int) ev.getY(pointerIndex);
                final int yDiff = Math.abs(_y - mLastMotionY);
                if (yDiff > mTouchSlop) {
                    mIsBeginDragged = true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeginDragged = false;
                mNeedDispatchDown = true;
                mActivePointerId = INVALID_POINTER;
                break;
        }
//        return super.onInterceptTouchEvent(parent, child, ev);
        return mIsBeginDragged;
    }


    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull MotionEvent ev) {
        Log.d(TAG, "onTouchEvent: action : is " + ev.getAction() + "mIsBeginDragged: " + (mIsBeginDragged ? "true" : "false"));
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE: {
                if (mIsBeginDragged) {
                    final int offset = child.getHeight() - child.getBottom();
                    if (mNeedDispatchDown) {
                        mNeedDispatchDown = false;
                        mCurrentDownEvent.offsetLocation(0, offset);
                        mScrollingViewBehaviorView.dispatchTouchEvent(mCurrentDownEvent);
                    }
                    ev.offsetLocation(0, offset);
                    mScrollingViewBehaviorView.dispatchTouchEvent(ev);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                if (mIsBeginDragged) {
                    ev.offsetLocation(0, child.getHeight() - child.getBottom());
                    mScrollingViewBehaviorView.dispatchTouchEvent(ev);
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout abl, int layoutDirection) {
        boolean handle = super.onLayoutChild(parent, abl, layoutDirection);
        for (int i = 0, c = parent.getChildCount(); i < c; i++) {
            View sibling = parent.getChildAt(i);
            final CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) sibling.getLayoutParams()).getBehavior();
            if (behavior != null && behavior instanceof AppBarLayout.ScrollingViewBehavior) {
                Log.d(TAG, "onLayoutChild: " + sibling.getId());
                mScrollingViewBehaviorView = sibling;
            }
        }
        return handle;
    }
}
