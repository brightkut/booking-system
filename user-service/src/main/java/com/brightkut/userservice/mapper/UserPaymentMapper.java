package com.brightkut.userservice.mapper;

import com.brightkut.userservice.dto.AddPaymentCardDto;
import com.brightkut.userservice.entity.UserAuth;
import com.brightkut.userservice.entity.UserPaymentCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPaymentMapper {

   @Mapping(target = "cardType", source = "addPaymentCardDto.cardType")
   @Mapping(target = "cardNumber", source = "addPaymentCardDto.cardNumber")
   @Mapping(target = "cardHolderName", source = "addPaymentCardDto.cardHolderName")
   @Mapping(target = "userAuth", source = "userAuth")
   UserPaymentCard toUserPaymentCard(AddPaymentCardDto addPaymentCardDto, UserAuth userAuth);
}
