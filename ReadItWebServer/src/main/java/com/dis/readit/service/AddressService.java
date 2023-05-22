package com.dis.readit.service;

import com.dis.readit.dtos.address.JudetDto;
import com.dis.readit.dtos.address.LocalitateDto;
import com.dis.readit.dtos.address.UserAddressInputDto;
import com.dis.readit.dtos.users.UserDto;

import java.util.List;

public interface AddressService {

	List<JudetDto> getJudete();

	List<LocalitateDto> getLocalitati();

	UserDto addAddress(UserAddressInputDto userAddress);
}
