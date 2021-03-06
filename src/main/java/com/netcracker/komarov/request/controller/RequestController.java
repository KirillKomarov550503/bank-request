package com.netcracker.komarov.request.controller;

import com.netcracker.komarov.request.service.RequestService;
import com.netcracker.komarov.request.service.dto.entity.RequestDTO;
import com.netcracker.komarov.request.service.exception.LogicException;
import com.netcracker.komarov.request.service.exception.NotFoundException;
import com.netcracker.komarov.request.service.util.ErrorJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/bank/v1/requests")
public class RequestController {
    private RequestService requestService;
    private ErrorJson errorJson;

    @Autowired
    public RequestController(RequestService requestService, ErrorJson errorJson) {
        this.requestService = requestService;
        this.errorJson = errorJson;
    }

    @ApiOperation(value = "Save request to unlock entity")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody RequestDTO requestDTO) {
        ResponseEntity responseEntity;
        try {
            RequestDTO dto = requestService.save(requestDTO);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (LogicException e) {
            responseEntity = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return responseEntity;
    }


    @ApiOperation(value = "Select all unlock requests")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAllRequests() {
        Collection<RequestDTO> dtos = requestService.findAllRequests();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @ApiOperation(value = "Delete request by entity ID and status")
    @DeleteMapping
    public ResponseEntity deleteByMap(@RequestBody Map<String, Long> entityIdsMap) {
        requestService.deleteByEntityIdAndStatus(entityIdsMap);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Delete request by ID")
    @RequestMapping(value = "/{requestId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            requestService.deleteById(requestId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Select request by ID")
    @RequestMapping(value = "/{requestId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            RequestDTO dto = requestService.findById(requestId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getErrorResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(errorJson.getErrorJson(message));
    }
}
