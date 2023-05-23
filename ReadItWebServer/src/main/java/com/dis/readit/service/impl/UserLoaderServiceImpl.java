package com.dis.readit.service.impl;

import com.dis.readit.dtos.address.AddressDto;
import com.dis.readit.dtos.address.AddressFullDto;
import com.dis.readit.dtos.address.JudetDto;
import com.dis.readit.dtos.address.LocalitateDto;
import com.dis.readit.dtos.users.UserDto;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.UserMapper;
import com.dis.readit.model.address.Adresa;
import com.dis.readit.model.address.Judet;
import com.dis.readit.model.address.Localitate;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.UserLoaderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoaderServiceImpl implements UserLoaderService {


	private final UserRepository userRepository;

	private final UserMapper userMapper;

	public UserLoaderServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public DataBaseUser getUserByEmail(String email) {
		Optional<DataBaseUser> userOptional = userRepository.findUserByEmail(email);

		userOptional.orElseThrow(() -> new EntityNotFound("User with email " + email + " was not found"));
		return userOptional.get();
	}

	@Override
	public UserDto mapUserToDto(DataBaseUser user) {
		UserDto userDto = userMapper.mapToDto(user);

		userDto.setAddressDto(createAddressFullDto(user.getAdresa()));

		return userDto;
	}

	public static AddressFullDto createAddressFullDto(Adresa adresa) {

		AddressDto adresaDto = new AddressDto();
		adresaDto.setIdAdresa(adresa.getIdAdresa());
		adresaDto.setStrada(adresa.getStrada());
		adresaDto.setNumar(adresa.getNumar());
		adresaDto.setBloc(adresa.getBloc());
		adresaDto.setScara(adresa.getScara());
		adresaDto.setNumarApartament(adresa.getNumarApartament());

		AddressFullDto fullDto = new AddressFullDto();
		fullDto.setAddress(adresaDto);

		Localitate localitate = adresa.getLocalitate();
		Judet judet = localitate.getJudet();

		LocalitateDto localitateDto = new LocalitateDto();
		localitateDto.setIdLocalitate(localitate.getIdLocalitate());
		localitateDto.setNume(localitate.getNume());
		localitateDto.setIdJudet(judet.getIdJudet());

		fullDto.setLocalitate(localitateDto);

		JudetDto judetDto = new JudetDto();
		judetDto.setIdJudet(judet.getIdJudet());
		judetDto.setNume(judet.getNume());

		fullDto.setJudet(judetDto);

		return fullDto;
	}
}
