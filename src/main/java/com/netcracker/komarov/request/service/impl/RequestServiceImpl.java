package com.netcracker.komarov.request.service.impl;

import com.netcracker.komarov.request.dao.entity.Request;
import com.netcracker.komarov.request.dao.repository.RequestRepository;
import com.netcracker.komarov.request.service.RequestService;
import com.netcracker.komarov.request.service.dto.converter.impl.RequestConverter;
import com.netcracker.komarov.request.service.dto.entity.RequestDTO;
import com.netcracker.komarov.request.service.exception.LogicException;
import com.netcracker.komarov.request.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;
    private RequestConverter converter;
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestServiceImpl.class);

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, RequestConverter converter) {
        this.requestRepository = requestRepository;
        this.converter = converter;
    }

    private Collection<RequestDTO> convertCollection(Collection<Request> requests) {
        return requests.stream()
                .map(request -> converter.convertToDTO(request))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RequestDTO save(RequestDTO requestDTO) throws LogicException {
        Request request;
        Request temp = requestRepository
                .findRequestByEntityIdAndAndStatus(requestDTO.getEntityId(), requestDTO.getStatus());
        if (temp == null) {
            request = requestRepository.save(new Request(requestDTO.getEntityId(), requestDTO.getStatus()));
            LOGGER.info("Request was added to database with ID " + request.getId());
        } else {
            String error = "Account with ID " + requestDTO.getEntityId()
                    + " and status " + requestDTO.getStatus() + " have already added to requests";
            LOGGER.error(error);
            throw new LogicException(error);
        }
        return converter.convertToDTO(request);
    }

    @Transactional
    @Override
    public Collection<RequestDTO> findRequests() {
        LOGGER.info("Select all requests");
        return convertCollection(requestRepository.findAll());
    }

    @Transactional
    @Override
    public RequestDTO findById(long id) throws NotFoundException {
        Request request;
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            request = optionalRequest.get();
            LOGGER.info("Return request with ID: " + id);
        } else {
            String error = "There is no such request in database";
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return converter.convertToDTO(request);
    }

    @Transactional
    @Override
    public void deleteById(long id) throws NotFoundException {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            requestRepository.deleteRequestById(id);
            LOGGER.info("Request was deleted successful");
        } else {
            String error = "There is no such request in database";
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
    }
}
