package com.example.readitapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.SubscriptionDto;
import com.example.readitapp.utils.FragmentInteractionListener;
import com.example.readitapp.utils.PaymentConstants;
import com.example.readitapp.utils.PaymentsUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;

public class SubscriptionFragment extends Fragment implements ActivityResultCaller, FragmentInteractionListener {

    private View view;
    public static TextView statusValue;
    // A client for interacting with the Google Pay API.
    private PaymentsClient paymentsClient;
    public static Button googlePayButton;
    private static final long SHIPPING_COST_CENTS = 90 * PaymentsUtil.CENTS_IN_A_UNIT.longValue();
    // Arbitrarily-picked constant integer you define to track a request for payment data activity.
    public static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    public static boolean showButton = false;
    public static RadioGroup radioGroup;
    public static TextView amount;
    private int amountValue = 30;
    private int nbrMonths = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_subscription, container, false);

        initView();
        getSubscription();

        googlePayButton.setOnClickListener(view -> requestPayment());

        // Initialize a Google Pay API client for an environment suitable for testing.
        // It's recommended to create the PaymentsClient object inside of the onCreate method.
        paymentsClient = createPaymentsClient(getContext());
        possiblyShowPaymentControls();

        return view;
    }

    private void initView() {
        statusValue = view.findViewById(R.id.status_value);
        googlePayButton = view.findViewById(R.id.googlePayButton);
        radioGroup = view.findViewById(R.id.radioGroup);
        amount = view.findViewById(R.id.amount);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(view.findViewById(checkedId).getId()) {
                    case R.id.radioButton1:
                        amount.setText("30 lei");
                        amountValue = 30;
                        nbrMonths = 1;
                        break;
                    case R.id.radioButton2:
                        amount.setText("55 lei");
                        amountValue = 55;
                        nbrMonths = 2;
                        break;
                    case R.id.radioButton3:
                        amount.setText("80 lei");
                        amountValue = 80;
                        nbrMonths = 3;
                        break;
                }
                sendResult(Activity.RESULT_OK, nbrMonths);
            }
        });
    }

    private void getSubscription() {
        Call<SubscriptionDto> call = WebServerAPIBuilder.getInstance().getAvailability(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        call.enqueue(new Callback<SubscriptionDto>() {
            @Override
            public void onResponse(Call<SubscriptionDto> call, retrofit2.Response<SubscriptionDto> response) {
                if (response.isSuccessful()) {
                    statusValue.setText("Valid between " + response.body().getStartDate() + " - " + response.body().getEndDate());
                    showButton = false;
                } else if (response.code() == 403) {
                    statusValue.setText("You don't have a subscription");
                    showButton = true;
                }
            }

            @Override
            public void onFailure(Call<SubscriptionDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                statusValue.setText("Error occurred");
            }
        });
    }

    public static PaymentsClient createPaymentsClient(Context context) {
        Wallet.WalletOptions walletOptions =
                new Wallet.WalletOptions.Builder().setEnvironment(PaymentConstants.PAYMENTS_ENVIRONMENT).build();
        return Wallet.getPaymentsClient(context, walletOptions);
    }

    private void possiblyShowPaymentControls() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(getActivity(),
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful() && showButton) {
                            setPaymentControlsAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }

    public static void setPaymentControlsAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            amount.setVisibility(View.VISIBLE);
        } else {
            googlePayButton.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            amount.setVisibility(View.GONE);
        }
    }

    public void requestPayment() {

        // Disables the button to prevent multiple clicks.
        googlePayButton.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        long garmentPriceCents = Math.round(amountValue * PaymentsUtil.CENTS_IN_A_UNIT.longValue());
        long priceCents = garmentPriceCents + SHIPPING_COST_CENTS;

        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        if (!paymentDataRequestJson.isPresent()) {
            return;
        }

        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        if (request != null) {
            AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request),
                    requireActivity(), LOAD_PAYMENT_DATA_REQUEST_CODE);
        }

    }

    private void sendResult(int resultCode, int value) {
        if (getActivity() != null) {
            ((FragmentInteractionListener) getActivity()).onFragmentResult(resultCode, nbrMonths);
        }
    }

    @Override
    public void onFragmentResult(int resultCode, int data) {

    }
}