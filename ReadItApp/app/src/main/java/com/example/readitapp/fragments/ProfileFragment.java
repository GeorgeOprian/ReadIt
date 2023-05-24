package com.example.readitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.JudetDto;
import com.example.readitapp.model.webserver.LocalitateDto;
import com.example.readitapp.model.webserver.UserAddressInputDto;
import com.example.readitapp.model.webserver.user.output.UserDto;
import com.example.readitapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment {

    private View view;
    private TextInputEditText nume;
    private TextInputEditText email;
    private AutoCompleteTextView judete;
    private List<JudetDto> judeteList = new ArrayList<>();
    private AutoCompleteTextView localitate;
    private TextInputLayout localitateLayout;
    private List<LocalitateDto> localitatiList = new ArrayList<>();
    private TextInputEditText strada;
    private TextInputEditText nr;
    private TextInputEditText bloc;
    private TextInputEditText scara;
    private TextInputEditText ap;
    private Button save;
    private ArrayAdapter<LocalitateDto> adapterLocalitati;
    private LocalitateDto selectedValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView();
        fillControls();

        save.setOnClickListener(view -> addAddress());

        return view;
    }

    private void initView() {
        nume = view.findViewById(R.id.textInputEditTextNume);
        email = view.findViewById(R.id.textInputEditTextEmail);
        judete = view.findViewById(R.id.judete_auto);
        localitate = view.findViewById(R.id.localitate_auto);
        strada = view.findViewById(R.id.textInputEditTextAddress);
        nr = view.findViewById(R.id.textInputEditTextNr);
        bloc = view.findViewById(R.id.textInputEditTextBloc);
        scara = view.findViewById(R.id.textInputEditTextScara);
        ap = view.findViewById(R.id.textInputEditTextAp);
        save = view.findViewById(R.id.save_button);
        localitateLayout = view.findViewById(R.id.localitate);
    }

    private void fillControls() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        nume.setText(user.getDisplayName());
        email.setText(user.getEmail());
        fillJudete();
        fillLocalitati();
        ((AutoCompleteTextView)localitateLayout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedValue = adapterLocalitati.getItem(position);
            }
        });
    }

    private void getJudete() {
        Call<List<JudetDto>> call = WebServerAPIBuilder.getInstance().getJudete();

        call.enqueue(new Callback<List<JudetDto>>() {
            @Override
            public void onResponse(Call<List<JudetDto>> call, retrofit2.Response<List<JudetDto>> response) {
                if (response.isSuccessful()) {
                    List<JudetDto> lista = response.body();
                    for (JudetDto judetDto : lista) {
                        judeteList.add(judetDto);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JudetDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillJudete() {
        getJudete();
        ArrayAdapter<JudetDto> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, judeteList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        judete.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getLocalitati() {
        Call<List<LocalitateDto>> call = WebServerAPIBuilder.getInstance().getLocalitati();

        call.enqueue(new Callback<List<LocalitateDto>>() {
            @Override
            public void onResponse(Call<List<LocalitateDto>> call, retrofit2.Response<List<LocalitateDto>> response) {
                if (response.isSuccessful()) {
                    List<LocalitateDto> lista = response.body();
                    for (LocalitateDto localitateDto : lista) {
                        localitatiList.add(localitateDto);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LocalitateDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillLocalitati() {
        getLocalitati();
        adapterLocalitati = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, localitatiList);
        adapterLocalitati.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localitate.setAdapter(adapterLocalitati);
        adapterLocalitati.notifyDataSetChanged();
    }

    private UserAddressInputDto getUserAddress() {
        UserAddressInputDto userAddressInputDto = new UserAddressInputDto();
        userAddressInputDto.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        userAddressInputDto.setStrada(strada.getText().toString());
        userAddressInputDto.setNumar(Integer.valueOf(nr.getText().toString().trim()));
        userAddressInputDto.setBloc(bloc.getText().toString());
        userAddressInputDto.setScara(scara.getText().toString());
        userAddressInputDto.setNumarApartament(Integer.valueOf(ap.getText().toString().trim()));
        userAddressInputDto.setIdLocalitate(selectedValue.getIdLocalitate());
        return userAddressInputDto;
    }

    private void addAddress() {
        Call<UserDto> call = WebServerAPIBuilder.getInstance().addAddress(getUserAddress());

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, retrofit2.Response<UserDto> response) {
                if (response.isSuccessful()) {
                    Utils.hideKeyboard(ProfileFragment.this);
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
