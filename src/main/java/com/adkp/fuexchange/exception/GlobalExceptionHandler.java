package com.adkp.fuexchange.exception;

import com.adkp.fuexchange.response.ResponseObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseObject usernameNotFoundException() {
        return ResponseObject.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.name())
                .content("Mã số sinh viên không tồn tại hoặc chưa được đăng ký")
                .build();
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseObject dataAccessException(UsernameNotFoundException usernameNotFoundException) {
        return ResponseObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .content("Lỗi trong quá trình lưu trữ dữ liệu!")
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseObject exception(Exception exception) {
        return ResponseObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .content("Lỗi không xác định!")
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseObject entityNotFoundException(Exception exception) {
        return ResponseObject.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.name())
                .content("Thực thể không tồn tại")
                .build();
    }
}
