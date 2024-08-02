package by.ladzislau.gusakov.accountmanagement.mapper;

import by.ladzislau.gusakov.accountmanagement.dto.auth.request.LoginRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.auth.request.RegistrationRequestDTO;
import by.ladzislau.gusakov.accountmanagement.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(RegistrationRequestDTO registrationRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(LoginRequestDTO loginRequestDTO);
}
