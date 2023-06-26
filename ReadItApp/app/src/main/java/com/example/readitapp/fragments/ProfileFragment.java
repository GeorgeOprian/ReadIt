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
import com.example.readitapp.model.webserver.AddressFullDto;
import com.example.readitapp.model.webserver.JudetDto;
import com.example.readitapp.model.webserver.LocalitateDto;
import com.example.readitapp.model.webserver.UserAddressInputDto;
import com.example.readitapp.model.webserver.user.output.UserDto;
import com.example.readitapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment {

    private View view;
    private TextInputEditText nume;
    private TextInputEditText email;
    private AutoCompleteTextView judete;
    private AutoCompleteTextView localitate;
    private TextInputLayout localitateLayout;
    private TextInputEditText strada;
    private TextInputEditText nr;
    private TextInputEditText bloc;
    private TextInputEditText scara;
    private TextInputEditText ap;
    private Button save;
    private ArrayAdapter<LocalitateDto> adapterLocalitati;
    private ArrayAdapter<JudetDto> adapterJudete;
    private LocalitateDto selectedValue;

    private List<JudetDto> judeteList = new ArrayList<>();
    private Map<Integer, List<LocalitateDto>> mapLocalitati = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView();
        getJudete();
        getLocalitati();
        fillControls();

        save.setOnClickListener(view -> addAddress());

        return view;
    }

    private void initView() {
        nume = view.findViewById(R.id.textInputEditTextNume);
        email = view.findViewById(R.id.textInputEditTextEmail);
        judete = view.findViewById(R.id.judete_auto);

        judete.setOnItemClickListener((adapterView, view, i, l) -> {
            JudetDto judetDto = judeteList.get(i);
            fillLocalitati(mapLocalitati.get(judetDto.getIdJudet()));
        });

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
        ((AutoCompleteTextView)localitateLayout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedValue = adapterLocalitati.getItem(position);
            }
        });
        AddressFullDto addressDto = Utils.currentUser.getAddressDto();
        if (addressDto != null && addressDto.getAddress() != null) {
            strada.setText(addressDto.getAddress().getStrada());
            if(addressDto.getAddress().getNumar() != null) {
                nr.setText(addressDto.getAddress().getNumar().toString());
            }
            bloc.setText(addressDto.getAddress().getBloc());
            scara.setText(addressDto.getAddress().getScara());
            if (addressDto.getAddress().getNumarApartament() != null) {
                ap.setText(addressDto.getAddress().getNumarApartament().toString());
            }
        }
    }

    private void getJudete() {
        Call<List<JudetDto>> call = WebServerAPIBuilder.getInstance().getJudete();

        call.enqueue(new Callback<List<JudetDto>>() {
            @Override
            public void onResponse(Call<List<JudetDto>> call, retrofit2.Response<List<JudetDto>> response) {
                if (response.isSuccessful()) {
                    judeteList.clear();
                    List<JudetDto> lista = response.body();
                    for (JudetDto judetDto : lista) {
                        judeteList.add(judetDto);
                    }
                    fillJudete();
                    if(Utils.currentUser.getAddressDto() != null) {
                        if(Utils.currentUser.getAddressDto().getJudet().getIdJudet() != null) {
                            // Check if the index is within the bounds of the adapter's data
                            int index = Utils.currentUser.getAddressDto().getJudet().getIdJudet().intValue() - 1;
                            if (index >= 0 && index < adapterJudete.getCount()) {
                                // Retrieve the item from the adapter using the index
                                JudetDto item = adapterJudete.getItem(index);
                                if (item != null) {
                                    // Get the position of the item in the adapter
                                    int position = adapterJudete.getPosition(item);
                                    String selectedItem = adapterJudete.getItem(position).getNume();
                                    // Set the selection programmatically
                                    judete.setText(selectedItem, false);
                                }
                            }
                        }
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
        adapterJudete = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, judeteList);
        adapterJudete.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        judete.setAdapter(adapterJudete);
        adapterJudete.notifyDataSetChanged();
    }

    private void getLocalitati() {
        Call<List<LocalitateDto>> call = WebServerAPIBuilder.getInstance().getLocalitati();

        call.enqueue(new Callback<List<LocalitateDto>>() {
            @Override
            public void onResponse(Call<List<LocalitateDto>> call, retrofit2.Response<List<LocalitateDto>> response) {
                if (response.isSuccessful()) {

                    mapLocalitati = createMapLocalitati(response.body());

                    fillLocalitati(mapLocalitati.get(0));

                    if (Utils.currentUser.getAddressDto() != null) {
                        if (Utils.currentUser.getAddressDto().getLocalitate().getIdLocalitate() != null) {
                            int index = Utils.currentUser.getAddressDto().getLocalitate().getIdLocalitate().intValue() - 1;
                            if (index >= 0 && index < adapterLocalitati.getCount()) {
                                // Retrieve the item from the adapter using the index
                                LocalitateDto item = adapterLocalitati.getItem(index);
                                if (item != null) {
                                    // Get the position of the item in the adapter
                                    int position = adapterLocalitati.getPosition(item);
                                    String selectedItem = adapterLocalitati.getItem(position).getNume();
                                    // Set the selection programmatically
                                    localitate.setText(selectedItem, false);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LocalitateDto>> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private Map<Integer, List<LocalitateDto>> createMapLocalitati(List<LocalitateDto> localitati) {

        Map<Integer, List<LocalitateDto>> map = localitati.stream().collect(Collectors.groupingBy(
                LocalitateDto::getIdJudet,
                LinkedHashMap::new,
                Collectors.toList()
                ));

        map.put(0, localitati);

        return map;

    }

    private void fillLocalitati(List<LocalitateDto> localitatiList) {
        if(localitatiList != null) {
            adapterLocalitati = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, localitatiList);
        } else {
            adapterLocalitati = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        }
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
        if (!ap.getText().toString().trim().equals("")) {
            userAddressInputDto.setNumarApartament(Integer.valueOf(ap.getText().toString().trim()));
        }
        if (selectedValue != null) {
            userAddressInputDto.setIdLocalitate(selectedValue.getIdLocalitate());
        } else if (Utils.currentUser.getAddressDto() != null) {
            if (Utils.currentUser.getAddressDto().getLocalitate().getIdLocalitate() != null) {
                int index = Utils.currentUser.getAddressDto().getLocalitate().getIdLocalitate().intValue() - 1;
                if (index >= 0 && index < adapterLocalitati.getCount()) {
                    // Retrieve the item from the adapter using the index
                    LocalitateDto item = adapterLocalitati.getItem(index);
                    if (item != null) {
                        // Get the position of the item in the adapter
                        userAddressInputDto.setIdLocalitate(item.getIdLocalitate());
                    }
                }
            }
        }

        return userAddressInputDto;
    }

    private void addAddress() {
        Call<UserDto> call = WebServerAPIBuilder.getInstance().addAddress(getUserAddress());

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, retrofit2.Response<UserDto> response) {
                if (response.isSuccessful()) {
                    Utils.currentUser.setAddressDto(response.body().getAddressDto());
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
