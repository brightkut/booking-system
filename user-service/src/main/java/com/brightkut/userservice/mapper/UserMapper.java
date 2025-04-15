package com.brightkut.userservice.mapper;

import com.brightkut.userservice.dto.RegisterUserDto;
import com.brightkut.userservice.entity.UserAuth;
import com.brightkut.userservice.entity.UserProfile;
import com.brightkut.userservice.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// componentModel = "spring" -> for create spring bean and inject via @Autowired
@Mapper(componentModel = "spring")
public interface UserMapper {

    // public UserAuth toUserAuth(RegisterUserDto registerUserDto, UserRole
    // userRole) {
    // var userProfile = UserProfile.builder()
    // .firstName(registerUserDto.getFirstName())
    // .lastName(registerUserDto.getLastName())
    // .dateOfBirth(registerUserDto.getDateOfBirth())
    // .phoneNumber(registerUserDto.getPhoneNumber())
    // .build();
    //
    // return UserAuth.builder()
    // .email(registerUserDto.getEmail())
    // .passwordHash(registerUserDto.getPassword())
    // .userProfile(userProfile)
    // .userRole(userRole)
    // .build();
    // }

    @Mapping(target = "email", source = "registerUserDto.email")
    @Mapping(target = "passwordHash", source = "registerUserDto.password")
    @Mapping(target = "userProfile.firstName", source = "registerUserDto.firstName")
    @Mapping(target = "userProfile.lastName", source = "registerUserDto.lastName")
    @Mapping(target = "userProfile.dateOfBirth", source = "registerUserDto.dateOfBirth")
    @Mapping(target = "userProfile.phoneNumber", source = "registerUserDto.phoneNumber")
    @Mapping(target = "userRole", source = "userRole")
    UserAuth toUserAuth(RegisterUserDto registerUserDto, UserRole userRole);
}
