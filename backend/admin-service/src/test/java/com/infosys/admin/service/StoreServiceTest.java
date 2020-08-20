package com.infosys.admin.service;

import com.infosys.admin.AdminService;
import com.infosys.admin.converter.Converter;
import com.infosys.admin.dto.StoreDTO;
import com.infosys.admin.exceptions.NotCreatedException;
import com.infosys.admin.exceptions.NotFoundException;
import com.infosys.admin.model.Store;
import com.infosys.admin.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = AdminService.class)
class StoreServiceTest {


    private static final String NAME = String.valueOf(1);
    private static final Long ID = 1L;
    private static final String LOCATION = "LOCATION";
    @Mock
    AwsService awsService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    Converter converter;

    @InjectMocks
    StoreService storeService;


    @Test
    void shouldFindAll() {
        Store store = createStore();
        StoreDTO storeDTO = createStoreDTO();

        when(storeRepository.findAll()).thenReturn(Arrays.asList(store));
        when(converter.convertToDTO(eq(store))).thenReturn(storeDTO);

        List<StoreDTO> result = storeService.findAll(0,2,"id");
        verify(converter, times(1)).convertToDTO(any());
        assertEquals(storeDTO, result.get(0));
    }

    @Test
    void shouldFindStoreById() throws Exception{
        Store store = createStore();
        StoreDTO storeDTO = createStoreDTO();

        when(storeRepository.findById(ID)).thenReturn(Optional.of(store));
        when(converter.convertToDTO(eq(store))).thenReturn(storeDTO);

        StoreDTO result = storeService.findStoreById(ID);
        assertEquals(storeDTO, result);

    }

    @Test
    void shouldNotFindStoreById() {

        when(storeRepository.findById(ID)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            storeService.findStoreById(ID);
        });

        String expectedMessage = "Store not found " + ID.toString();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void shouldCreateStore() throws Exception {
        StoreDTO storeDTO = createStoreDTO();
        Store store = converter.convertToModel(storeDTO);

        when(awsService.createCollection(storeDTO.getName())).thenReturn(200);

        when(storeRepository.findById(ID)).thenReturn(Optional.empty());
        when(storeRepository.save(store)).thenReturn(store);
        when(converter.convertToModel(eq(storeDTO))).thenReturn(store);
        when(converter.convertToDTO(eq(store))).thenReturn(storeDTO);

        StoreDTO result = storeService.createStore(storeDTO);
        assertThat(result).isNotNull();
        verify(storeRepository, times(1)).save(any());

    }
    @Test
    void shouldNotCreateStore() {
        StoreDTO storeDTO = createStoreDTO();
        when(awsService.createCollection(storeDTO.getName())).thenReturn(404);
        NotCreatedException exception = assertThrows(NotCreatedException.class, () -> {
            storeService.createStore(storeDTO);
        });

        String expectedMessage = "Store was not created!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test void shouldDeleteStoreById() {
        storeService.deleteStoreById(ID);
        verify(storeRepository, times(1)).deleteById(ID);
    }


    private Store createStore() {
        Store store = new Store(null, null);
        return store;
    }


    private StoreDTO createStoreDTO() {
        StoreDTO storeDTO = new StoreDTO(ID, NAME, LOCATION);
        return storeDTO;
    }
}