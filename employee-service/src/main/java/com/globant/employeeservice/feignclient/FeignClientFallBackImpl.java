/*package com.globant.employeeservice.feignclient;

import com.globant.employeeservice.dto.ProjectDto;
import org.springframework.http.ResponseEntity;

public class FeignClientFallBackImpl implements ProjectFeignClient {

    @Override
    public ResponseEntity<ProjectDto> getProject(long id) {
        try {
            throw new NotFoundException("hi, something wrong");
        }
    }
}*/
