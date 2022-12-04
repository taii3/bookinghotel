package com.example.bookinghotel.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookinghotel.AdminFragment1;
import com.example.bookinghotel.AdminFragment2;
import com.example.bookinghotel.AdminFragment3;
import com.example.bookinghotel.OnboardingFragment1;
import com.example.bookinghotel.OnboardingFragment2;
import com.example.bookinghotel.OnboardingFragment3;

public class AdminAdapter extends FragmentStateAdapter {
    public AdminAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminFragment1();
            case 1:
                return new AdminFragment2();
            case 2:
                return new AdminFragment3();
            default:
                return new AdminFragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
