package com.brightkut.commonlib.lib.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiRes<T> {
    private ApiStatusEnum status;
    private int code;
    private T data;
    private String error;

    public static <T> ApiRes<T> success(HttpStatus httpStatus, T data) {
        return new ApiRes<>(ApiStatusEnum.SUCCESS, httpStatus.value(), data, null);
    }

    public static ApiRes<String> error(HttpStatus httpStatus, String errorMsg) {
        return new ApiRes<>(ApiStatusEnum.FAILED, httpStatus.value(), null, errorMsg);
    }
}
