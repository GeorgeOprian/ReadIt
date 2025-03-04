package com.example.readitapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.fragments.AdministrationFragment;
import com.example.readitapp.fragments.BooksFragment;
import com.example.readitapp.fragments.HomeFragment;
import com.example.readitapp.fragments.MyBooksTabbedFragment;
import com.example.readitapp.fragments.ProfileFragment;
import com.example.readitapp.fragments.SubscriptionFragment;
import com.example.readitapp.fragments.WishlistFragment;
import com.example.readitapp.model.webserver.SubscriptionDto;
import com.example.readitapp.utils.FirebaseConstants;
import com.example.readitapp.utils.FragmentInteractionListener;
import com.example.readitapp.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.readitapp.fragments.SubscriptionFragment.LOAD_PAYMENT_DATA_REQUEST_CODE;
import static com.example.readitapp.fragments.SubscriptionFragment.googlePayButton;
import static com.example.readitapp.fragments.SubscriptionFragment.statusValue;

public class MainActivity extends AppCompatActivity implements FragmentInteractionListener {

    private DrawerLayout drawerLayout;
    private NavigationView leftNavigationView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private GoogleSignInClient mGoogleSignInClient;
    public static boolean skipFragment = false;
    private int nbrMonths = 1;

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
                selectedFragment = new MyBooksTabbedFragment();
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (skipFragment) {
            fragmentManager.popBackStack("avoid", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            skipFragment = false; // Reset the flag
            return;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        addSubscription();
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Google Pay payment button.
                googlePayButton.setClickable(true);
        }
    }

    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String token = tokenizationData.getString("token");
            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    private void handleError(int statusCode) {
        Log.e("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    private void addSubscription() {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(nbrMonths);
        subscriptionDto.setStartDate(startDate.toString());
        subscriptionDto.setEndDate(endDate.toString());
        Call<SubscriptionDto> call = WebServerAPIBuilder.getInstance().createSubscription(subscriptionDto);

        call.enqueue(new Callback<SubscriptionDto>() {
            @Override
            public void onResponse(Call<SubscriptionDto> call, retrofit2.Response<SubscriptionDto> response) {
                if (response.isSuccessful()) {
                    statusValue.setText("Valid between " + response.body().getStartDate() + " - " + response.body().getEndDate());
                    SubscriptionFragment.showButton = false;
                    SubscriptionFragment.setPaymentControlsAvailable(SubscriptionFragment.showButton);
                }
            }

            @Override
            public void onFailure(Call<SubscriptionDto> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onFragmentResult(int resultCode, int data) {
        if (resultCode == Activity.RESULT_OK) {
            nbrMonths = data;
        }
    }
}