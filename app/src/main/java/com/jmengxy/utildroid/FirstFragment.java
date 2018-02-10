package com.jmengxy.utildroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmengxy.utildroid.models.UserEntity;
import com.jmengxy.utillib.listeners.OnBackPressedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import icepick.State;

/**
 * Created by jiemeng on 26/12/2017.
 */

public class FirstFragment extends Fragment implements OnBackPressedListener {

    public static final String TAG = "FirstFragment";
    public static final String ARG_USER_ENTITY = "arg_user_entity";

    private Unbinder unbinder;

    @State
    UserEntity userEntity;

    @BindView(R.id.text)
    TextView tvText;

    public static Fragment newInstance(UserEntity userEntity) {
        FirstFragment firstFragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_USER_ENTITY, userEntity);
        firstFragment.setArguments(bundle);
        return firstFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        if (userEntity == null) {
            userEntity = getArguments().getParcelable(ARG_USER_ENTITY);
            System.out.println("Fragment new >>>>>> " + Integer.toString(userEntity.hashCode()));
        } else {
            System.out.println("Fragment recreate >>>>>> " + Integer.toString(userEntity.hashCode()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_first, container, false);
        unbinder = ButterKnife.bind(this, rootView);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        setHasOptionsMenu(true);
        init();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void init() {
        tvText.setText(Integer.toString(userEntity.hashCode()));
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
