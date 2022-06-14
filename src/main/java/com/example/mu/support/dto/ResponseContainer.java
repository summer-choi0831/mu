package com.example.mu.support.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ResponseContainer<T> implements Serializable {

    private static final long serialVersionUID = 894431147195944885L;

    @ApiModelProperty(required = true, value = "success", example = "true")
    private boolean success;

    @Nullable
    @ApiModelProperty("result")
    private T result;

    @Nullable
    @ApiModelProperty("status")
    private HttpStatus httpStatus;

    public ResponseContainer(@NonNull Boolean isSuccess, @Nullable T responseData, @Nullable HttpStatus status) {
        success = isSuccess;
        httpStatus = status;
        result = responseData;
    }

    @NonNull
    public static ResponseContainer success() {
        return new ResponseContainer<>(true, null, HttpStatus.NO_CONTENT);
    }

    @NonNull
    public static <T> ResponseContainer success(@Nullable T data) {
        return new ResponseContainer<>(true, data, HttpStatus.OK);
    }

    @NonNull
    private static ResponseContainer empty() {
        return new ResponseContainer<>(true, null, HttpStatus.NO_CONTENT);
    }

    public static <T> ResponseContainer<T> fail(@Nullable T data) {
        return new ResponseContainer<>(false, data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
