package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.RequestRepository;
import com.bnl.bloodbank.service.RequestService;
import com.bnl.bloodbank.serviceImpl.RequestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest
@TestPropertySource(properties = "email=test@example.com")
public class RequestTest {

    @Mock
    RequestRepository requestRepository;

    @InjectMocks
    RequestService requestService = new RequestServiceImpl();

    private static Request request =
            Request.builder()
                    .requestId(1)
                    .bloodGroup("A+")
                    .quantity(2)
                    .build();

    /**
     * To check addRequest is successful
     */
    @Test
    void validAddReqeust() {
        Assertions.assertEquals("Request saved successfully", requestService.addRequest(request));
    }

    /**
     * To check update status of request is successful if requestId is valid
     * @throws NotPresentException
     */
    @Test
    void validUpdateStatus() throws NotPresentException{
        Mockito.when(requestRepository.findById(request.getRequestId())).thenReturn(Optional.of(request));
        Assertions.assertEquals("Status for Request ID " + request.getRequestId() + " successfully updated", requestService.updateStatus(request.getRequestId(), request.getStatus()));
    }

    /**
     * To check updateStatus throws NotPresentException if requestId is not valid
     * @throws NotPresentException
     */
    @Test
    void invalidUpdateStatus() throws NotPresentException{
        Mockito.when(requestRepository.findById(request.getRequestId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> requestService.updateStatus(request.getRequestId() ,request.getStatus())
        );
        Assertions.assertEquals("Request for id " + request.getRequestId() + " not found", ex.getMessage());
    }

    /**
     * to check deleteRequest is successful if requestId is valid
     * @throws NotPresentException
     */
    @Test
    void validDeleteRequest() throws NotPresentException{
        Mockito.when(requestRepository.findById(request.getRequestId())).thenReturn(Optional.of(request));
        Assertions.assertEquals("Request with ID " + request.getRequestId() + " successfully deleted", requestService.deleteRequest(request.getRequestId()));
    }

    /**
     * To check deleteRequest throws NotPresentException if requestId is not valid
     * @throws NotPresentException
     */
    @Test
    void invalidDeleteRequest() throws NotPresentException{
        Mockito.when(requestRepository.findById(request.getRequestId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> requestService.deleteRequest(request.getRequestId())
        );
        Assertions.assertEquals("Request for id " + request.getRequestId() + " not found", ex.getMessage());
    }
}
