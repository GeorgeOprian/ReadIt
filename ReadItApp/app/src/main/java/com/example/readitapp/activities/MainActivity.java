package com.example.readitapp.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.fragments.AdministrationFragment;
import com.example.readitapp.fragments.BooksFragment;
import com.example.readitapp.fragments.HomeFragment;
import com.example.readitapp.fragments.MyBooksFragment;
import com.example.readitapp.fragments.ProfileFragment;
import com.example.readitapp.fragments.SubscriptionFragment;
import com.example.readitapp.fragments.WishlistFragment;
import com.example.readitapp.model.webserver.JudetDto;
import com.example.readitapp.model.webserver.user.output.UserDto;
import com.example.readitapp.utils.FirebaseConstants;
import com.example.readitapp.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView leftNavigationView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureGoogleSignIn();
        initMenu();
        Utils.getUserDetails();
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initMenu() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        leftNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        updateNavHeader();
        setLeftNavigationView();
        setBottomNavigationView();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    private void setLeftNavigationView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        hideAdmin();
        leftNavigationView.setNavigationItemSelectedListener(item -> switchLeftNav(item));
    }

    private void hideAdmin() {
        Menu nav_Menu = leftNavigationView.getMenu();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        nav_Menu.findItem(R.id.nav_administration).setVisible(currentUser.equals(Utils.USER_ADMIN));
    }

    private boolean switchLeftNav(MenuItem item) {
        Fragment selectedFragment = new HomeFragment();
        switch (item.getItemId()) {
            case R.id.nav_profile:
                selectedFragment = new ProfileFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_administration:
                selectedFragment = new AdministrationFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_signOut:
                mGoogleSignInClient.signOut();
                FirebaseConstants.mAuth.signOut();
                Intent signIn = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(signIn);
                finish();
                break;
            case R.id.nav_subscription:
                selectedFragment = new SubscriptionFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_my_books:
                selectedFragment = new MyBooksFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_wishlist:
                selectedFragment = new WishlistFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment).addToBackStack("tag")
                .commit();
        return true;
    }

    private void setBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = new HomeFragment();
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_books:
                    selectedFragment = new BooksFragment();
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment).addToBackStack("tag")
                    .commit();
            return true;
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    private void updateNavHeader() {
        View headerView = leftNavigationView.getHeaderView(0);
        CircleImageView circleImageView = headerView.findViewById(R.id.profile_image);
        TextView name = headerView.findViewById(R.id.name);

        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Glide.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(circleImageView);
    }
}