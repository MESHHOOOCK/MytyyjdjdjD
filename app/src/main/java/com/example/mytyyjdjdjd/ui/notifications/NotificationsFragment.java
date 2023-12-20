package com.example.mytyyjdjdjd.ui.notifications;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mytyyjdjdjd.AppData;
import com.example.mytyyjdjdjd.MainActivity;
import com.example.mytyyjdjdjd.R;
import com.example.mytyyjdjdjd.ui.home.HomeFragment;

public class NotificationsFragment extends Fragment {
    private TextView bonusText;
    private Button claimButton;
    private CountDownTimer bonusTimer;

    private static final long BONUS_DELAY = 120000; // 2 минуты
    private static final long COUNTDOWN_INTERVAL = 1000; // интервал обновления таймера (1 секунда)
    private static final long BONUS_AMOUNT = 2500; // сумма бонуса

    private AppData appData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        appData = AppData.getInstance();

        bonusText = root.findViewById(R.id.bonus_text);
        claimButton = root.findViewById(R.id.claim_button);
        claimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                claimBonus();
            }
        });

        // Планируем выполнение получения бонуса по времени
        startBonusTimer();

        return root;
    }

    private void startBonusTimer() {
        bonusTimer = new CountDownTimer(BONUS_DELAY, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsUntilNextBonus = (int) (millisUntilFinished / 1000);
                bonusText.setText("Next bonus in " + secondsUntilNextBonus + " seconds");
            }

            @Override
            public void onFinish() {
                bonusText.setText("Claim your bonus!");
                claimButton.setEnabled(true);
            }
        }.start();
    }

    private void claimBonus() {
        // Добавляем бонус к балансу
        AppData appData = AppData.getInstance();
        appData.setBalance(appData.getBalance() + 2500f);

        // Обновляем UI в HomeFragment
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateHomeFragmentUI();
        }

        // Обновляем UI в HomeFragment
        HomeFragment homeFragment = (HomeFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment");
        if (homeFragment != null) {
            homeFragment.updateUI();
        }

        // Отключаем кнопку до следующего бонуса
        claimButton.setEnabled(false);

        // Планируем выполнение получения следующего бонуса по времени
        startBonusTimer();
    }
}