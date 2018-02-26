package com.jmengxy.utildroid.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.Root;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by jiemeng on 26/02/2018.
 */

public class EspressoUtils {

    public static void waitUIShort() {
        SystemClock.sleep(300);
    }

    public static void waitUIMedium() {
        SystemClock.sleep(1000);
    }

    public static void waitUILong() {
        SystemClock.sleep(3000);
    }

    public static void waitUI(int ms) {
        SystemClock.sleep(ms);
    }

    public static boolean checkView(ViewInteraction viewInteraction, final ViewAssertion viewAssert) {
        try {
            viewInteraction.check(viewAssert);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public static String getText(ViewInteraction viewInteraction) {
        final String[] stringHolder = {null};
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                stringHolder[0] = ((TextView) view).getText().toString();
            }
        });
        return stringHolder[0];
    }

    public static ViewInteraction getNavigateUpButton(@IdRes int toolbarId) {
        return onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(withId(toolbarId)),
                        isDisplayed()));
    }

    @NonNull
    public static Matcher<View> containsText(final String text) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item instanceof TextView) {
                    return ((TextView) item).getText().toString().contains(text);
                } else {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("contains text: ");
            }
        };
    }

    @NonNull
    public static Matcher<View> isRadioButtonChecked() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item instanceof RadioButton) {
                    return ((RadioButton) item).isChecked();
                } else {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("RadioButton checked: ");
            }
        };
    }

    @NonNull
    public static Matcher<View> withTextSelection(final int start, final int end) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                TextView textView = (TextView) item;
                return textView.getSelectionStart() == start && textView.getSelectionEnd() == end;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("text with selection: ");
            }
        };
    }

    @NonNull
    public static Matcher<View> textInputLayoutWithError() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return !TextUtils.isEmpty(((TextInputLayout) item).getError());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("textInputLayoutwithError with error: ");
            }
        };
    }

    @NonNull
    public static Matcher<View> withBackgroundColor(final int color) {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View view) {
                Drawable background = view.getBackground();
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                background.draw(canvas);
                return color == bitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with background color: ");
            }
        };
    }

    @NonNull
    public static Matcher<View> textViewWithColor(final int color) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return ((TextView) item).getTextColors().getDefaultColor() == color;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("TextView with color: ");
            }
        };
    }

    @NonNull
    public static Matcher<View> editTextWithSelection(final int begin, final int end) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                EditText editText = (EditText) item;
                return editText.getSelectionStart() == begin && editText.getSelectionEnd() == end;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("EditText with selection: ");
            }
        };
    }

    @NonNull
    public static Matcher<View> toolbarWithTitle(final CharSequence title) {
        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return title.equals(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
            }
        };
    }

    @NonNull
    public static Matcher<View> isSwitchChecked() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return ((Switch) item).isChecked();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is switch checked");
            }
        };
    }

    @NonNull
    public static Matcher<View> isSwitchPreferenceChecked() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return ((SwitchCompat) item.findViewById(com.jmengxy.utillib.R.id.switchWidget)).isChecked();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isSwitchPreference checked");
            }
        };
    }

    @NonNull
    public static Matcher<View> isActivated() {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is activated");
            }

            @Override
            public boolean matchesSafely(View view) {
                return view.isActivated();
            }
        };
    }

    @NonNull
    public static Matcher<View> isShowingPassword() {
        return new BoundedMatcher<View, EditText>(EditText.class) {
            @Override
            public boolean matchesSafely(EditText view) {
                return view.getTransformationMethod() instanceof PasswordTransformationMethod;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("");
            }
        };
    }

    @NonNull
    public static Matcher<View> hasRightDrawable(final int drawableRes) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has right drawable " + drawableRes);
            }

            @Override
            public boolean matchesSafely(TextView view) {
                final Drawable actualDrawable = view.getCompoundDrawables()[2];
                if (actualDrawable == null || !(actualDrawable instanceof BitmapDrawable))
                    return false;
                final Drawable expectedDrawable = ContextCompat.getDrawable(view.getContext(), drawableRes);
                return ((BitmapDrawable) actualDrawable).getBitmap().sameAs(((BitmapDrawable) expectedDrawable).getBitmap());
            }
        };
    }

    @NonNull
    public static ViewAction forceInput(final String text) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isEnabled();
            }

            @Override
            public String getDescription() {
                return "input";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((EditText) view).setText(text);
            }
        };
    }

    @NonNull
    public static TypeSafeMatcher<Root> isToast() {
        return new TypeSafeMatcher<Root>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("is a toast");
            }

            @Override
            public boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                    final View decorView = root.getDecorView();

                    IBinder windowToken = decorView.getWindowToken();
                    IBinder appToken = decorView.getApplicationWindowToken();
                    if (windowToken == appToken) {
                        // windowToken == appToken means this window isn't contained by any other windows.
                        // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @NonNull
    public static Matcher<View> isCheckedNav() {
        return new BoundedMatcher<View, BottomNavigationItemView>(BottomNavigationItemView.class) {
            boolean triedMatching;

            @Override
            public void describeTo(Description description) {
                if (triedMatching) {
                    description.appendText("with BottomNavigationItem check status true");
                }
            }

            @Override
            protected boolean matchesSafely(BottomNavigationItemView item) {
                triedMatching = true;
                return item.getItemData().isChecked();
            }
        };
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    public static int getRecyclerViewItemCount(ViewInteraction viewInteraction) {
        final int[] intHolder = {0};
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "get item count from RecyclerView: ";
            }

            @Override
            public void perform(UiController uiController, View view) {
                intHolder[0] = ((RecyclerView) view).getAdapter().getItemCount();
            }
        });
        return intHolder[0];
    }

    @NonNull
    public static Matcher<View> recyclerViewWithItemCount(final int itemCount) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                return recyclerView.getAdapter().getItemCount() == itemCount;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("recyclerView with ItemCount: ");
            }
        };
    }

    public static ViewAction clickClickableSpan(final CharSequence textToClick) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return Matchers.instanceOf(TextView.class);
            }

            @Override
            public String getDescription() {
                return "clicking on a ClickableSpan";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView textView = (TextView) view;
                SpannableString spannableString = (SpannableString) textView.getText();

                if (spannableString.length() == 0) {
                    // TextView is empty, nothing to do
                    throw new NoMatchingViewException.Builder()
                            .includeViewHierarchy(true)
                            .withRootView(textView)
                            .build();
                }

                // Get the links inside the TextView and check if we find textToClick
                ClickableSpan[] spans = spannableString.getSpans(0, spannableString.length(), ClickableSpan.class);
                if (spans.length > 0) {
                    ClickableSpan spanCandidate;
                    for (ClickableSpan span : spans) {
                        spanCandidate = span;
                        int start = spannableString.getSpanStart(spanCandidate);
                        int end = spannableString.getSpanEnd(spanCandidate);
                        CharSequence sequence = spannableString.subSequence(start, end);
                        if (textToClick.toString().equals(sequence.toString())) {
                            span.onClick(textView);
                            return;
                        }
                    }
                }

                // textToClick not found in TextView
                throw new NoMatchingViewException.Builder()
                        .includeViewHierarchy(true)
                        .withRootView(textView)
                        .build();
            }
        };
    }

    public static class RecyclerViewMatcher {
        private final int recyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;

                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = recyclerViewId + " (resource name not found)";
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {

                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView =
                                (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        } else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }

                }
            };
        }
    }

    public static DrawableMatcher imageWithDrawableId(@DrawableRes int expectedId) {
        return new DrawableMatcher(expectedId);
    }

    public static ViewAction setNumberPickerValue(final int num) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                NumberPicker np = (NumberPicker) view;
                np.setValue(num);

            }

            @Override
            public String getDescription() {
                return "Set the passed number into the NumberPicker";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }
        };
    }

    public static class ClickDrawableAction implements ViewAction
    {
        public static final int Left = 0;
        public static final int Top = 1;
        public static final int Right = 2;
        public static final int Bottom = 3;

        @Location
        private final int drawableLocation;

        public ClickDrawableAction(@Location int drawableLocation)
        {
            this.drawableLocation = drawableLocation;
        }

        @Override
        public Matcher<View> getConstraints()
        {
            return allOf(isAssignableFrom(TextView.class), new BoundedMatcher<View, TextView>(TextView.class)
            {
                @Override
                protected boolean matchesSafely(final TextView tv)
                {
                    return tv.getCompoundDrawables()[drawableLocation] != null;
                }

                @Override
                public void describeTo(Description description)
                {
                    description.appendText("has drawable");
                }
            });
        }

        @Override
        public String getDescription()
        {
            return "click drawable ";
        }

        @Override
        public void perform(final UiController uiController, final View view)
        {
            TextView tv = (TextView)view;
            if (tv != null)
            {
                Rect drawableBounds = tv.getCompoundDrawables()[drawableLocation].getBounds();

                final Point[] clickPoint = new Point[4];
                clickPoint[Left] = new Point(tv.getLeft() + (drawableBounds.width() / 2), (int)(tv.getPivotY() + (drawableBounds.height() / 2)));
                clickPoint[Top] = new Point((int)(tv.getPivotX() + (drawableBounds.width() / 2)), tv.getTop() + (drawableBounds.height() / 2));
                clickPoint[Right] = new Point(tv.getRight() + (drawableBounds.width() / 2), (int)(tv.getPivotY() + (drawableBounds.height() / 2)));
                clickPoint[Bottom] = new Point((int)(tv.getPivotX() + (drawableBounds.width() / 2)), tv.getBottom() + (drawableBounds.height() / 2));

                if(tv.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, clickPoint[drawableLocation].x, clickPoint[drawableLocation].y, 0)))
                    tv.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, clickPoint[drawableLocation].x, clickPoint[drawableLocation].y, 0));
            }
        }

        @IntDef({ Left, Top, Right, Bottom })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Location{}
    }

    public static class DrawableMatcher extends TypeSafeMatcher {
        private final int expectedId;
        private String resourceName;

        public DrawableMatcher(int resourceId){
            super(View.class);
            this.expectedId = resourceId;
        }

        @Override
        protected boolean matchesSafely(Object target) {
            if (!(target instanceof ImageView)){
                return false;
            }
            ImageView imageView = (ImageView) target;
            if (expectedId < 0){
                return imageView.getDrawable() == null;
            }
            Resources resources = imageView.getContext().getResources();
            Drawable expectedDrawable = resources.getDrawable(expectedId);
            resourceName = resources.getResourceEntryName(expectedId);

            if (expectedDrawable == null) {
                return false;
            }

            Bitmap bitmap = getBitmap(imageView.getDrawable());
            Bitmap otherBitmap = getBitmap(expectedDrawable);
            return bitmap.sameAs(otherBitmap);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with drawable from resource id: ");
            description.appendValue(expectedId);
            if (resourceName != null) {
                description.appendText("[");
                description.appendText(resourceName);
                description.appendText("]");
            }
        }

        private Bitmap getBitmap(Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof VectorDrawable) {
                return getBitmapFromVectorDrawable(drawable);
            }
            else {
                throw new IllegalArgumentException("unsupported drawable type " + drawable.getClass());
            }
        }

        private static Bitmap getBitmapFromVectorDrawable(Drawable drawable) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = (DrawableCompat.wrap(drawable)).mutate();
            }

            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        }
    }
}
