package com.brightkut.userservice.dto;

import com.brightkut.userservice.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRoleDto {
    private RoleEnum role;
}
