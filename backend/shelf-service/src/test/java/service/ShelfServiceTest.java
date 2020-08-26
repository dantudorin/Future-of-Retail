package service;

import com.infosys.ShelfServiceApplication;
import com.infosys.config.IdentifyActions;
import com.infosys.enums.CustomerAction;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ShelfServiceApplication.class, AwsService.class,
        ShelfService.class, IdentifyActions.class})
public class ShelfServiceTest {

    @Mock
    private AwsService awsService;

    @InjectMocks
    private ShelfService shelfService;

    @Mock
    private IdentifyActions detectLabels;

    @Test
    public void shouldDetectPutTake() throws CustomerNotFoundException, IOException {
        String collectionName = "Carrefour";
        String name = "emptyhand.jpg";
        String contentType = "image/jpg";
        MultipartFile result = new MockMultipartFile(name, name, contentType,
                name.getBytes());
        Assertions.assertNotNull(result);
        String name2 = "fantainhand.jpg";
        MultipartFile result2 = new MockMultipartFile(name2, name2, contentType,
                name2.getBytes());
        Assertions.assertNotNull(result2);
        List<MultipartFile> photos = new ArrayList<MultipartFile>();
        photos.add(result);
        photos.add(result2);
        when(detectLabels.detectAction(photos)).thenReturn(CustomerAction.NOT_ABLE_TO_IDENTIFY);
        CustomerAction action = shelfService.detectPutTake(photos, collectionName);
    }

    @Test
    public void shouldIdentifyCustomer() throws CustomerNotFoundException {
        String collectionName = "Carrefour";
        String name = "emptyhand.jpg";
        String contentType = "image/jpg";
        MultipartFile result = new MockMultipartFile(name, name, contentType,
                name.getBytes());
        Assertions.assertNotNull(result);
        String name2 = "fantainhand.jpg";
        MultipartFile result2 = new MockMultipartFile(name2, name2, contentType,
                name2.getBytes());
        Assertions.assertNotNull(result2);
        List<MultipartFile> photos = new ArrayList<MultipartFile>();
        photos.add(result);
        photos.add(result2);
        when(awsService.searchFace (collectionName, result)).thenReturn(Optional.of((long) 1));
        Optional<Long> id = awsService.searchFace (collectionName, result);
        verify(awsService, times(1)).searchFace(collectionName, result);
    }
}
