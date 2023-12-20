package com.example.mytyyjdjdjd.ui.testing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytyyjdjdjd.databinding.FragmentTestingBinding;
import com.example.mytyyjdjdjd.ui.testing.TestingViewModel;

public class TestingFragment extends Fragment {

    private FragmentTestingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TestingViewModel testingViewModel =
                new ViewModelProvider(this).get(TestingViewModel.class);

        binding = FragmentTestingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        testingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}