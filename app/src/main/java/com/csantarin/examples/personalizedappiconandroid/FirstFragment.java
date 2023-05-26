package com.csantarin.examples.personalizedappiconandroid;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.csantarin.examples.personalizedappiconandroid.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initializeView() {
        binding.buttonFirstIcon.setOnClickListener(handleClick(this::changeIconToFirst));
        binding.buttonSecondIcon.setOnClickListener(handleClick(this::changeIconToSecond));

        Toast
                .makeText(getActivity(), "First Fragment initialized!", Toast.LENGTH_SHORT)
                .show();
    }

    private View.OnClickListener handleClick(Runnable runnable) {
        return view -> {
            runnable.run();
        };
    }

    private String classToKill = "";

    @Override
    public void onPause() {
        completeIconChange();
        super.onPause();
    }

    private void completeIconChange() {
        FragmentActivity activity = getActivity();

        if (activity == null) {
            return;
        }

        String applicationId = BuildConfig.APPLICATION_ID;
        PackageManager packageManager = activity.getPackageManager();

        packageManager.setComponentEnabledSetting(new ComponentName(activity, applicationId + classToKill),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );

        classToKill = "";
    }

    private void changeIconToFirst() {
        FragmentActivity activity = getActivity();

        if (activity == null) {
            return;
        }

        String applicationId = BuildConfig.APPLICATION_ID;
        PackageManager packageManager = activity.getPackageManager();

        // NOTE: Disabling the previous alias immediately here will put the app to background
        //       and then kill it. We do not want that.
        //
        //       Instead, wait until the app reaches .onPause() before disabling the previous alias.

        // packageManager.setComponentEnabledSetting(
        //         new ComponentName(activity, applicationId + ".MainActivityAlias2"),
        //         PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        //         PackageManager.DONT_KILL_APP
        // );

        classToKill = ".MainActivityAlias2";

        packageManager.setComponentEnabledSetting(
                new ComponentName(activity, applicationId + ".MainActivityAlias1"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );

        Toast
                .makeText(activity, "Enabling Green Icon. Hang on...", Toast.LENGTH_SHORT)
                .show();
    }

    private void changeIconToSecond() {
        FragmentActivity activity = getActivity();

        if (activity == null) {
            return;
        }

        String applicationId = BuildConfig.APPLICATION_ID;
        PackageManager packageManager = activity.getPackageManager();

        // NOTE: Disabling the previous alias immediately here will put the app to background
        //       and then kill it. We do not want that.
        //
        //       Instead, wait until the app reaches .onPause() before disabling the previous alias.

        // packageManager.setComponentEnabledSetting(
        //         new ComponentName(activity, applicationId + ".MainActivityAlias1"),
        //         PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        //         PackageManager.DONT_KILL_APP
        // );

        classToKill = ".MainActivityAlias1";

        packageManager.setComponentEnabledSetting(
                new ComponentName(activity, applicationId + ".MainActivityAlias2"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );

        Toast
                .makeText(activity, "Enabling Red Icon. Hang on...", Toast.LENGTH_SHORT)
                .show();
    }
}