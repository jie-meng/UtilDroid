package com.jmengxy.utillib.utils;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jmengxy.utillib.R;
import com.jmengxy.utillib.functors.Action0;
import com.jmengxy.utillib.functors.Action1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiemeng on 10/08/2017.
 */

public class UiUtils {

    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int frameId, @Nullable String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.commitAllowingStateLoss();
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId, @Nullable String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commitAllowingStateLoss();
    }

    public static void addFragmentAndAddToBackStack(@NonNull FragmentManager fragmentManager,
                                                    @NonNull Fragment fragment, int frameId, @Nullable String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    public static void replaceFragmentAndAddToBackStack(@NonNull FragmentManager fragmentManager,
                                                        @NonNull Fragment fragment, int frameId, @Nullable String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    public static void removeFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean darkStatusBarTextColor = isColorDarkOrLight(color);
                final int lFlags = activity.getWindow().getDecorView().getSystemUiVisibility();
                activity.getWindow().getDecorView().setSystemUiVisibility(darkStatusBarTextColor ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
            }
        }
    }

    public static boolean isColorDarkOrLight(@ColorInt int color) {
        int mono = (int) (0.3 * (double) ((color & 0xff0000) >> 16) + 0.59 * (double) ((color & 0x00ff00) >> 8) + 0.11 * (double) (color & 0x0000ff));
        return mono <= 192;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager input = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context, boolean force) {
        InputMethodManager input = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        input.toggleSoftInput(force ? InputMethodManager.SHOW_FORCED : InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void setViewEnable(View view, boolean enable) {
        if (view == null) {
            return;
        }

        float alpha = enable ? 1f : 0.4f;
        view.setAlpha(alpha);
        view.setClickable(enable);
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView bottomNavigationView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Ignore
        }
    }

    public static void setViewBackgroundColor(@NonNull View view, @ColorInt int color) {
        Drawable drawable = view.getBackground();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT > 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static void showOrHideProgressView(Activity activity, boolean showOrHide, @LayoutRes int viewProgressBar, @IdRes int viewProgressBarId) {
        showOrHideProgressView(activity, activity.findViewById(android.R.id.content), showOrHide, viewProgressBar, viewProgressBarId);
    }

    public static void showOrHideProgressView(Context context, ViewGroup viewGroup, boolean showOrHide, @LayoutRes int viewProgressBar, @IdRes int viewProgressBarId) {
        View view = viewGroup.findViewById(viewProgressBarId);
        if (showOrHide) {
            if (view == null) {
                View progressView = LayoutInflater.from(context).inflate(viewProgressBar, viewGroup, false);
                viewGroup.addView(progressView);
            }
        } else {
            if (view != null) {
                viewGroup.removeView(view);
            }
        }
    }

    public static boolean isShowingProgress(Activity activity, @IdRes int viewProgressBarId) {
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View view = viewGroup.findViewById(viewProgressBarId);
        return view != null;
    }

    public static void editTextSetContentMemorizeSelection(EditText editText, CharSequence charSequence) {
        if (editText == null) {
            return;
        }

        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        editText.setText(charSequence.toString());

        if (selectionStart > charSequence.toString().length()) {
            selectionStart = charSequence.toString().length();
        }
        if (selectionStart < 0) {
            selectionStart = 0;
        }

        if (selectionEnd > charSequence.toString().length()) {
            selectionEnd = charSequence.toString().length();
        }
        if (selectionEnd < 0) {
            selectionEnd = 0;
        }

        editText.setSelection(selectionStart, selectionEnd);
    }

    public static void showTextInputLayoutError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(error);
    }

    public static void hideTextInputLayoutError(TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
    }

    public static void dimBackground(Activity activity, final float alpha) {
        final Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = alpha;
        window.setAttributes(params);
    }

    public static void dimBackgroundWithAnimation(Activity activity, final float from, final float to) {
        final Window window = activity.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(animation -> {
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = (Float) animation.getAnimatedValue();
            window.setAttributes(params);
        });

        valueAnimator.start();
    }

    public static void setProgressBarColor(ProgressBar progressBar, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(color));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    public static void setOverFlowMenuIconColor(Toolbar toolbar, @ColorInt int color) {
        Drawable overflowIcon = toolbar.getOverflowIcon();
        if (overflowIcon != null) {
            overflowIcon = DrawableCompat.wrap(overflowIcon);
            DrawableCompat.setTint(overflowIcon.mutate(), color);
            toolbar.setOverflowIcon(overflowIcon);
        }
    }

    public static void doOnEditFocusChange(EditText editText, Action0 hasFocus, Action0 lostFocus) {
        editText.setOnFocusChangeListener((v, focus) -> {
            if (editText == null) {
                return;
            }

            if (focus) {
                if (hasFocus != null) {
                    hasFocus.apply();
                }
            } else {
                if (lostFocus != null) {
                    lostFocus.apply();
                }
            }
        });
    }

    public static void doOnEditTextChange(EditText editText, Action1<String> action) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText == null) {
                    return;
                }

                action.apply(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void addEditTextAddFilters(EditText editText, InputFilter... inputFilters) {
        List<InputFilter> inputFilterList = new ArrayList<>();
        for (InputFilter filter : editText.getFilters()) {
            inputFilterList.add(filter);
        }
        for (InputFilter filter : inputFilters) {
            inputFilterList.add(filter);
        }
        editText.setFilters(inputFilterList.toArray(new InputFilter[inputFilterList.size()]));
    }

    public static void setTextViewDrawableRightOnClickListener(TextView textView, Action1<TextView> action) {
        textView.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (event.getRawX() >= (textView.getRight() - textView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - textView.getCompoundPaddingRight())) {
                    action.apply(textView);
                    return true;
                }
            }

            return false;
        });
    }

    public static int getContentHeight(Activity activity) {
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        TypedArray styledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        Rect rectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;

        return screenHeight - statusBarHeight - actionBarSize;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void drawUnderStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void drawBelowStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.cardview_dark_background));
    }
}
