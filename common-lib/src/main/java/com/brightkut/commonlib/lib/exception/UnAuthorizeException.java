package com.brightkut.commonlib.lib.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Data
public class UnAuthorizeException extends RuntimeException {
    private final String errorMessage;
}