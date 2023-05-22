package com.dis.readit.service.impl;

import com.dis.readit.dtos.address.JudetDto;
import com.dis.readit.dtos.address.LocalitateDto;
import com.dis.readit.dtos.address.UserAddressInputDto;
import com.dis.readit.dtos.users.UserDto;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.UserMapper;
import com.dis.readit.model.address.Adresa;
import com.dis.readit.model.address.Judet;
import com.dis.readit.model.address.Localitate;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.AdresaRepository;
import com.dis.readit.repository.JudetRepository;
import com.dis.readit.repository.LocalitateRepository;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.AddressService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

	private final JudetRepository judetRepository;

	private final LocalitateRepository localitateRepository;

	private final AdresaRepository addressRepository;
	private final UserRepository userRepository;

	private final UserMapper userMapper;

	public AddressServiceImpl(JudetRepository judetRepository, LocalitateRepository localitateRepository, AdresaRepository addressRepository, UserRepository userRepository, UserMapper userMapper) {
		this.judetRepository = judetRepository;
		this.localitateRepository = localitateRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public List<JudetDto> getJudete() {
		List<Judet> judete = judetRepository.findAll();

		return judete.stream()
				.map(judet -> new JudetDto(judet.getIdJudet(), judet.getNume()))
				.collect(Collectors.toList());
	}

	@Override
	public List<LocalitateDto> getLocalitati() {
		List<Localitate> localitates = localitateRepository.findAll();

		return localitates.stream()
							.map(localitate -> new LocalitateDto(localitate.getIdLocalitate(), localitate.getNume(), localitate.getJudet().getIdJudet()))
							.collect(Collectors.toList());
	}

	@Override
	public UserDto addAddress(UserAddressInputDto userAddress) {

		DataBaseUser user = getUserFromDb(userAddress.getUserEmail());

		Adresa addressToSave = createAddressFromDto(userAddress);

		DataBaseUser savedUser = saveAddressAndUser(addressToSave, user);

		UserDto userDto = userMapper.mapToDto(savedUser);

		return userDto;
	}

	@Transactional
	private DataBaseUser saveAddressAndUser(Adresa adresa, DataBaseUser user) {
		Adresa savedAddress = addressRepository.save(adresa);

		user.setAdresa(savedAddress);

		return userRepository.save(user);
	}

	private Adresa createAddressFromDto(UserAddressInputDto addressDto) {
		Adresa adresa = new Adresa();

		adresa.setStrada(addressDto.getStrada());
		adresa.setNumar(addressDto.getNumar());
		adresa.setBloc(addressDto.getBloc());
		adresa.setScara(addressDto.getScara());
		adresa.setNumarApartament(addressDto.getNumarApartament());
		adresa.setLocalitate(getLocalitateFromDb(addressDto.getIdLocalitate()));

		return adresa;
	}

	private DataBaseUser getUserFromDb(String email) {
		Optional<DataBaseUser> userOptional = userRepository.findUserByEmail(email);

		userOptional.orElseThrow(() -> new EntityNotFound("User with email " + email + " was not found"));
		return userOptional.get ();
	}

	private Localitate getLocalitateFromDb(Integer idLocalitate) {
		Optional<Localitate> localitateOptional = localitateRepository.findById(idLocalitate);

		localitateOptional.orElseThrow(() -> new EntityNotFound("City with id" + idLocalitate  + " was not found"));
		return localitateOptional.get ();
	}
}
