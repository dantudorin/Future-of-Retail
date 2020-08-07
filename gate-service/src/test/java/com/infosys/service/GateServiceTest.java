package com.infosys.service;

import com.infosys.GateServiceApplication;
import com.infosys.exception.UserNotFoundException;
import com.infosys.model.Customer;
import com.infosys.repository.CustomerRepository;
import com.infosys.requestModel.EntryRequest;
import com.infosys.requestModel.ExitRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GateServiceApplication.class, AwsService.class, GateService.class})
public class GateServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private GateService gateService;

    @Mock
    private AwsService awsService;

    @Test
    public void processEntryRequestTest() throws IOException, UserNotFoundException {
        EntryRequest entryRequest;
        String collectionName = "Carrefour";
        Long customerId = Long.valueOf(1);
        Customer customer = new Customer(customerId, "andra");

        customerRepository.save(customer);
        doReturn(Optional.of(customer)).when(customerRepository).findById(customerId);

        String resourceName = "andra.jpg";

        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        Mockito.when(mockMultipartFile.getName()).thenReturn(resourceName);
        assertNotNull(mockMultipartFile);

        MultipartFile multipartFile = (MultipartFile) mockMultipartFile;
        assertEquals(mockMultipartFile.getClass(),multipartFile.getClass());

        List<MultipartFile> photos = new ArrayList<>();
        photos.add(multipartFile);

        entryRequest = new EntryRequest(collectionName, customerId, photos);
        Mockito.when(gateService.processEntryRequest(entryRequest)).thenReturn(true);
    }

    @Test
    public void processExistRequestTest() throws IOException, UserNotFoundException {
        Customer customer;
        ExitRequest exitRequest;
        List<MultipartFile> photos = new ArrayList<>();

        String collectionName = "Carrefour";
        Long customerId = Long.valueOf(1);

        customer = new Customer(customerId, "andra");
        doReturn(Optional.of(customer)).when(customerRepository).findById(customerId);

        String resourceName = "andra.jpg";

        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        Mockito.when(mockMultipartFile.getName()).thenReturn(resourceName);
        assertNotNull(mockMultipartFile);

        MultipartFile multipartFile = (MultipartFile) mockMultipartFile;
        assertEquals(mockMultipartFile.getClass(),multipartFile.getClass());

        photos.add(multipartFile);
        exitRequest = new ExitRequest(collectionName, customerId, photos);
        gateService.processExitRequest(exitRequest);
        verify(customerRepository, times(1)).findById(exitRequest.getCustomerId());
    }

    @Test
    public void processEntryRequestUserNotFoundTest() throws IOException, UserNotFoundException {
        EntryRequest entryRequest;
        String collectionName = "Carrefour";
        Long customerId = Long.valueOf(1);
        List<MultipartFile> photos = new ArrayList<>();

        String resourceName = "andra.jpg";

        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        Mockito.when(mockMultipartFile.getName()).thenReturn(resourceName);
        assertNotNull(mockMultipartFile);

        MultipartFile multipartFile = (MultipartFile) mockMultipartFile;
        assertEquals(mockMultipartFile.getClass(),multipartFile.getClass());

        photos.add(multipartFile);
        entryRequest = new EntryRequest(collectionName, customerId, photos);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            gateService.processEntryRequest(entryRequest);
        });

        String expectedMessage = "User with id " + customerId + " not found !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

