package com.brightkut.commonlib.lib.exception;

import com.brightkut.commonlib.lib.api.ApiRes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiRes<String> handleBadRequest(Exception ex) {
        return ApiRes.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiRes<String> handleBadRequest(BadRequestException ex) {
        return ApiRes.error(HttpStatus.BAD_REQUEST, ex.getErrorMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiRes<String> handleNotFound(NotFoundException ex) {
        return ApiRes.error(HttpStatus.NOT_FOUND, ex.getErrorMessage());
    }

    @ExceptionHandler(BusinessException.class )
    @ResponseStatus(HttpStatus.CONFLICT)
    ApiRes<String> handleBusErr(BusinessException ex) {
        return ApiRes.error(HttpStatus.CONFLICT, ex.getErrorMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ApiRes<String> handleExistErr(AlreadyExistException ex) {
        return ApiRes.error(HttpStatus.CONFLICT, ex.getErrorMessage());
    }

//    @ExceptionHandler(AuthorizationDeniedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    ApiErrRes handleAuthDenied(AuthorizationDeniedException ex) {
//        return ApiErrRes.error(E_0031);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    ApiErrRes handleAccessDenied(AccessDeniedException ex) {
//        System.out.println(ex.getMessage());
//        return ApiErrRes.error(E_0027);
//    }

    @ExceptionHandler(UnAuthorizeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ApiRes<String> handleUnAuth(UnAuthorizeException ex) {
        return ApiRes.error(HttpStatus.UNAUTHORIZED, ex.getErrorMessage());
    }

    @ExceptionHandler({InternalServerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiRes<String> handleInternalErr(InternalServerException ex) {
        return ApiRes.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getErrorMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiRes<String> handleInternalErr(Exception ex) {
        return ApiRes.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
