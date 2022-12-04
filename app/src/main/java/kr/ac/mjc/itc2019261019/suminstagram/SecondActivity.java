package kr.ac.mjc.itc2019261019.suminstagram;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class SecondActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ViewPager viewpager = findViewById(R.id.viewpager);
        TabLayout tablayout = findViewById(R.id.tablayout);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewpager);

        tablayout.getTabAt(0)
                .setIcon(getResources().getDrawable(R.drawable.home));
        tablayout.getTabAt(1)
                .setIcon(getResources().getDrawable(R.drawable.search));
        tablayout.getTabAt(2)
                .setIcon(getResources().getDrawable(R.drawable.add_circle));
        tablayout.getTabAt(3)
                .setIcon(getResources().getDrawable(R.drawable.favorite));
        tablayout.getTabAt(4)
                .setIcon(getResources().getDrawable(R.drawable.account_circle));

    }
}
