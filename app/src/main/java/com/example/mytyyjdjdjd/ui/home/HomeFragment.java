package com.example.mytyyjdjdjd.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mytyyjdjdjd.AppData;
import com.example.mytyyjdjdjd.R;

import java.text.DecimalFormat;


public class HomeFragment extends Fragment {
    private TextView balanceTextView;
    private TextView incomeTextView;
    private Button earnButton;

    private AppData appData;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_KEY = "clicker_prefs";
    private static final String BALANCE_KEY = "balance";
    private static final String INCOME_KEY = "income";

    private Handler handler;
    private Runnable incomeRunnable;

    private DecimalFormat decimalFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        appData = AppData.getInstance();
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_KEY, AppCompatActivity.MODE_PRIVATE);
        appData.setBalance(sharedPreferences.getFloat(BALANCE_KEY, 0f));
        appData.setIncome(sharedPreferences.getFloat(INCOME_KEY, 1f));

        balanceTextView = rootView.findViewById(R.id.balanceTextView);
        incomeTextView = rootView.findViewById(R.id.incomeTextView);
        earnButton = rootView.findViewById(R.id.earnButton);

        decimalFormat = new DecimalFormat("#,##0.00");

        updateBalanceTextView();

        earnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appData.setBalance(appData.getBalance() + appData.getIncome());
                updateBalanceTextView();
                saveData();
            }
        });

        handler = new Handler();
        incomeRunnable = new Runnable() {
            @Override
            public void run() {
                appData.setBalance(appData.getBalance() + appData.getIncome()); // Изменил на эту строчку
                updateBalanceTextView();
                saveData();
                handler.postDelayed(this, 1000);
            }
        };

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        startIncome();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopIncome();
        saveData();
    }

    private void startIncome() {
        handler.postDelayed(incomeRunnable, 1000);
    }

    private void stopIncome() {
        handler.removeCallbacks(incomeRunnable);
    }

    private void updateBalanceTextView() {
        balanceTextView.setText(getString(R.string.balance_label, decimalFormat.format(appData.getBalance())));
        incomeTextView.setText(getString(R.string.income_label, decimalFormat.format(appData.getIncome()), decimalFormat.format(appData.getIncomePerSecond())));
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(BALANCE_KEY, appData.getBalance());
        editor.putFloat(INCOME_KEY, appData.getIncome());
        editor.apply();
    }

    public void updateUI() {
        AppData appData = AppData.getInstance();
        balanceTextView.setText("$" + appData.getBalance());
    }
}
