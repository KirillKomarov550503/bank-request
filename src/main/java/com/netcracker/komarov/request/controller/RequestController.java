package com.netcracker.komarov.request.controller;

import com.netcracker.komarov.request.service.RequestService;
import com.netcracker.komarov.request.service.dto.entity.RequestDTO;
import com.netcracker.komarov.request.service.exception.LogicException;
import com.netcracker.komarov.request.service.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bank/v1/requests")
public class RequestController {
    private RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @ApiOperation(value = "Save request to unlock entity")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody RequestDTO requestDTO) {
        ResponseEntity responseEntity;
        try {
            RequestDTO dto = requestService.save(requestDTO);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
        }
        return responseEntity;
    }


    @ApiOperation(value = "Selecting all unlock requests")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAllRequests() {
        Collection<RequestDTO> dtos = requestService.findAllRequests();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @ApiOperation(value = "Deleting request by ID")
    @RequestMapping(value = "/{requestId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            requestService.deleteById(requestId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting request by ID")
    @RequestMapping(value = "/{requestId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            RequestDTO dto = requestService.findById(requestId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity getInternalServerErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
