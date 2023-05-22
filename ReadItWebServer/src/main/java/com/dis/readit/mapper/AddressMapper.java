package com.dis.readit.mapper;

import com.dis.readit.dtos.address.AddressFullDto;
import com.dis.readit.model.address.Adresa;
import org.mapstruct.Mapping;

public interface AddressMapper {

	@Mapping(target = "localitate", source = "localitate.nume")
	@Mapping(target = "judet", source = "localitate.judet.nume")
	AddressFullDto mapToDto(Adresa adresa);
}
