package ba.sum.fpmoz.pma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ba.sum.fpmoz.pma.fragments.CreateTaskFragment;
import ba.sum.fpmoz.pma.fragments.HomeFragment;
import ba.sum.fpmoz.pma.fragments.LogoutFragment;
import ba.sum.fpmoz.pma.fragments.ProfileFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Open the HomeFragment by default
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        if (getIntent().getBooleanExtra("openProfileFragment", false)) {
            ProfileFragment profileFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.home) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        return true;
                    } else if (id == R.id.create_task) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateTaskFragment()).commit();
                        return true;
                    } else if (id == R.id.profile) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                        return true;
                    } else if (id == R.id.logout) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LogoutFragment()).commit();
                        return true;
                    }
                    return false;
                }
            };

    public void openHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
    }




}