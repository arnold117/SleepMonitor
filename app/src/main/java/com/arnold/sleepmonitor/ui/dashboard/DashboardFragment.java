package com.arnold.sleepmonitor.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.arnold.sleepmonitor.databinding.FragmentDashboardBinding;
import com.arnold.sleepmonitor.utils.MSensorManager;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private MSensorManager dashboardSensorManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardSensorManager = new MSensorManager(getContext());

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final Button startButton = binding.startButtonDashboard;
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dashboardSensorManager.startListener();
                dashboardViewModel.setText("Listener Started");
                dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            }
        });

        final Button endButton = binding.endButtonDashboard;
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dashboardSensorManager.stopListener();
                dashboardViewModel.setText("Listener Stopped");
                dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}