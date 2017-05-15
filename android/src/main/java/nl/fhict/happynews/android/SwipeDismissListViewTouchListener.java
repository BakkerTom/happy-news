package nl.fhict.happynews.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sander on 08/05/2017.
 * Attribution
 * Source: http://codesfor.in/android-swipe-to-delete-listview/
 */
public class SwipeDismissListViewTouchListener implements View.OnTouchListener {
    private int slop;
    private int minFlingVelocity;
    private int maxFlingVelocity;
    private long animationTime;

    // Fixed properties
    private ListView listView;
    private DismissCallbacks callbacks;
    private int viewWidth = 1; // 1 and not 0 to prevent dividing by zero

    // Transient properties
    private List<PendingDismissData> pendingDismisses = new ArrayList<PendingDismissData>();
    private int dismissAnimationRefCount = 0;
    private float downX;
    private float downY;
    private boolean swiping;
    private int swipingSlop;
    private VelocityTracker velocityTracker;
    private int fieldDownPosition;
    private View fieldDownView;
    private boolean paused;


    public interface DismissCallbacks {

        boolean canDismiss(int position);


        void onDismiss(ListView listView, int[] reverseSortedPositions);
    }


    /**
     * Constructor for SwipeDismissListViewTouchListener.
     *
     * @param listView  listView that needs to be swiped
     * @param callbacks the dismisscallbacks
     */
    public SwipeDismissListViewTouchListener(ListView listView, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
        slop = vc.getScaledTouchSlop();
        minFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        maxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        animationTime = listView.getContext().getResources().getInteger(
            android.R.integer.config_shortAnimTime);
        this.listView = listView;
        this.callbacks = callbacks;
    }


    /**
     * Set enabled true/false.
     *
     * @param enabled param bool
     */
    public void setEnabled(boolean enabled) {
        paused = !enabled;
    }

    /**
     * Something with a OnsScrollListener.
     *
     * @return something
     */
    public AbsListView.OnScrollListener makeScrollListener() {
        return new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        };
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (viewWidth < 2) {
            viewWidth = listView.getWidth();
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (paused) {
                    return false;
                }

                // TODO: ensure this is a finger, and set a flag

                // Find the child view that was touched (perform a hit test)
                Rect rect = new Rect();
                int childCount = listView.getChildCount();
                int[] listViewCoords = new int[2];
                listView.getLocationOnScreen(listViewCoords);
                int x = (int) motionEvent.getRawX() - listViewCoords[0];
                int y = (int) motionEvent.getRawY() - listViewCoords[1];
                View child;
                for (int i = 0; i < childCount; i++) {
                    child = listView.getChildAt(i);
                    child.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        fieldDownView = child;
                        break;
                    }
                }

