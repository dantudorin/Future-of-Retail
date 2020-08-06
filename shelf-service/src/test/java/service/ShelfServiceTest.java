package service;

import com.infosys.ShelfServiceApplication;
import com.infosys.exceptions.CustomerNotFoundException;
import com.infosys.service.AwsService;
import com.infosys.service.ShelfService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ShelfServiceApplication.class, AwsService.class, ShelfService.class})
public class ShelfServiceTest {

    @Mock
    private AwsService awsService;

    @InjectMocks
    private ShelfService shelfService;

    @Test
    public void shouldPutOnShelf() throws CustomerNotFoundException, IOException {
        String collectionName = "Carrefour";
        String name = "sami.jpg";
        String contentType = "image/jpg";
        MultipartFile result = new MockMultipartFile(name, name, contentType,
                name.getBytes());
        Assertions.assertNotNull(result);
        when(awsService.searchFace (collectionName, result)).thenReturn(Optional.of((long) 1));
        Optional<Long> id = awsService.searchFace (collectionName, result);
        verify(awsService, times(1)).searchFace(collectionName, result);
        assertThat(shelfService.putOnShelf(result, collectionName)).isNotNull();
    }

    @Test
    public void shouldTakeFromShelf() throws CustomerNotFoundException, IOException {
        String collectionName = "Carrefour";
        String name = "sami.jpg";
        String contentType = "image/jpg";
        MultipartFile result = new MockMultipartFile(name, name, contentType,
                name.getBytes());
        Assertions.assertNotNull(result);
        when(awsService.searchFace (collectionName, result)).thenReturn(Optional.of((long) 1));
        Optional<Long> id = awsService.searchFace (collectionName, result);
        verify(awsService, times(1)).searchFace(collectionName, result);
        assertThat(shelfService.putOnShelf(result, collectionName)).isNotNull();
    }
}
