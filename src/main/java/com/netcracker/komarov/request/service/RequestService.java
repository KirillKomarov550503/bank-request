package com.netcracker.komarov.request.service;

import com.netcracker.komarov.request.dao.entity.Status;
import com.netcracker.komarov.request.service.dto.entity.RequestDTO;
import com.netcracker.komarov.request.service.exception.LogicException;
import com.netcracker.komarov.request.service.exception.NotFoundException;

import java.util.Collection;

public interface RequestService {
    RequestDTO save(RequestDTO requestDTO) throws LogicException;

    RequestDTO findById(long id) throws NotFoundException;

    void deleteById(long id) throws NotFoundException;

    void deleteByEntityIdAndStatus(long entityId, Status status);

    Collection<RequestDTO> findAllRequests();
}
