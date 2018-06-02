package com.netcracker.komarov.request.service.impl;

import com.netcracker.komarov.TestConfig;
import com.netcracker.komarov.request.dao.entity.Status;
import com.netcracker.komarov.request.service.RequestService;
import com.netcracker.komarov.request.service.dto.entity.RequestDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RequestServiceImplTest {
    @Mock
    @Autowired
    private RequestService requestService;

    @Before
    public void setUp() {
        requestService.save(new RequestDTO(0L, 1L, Status.ACCOUNT));
        requestService.save(new RequestDTO(0L, 1L, Status.CARD));
        requestService.save(new RequestDTO(0L, 2L, Status.CARD));
    }

    @Test
    public void save() {
        RequestDTO dto = new RequestDTO(4L, 3L, Status.ACCOUNT);
        assertEquals(dto, requestService.save(dto));
    }

    @Test
    public void findAllRequests() {
        Collection<RequestDTO> dtos = Stream.of(
                new RequestDTO(1L, 1L, Status.ACCOUNT),
                new RequestDTO(2L, 1L, Status.CARD),
                new RequestDTO(3L, 2L, Status.CARD))
                .collect(Collectors.toList());
        assertEquals(dtos, requestService.findAllRequests());
    }

    @Test
    public void findById() {
        assertEquals(new RequestDTO(2L, 1L, Status.CARD), requestService.findById(2));
    }

    @Test
    public void deleteById() {
        requestService = mock(RequestService.class);
        doNothing().when(requestService).deleteById(isA(Long.class));
        requestService.deleteById(8432582);
        verify(requestService, times(1)).deleteById(8432582);
    }
}