                if (fieldDownView != null) {
                    downX = motionEvent.getRawX();
                    downY = motionEvent.getRawY();
                    fieldDownPosition = listView.getPositionForView(fieldDownView);
                    if (callbacks.canDismiss(fieldDownPosition)) {
                        velocityTracker = VelocityTracker.obtain();
                        velocityTracker.addMovement(motionEvent);
                    } else {
                        fieldDownView = null;
                    }
                }
                return false;
            }

            case MotionEvent.ACTION_CANCEL: {
                if (velocityTracker == null) {
                    break;
                }

                if (fieldDownView != null && swiping) {
                    // cancel
                    fieldDownView.animate()
                        .translationX(0)
                        .alpha(1)
                        .setDuration(animationTime)
                        .setListener(null);
                }
                velocityTracker.recycle();
                velocityTracker = null;
                downX = 0;
                downY = 0;
                fieldDownView = null;
                fieldDownPosition = ListView.INVALID_POSITION;
                swiping = false;
                break;
            }

            case MotionEvent.ACTION_UP: {
                if (velocityTracker == null) {
                    break;
                }

                float deltaX = motionEvent.getRawX() - downX;
                velocityTracker.addMovement(motionEvent);
                velocityTracker.computeCurrentVelocity(1000);
                float velocityX = velocityTracker.getXVelocity();
                float absVelocityX = Math.abs(velocityX);
                float absVelocityY = Math.abs(velocityTracker.getYVelocity());
                boolean dismiss = false;
                boolean dismissRight = false;
                if (Math.abs(deltaX) > viewWidth / 2 && swiping) {
                    dismiss = true;
                    dismissRight = deltaX > 0;
                } else if (minFlingVelocity <= absVelocityX && absVelocityX <= maxFlingVelocity
                    && absVelocityY < absVelocityX && swiping) {
                    // dismiss only if flinging in the same direction as dragging
                    dismiss = (velocityX < 0) == (deltaX < 0);
                    dismissRight = velocityTracker.getXVelocity() > 0;
                }
                if (dismiss && fieldDownPosition != ListView.INVALID_POSITION) {
                    // dismiss
                    final View downView = fieldDownView; // mDownView gets null'd before animation ends
                    final int downPosition = fieldDownPosition;
                    ++dismissAnimationRefCount;
                    fieldDownView.animate()
                        .translationX(dismissRight ? viewWidth : -viewWidth)
                        .alpha(0)
                        .setDuration(animationTime)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                performDismiss(downView, downPosition);
                            }
                        });
                } else {
                    // cancel
                    fieldDownView.animate()
                        .translationX(0)
                        .alpha(1)
                        .setDuration(animationTime)
                        .setListener(null);
                }
                velocityTracker.recycle();
                velocityTracker = null;
                downX = 0;
                downY = 0;
                fieldDownView = null;
                fieldDownPosition = ListView.INVALID_POSITION;
                swiping = false;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if (velocityTracker == null
                    || paused) {
                    break;
                }

                velocityTracker.addMovement(motionEvent);
                float deltaX = motionEvent.getRawX() - downX;
                float deltaY = motionEvent.getRawY() - downY;
                if (Math.abs(deltaX) > slop && Math.abs(deltaY) < Math.abs(deltaX) / 2) {
                    swiping = true;
                    swipingSlop = (deltaX > 0 ? slop : -slop);
                    listView.requestDisallowInterceptTouchEvent(true);

                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL
                        |
                        (motionEvent.getActionIndex()
                            << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    listView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                }

                if (swiping) {
                    fieldDownView.setTranslationX(deltaX - swipingSlop);
                    fieldDownView.setAlpha(Math.max(0f, Math.min(1f,
                        1f - 2f * Math.abs(deltaX) / viewWidth)));
                    return true;
                }
                break;
            }
            default:
                break;
        }
        return false;
    }

    class PendingDismissData implements Comparable<PendingDismissData> {
        public int position;
        public View view;

        public PendingDismissData(int position, View view) {
            this.position = position;
            this.view = view;
        }

        @Override
        public int compareTo(PendingDismissData other) {
            // Sort by descending position
            return other.position - position;
        }
    }

    private void performDismiss(final View dismissView, final int dismissPosition) {
        // Animate the dismissed list item to zero-height and fire the dismiss callback when
        // all dismissed list item animations have completed. This triggers layout on each animation
        // frame; in the future we may want to do something smarter and more performant.

        final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
        final int originalHeight = dismissView.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(animationTime);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                --dismissAnimationRefCount;
                if (dismissAnimationRefCount == 0) {
                    // No active animations, process all pending dismisses.
                    // Sort by descending position
                    Collections.sort(pendingDismisses);

                    int[] dismissPositions = new int[pendingDismisses.size()];
                    for (int i = pendingDismisses.size() - 1; i >= 0; i--) {
                        dismissPositions[i] = pendingDismisses.get(i).position;
                    }
                    callbacks.onDismiss(listView, dismissPositions);

                    // Reset mDownPosition to avoid MotionEvent.ACTION_UP trying to start a dismiss
                    // animation with a stale position
                    fieldDownPosition = ListView.INVALID_POSITION;

                    ViewGroup.LayoutParams lp;
                    for (PendingDismissData pendingDismiss : pendingDismisses) {
                        // Reset view presentation
                        pendingDismiss.view.setAlpha(1f);
                        pendingDismiss.view.setTranslationX(0);
                        lp = pendingDismiss.view.getLayoutParams();
                        lp.height = originalHeight;
                        pendingDismiss.view.setLayoutParams(lp);
                    }

                    // Send a cancel event
                    long time = SystemClock.uptimeMillis();
                    MotionEvent cancelEvent = MotionEvent.obtain(time, time,
                        MotionEvent.ACTION_CANCEL, 0, 0, 0);
                    listView.dispatchTouchEvent(cancelEvent);

                    pendingDismisses.clear();
                }
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                dismissView.setLayoutParams(lp);
            }
        });

        pendingDismisses.add(new PendingDismissData(dismissPosition, dismissView));
        animator.start();
    }
}

