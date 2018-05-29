package com.netcracker.komarov.request.service.dto.converter.impl;

import com.netcracker.komarov.request.dao.entity.Request;
import com.netcracker.komarov.request.service.dto.converter.Converter;
import com.netcracker.komarov.request.service.dto.entity.RequestDTO;
import org.springframework.stereotype.Component;

@Component
public class RequestConverter implements Converter<RequestDTO, Request> {
    @Override
    public RequestDTO convertToDTO(Request request) {
        RequestDTO dto = null;
        if (request != null) {
            dto = new RequestDTO();
            dto.setId(request.getId());
            dto.setEntityId(request.getEntityId());
            dto.setStatus(request.getStatus());
        }
        return dto;
    }

    @Override
    public Request convertToEntity(RequestDTO requestDTO) {
        Request request = new Request();
        request.setEntityId(requestDTO.getEntityId());
        request.setStatus(requestDTO.getStatus());
        return request;
    }
}
