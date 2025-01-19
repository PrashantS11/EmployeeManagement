package com.globant.employeeservice.feignclient;

import com.globant.employeeservice.exception.BadRequestException;
import com.globant.employeeservice.exception.InternalServerErrorException;
import com.globant.employeeservice.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                // Handle 400 Bad Request
                return new BadRequestException("Bad Request");
            case 404:
                // Handle 404 Not Found
                return new NotFoundException("Not Found");
            case 500:
                // Handle 500 Internal Server Error
                return new InternalServerErrorException("Internal Server Error");
            default:
                return new Exception("Generic error");
        }
    }
}

