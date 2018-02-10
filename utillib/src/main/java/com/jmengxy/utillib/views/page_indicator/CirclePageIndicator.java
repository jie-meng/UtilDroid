package com.jmengxy.utillib.views.page_indicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.jmengxy.utillib.R;
import com.jmengxy.utillib.utils.DisplayUtils;

/**
 * Created by jiemeng on 10/02/2018.
 */

public class CirclePageIndicator extends LinearLayout implements PageIndicator {
    private int animatorResId = R.animator.scale_with_alpha;
    private final static int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager viewPager;
    private int indicatorMargin = -1;
    private int indicatorWidth = -1;
    private int indicatorHeight = -1;
    private int animatorReverseResId = 0;
    private int indicatorBackgroundResId = R.drawable.white_radius;
    private int indicatorUnselectedBackgroundResId = R.drawable.white_radius;
    private Animator animatorOut;
    private Animator animatorIn;
    private Animator immediateAnimatorOut;
    private Animator immediateAnimatorIn;

    private int lastPosition = -1;

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        indicatorWidth =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
        indicatorHeight =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
        indicatorMargin =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);

        animatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator,
                R.animator.scale_with_alpha);
        animatorReverseResId =
                typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, 0);
        indicatorBackgroundResId =
                typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable,
                        R.drawable.white_radius);
        indicatorUnselectedBackgroundResId =
                typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected,
                        indicatorBackgroundResId);

        typedArray.recycle();
    }

    private void checkIndicatorConfig(Context context) {
        indicatorWidth = (indicatorWidth < 0) ?
                (int) DisplayUtils.convertDpToPixel(getContext(), DEFAULT_INDICATOR_WIDTH) : indicatorWidth;
        indicatorHeight = (indicatorHeight < 0) ?
                (int) DisplayUtils.convertDpToPixel(getContext(), DEFAULT_INDICATOR_WIDTH) : indicatorHeight;
        indicatorMargin = (indicatorMargin < 0) ?
                (int) DisplayUtils.convertDpToPixel(getContext(), DEFAULT_INDICATOR_WIDTH) : indicatorMargin;

        animatorResId = (animatorResId == 0) ? R.animator.scale_with_alpha : animatorResId;

        animatorOut = createAnimatorOut(context);
        immediateAnimatorOut = createAnimatorOut(context);
        immediateAnimatorOut.setDuration(0);

        animatorIn = createAnimatorIn(context);
        immediateAnimatorIn = createAnimatorIn(context);
        immediateAnimatorIn.setDuration(0);

        indicatorBackgroundResId = (indicatorBackgroundResId == 0) ?
                R.drawable.white_radius : indicatorBackgroundResId;

        indicatorUnselectedBackgroundResId = (indicatorUnselectedBackgroundResId == 0) ?
                indicatorBackgroundResId : indicatorUnselectedBackgroundResId;

        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, animatorResId);
    }

    private Animator createAnimatorIn(Context context) {
        Animator animatorIn;
        if (animatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(context, animatorResId);
            animatorIn.setInterpolator(new ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(context, animatorReverseResId);
        }
        return animatorIn;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        if (this.viewPager != null && this.viewPager.getAdapter() != null) {
            lastPosition = -1;
            createIndicators();
            this.viewPager.removeOnPageChangeListener(this);
            this.viewPager.addOnPageChangeListener(this);
            onPageSelected(this.viewPager.getCurrentItem());
        }
    }

    private void createIndicators() {
        removeAllViews();
        int count = viewPager.getAdapter().getCount();
        if (count <= 0) {
            return;
        }
        int currentItem = viewPager.getCurrentItem();
        int orientation = getOrientation();

        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(orientation, indicatorBackgroundResId, immediateAnimatorOut);
            } else {
                addIndicator(orientation, indicatorUnselectedBackgroundResId,
                        immediateAnimatorIn);
            }
        }
    }

    private void addIndicator(int orientation, @DrawableRes int backgroundDrawableId,
                              Animator animator) {
        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        View indicator = new View(getContext());
        indicator.setBackgroundResource(backgroundDrawableId);
        addView(indicator, indicatorWidth, indicatorHeight);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) indicator.getLayoutParams();

        if (orientation == HORIZONTAL) {
            lp.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
        } else {
            lp.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
        }

        indicator.setLayoutParams(lp);

        animator.setTarget(indicator);
        animator.start();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        if (viewPager.getAdapter() == null || viewPager.getAdapter().getCount() <= 0) {
            return;
        }

        if (animatorIn.isRunning()) {
            animatorIn.end();
            animatorIn.cancel();
        }

        if (animatorOut.isRunning()) {
            animatorOut.end();
            animatorOut.cancel();
        }

        View currentIndicator;
        if (lastPosition >= 0 && (currentIndicator = getChildAt(lastPosition)) != null) {
            currentIndicator.setBackgroundResource(indicatorUnselectedBackgroundResId);
            animatorIn.setTarget(currentIndicator);
            animatorIn.start();
        }

        View selectedIndicator = getChildAt(position);
        if (selectedIndicator != null) {
            selectedIndicator.setBackgroundResource(indicatorBackgroundResId);
            animatorOut.setTarget(selectedIndicator);
            animatorOut.start();
        }
        lastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private static final class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }
}
