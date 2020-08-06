package com.infosys.admin.model;

import com.infosys.admin.dto.StoreDTO;
import com.infosys.admin.AdminService;
import com.infosys.admin.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AdminService.class)
public class StoreTest {


    Store store = new Store( "alex", "titan", null);
    private StoreDTO storeDTO = new StoreDTO( (long)23445,"ana", "militari" );


    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void getId() {
        assertThat(store.getId())
                .isEqualTo(null);
    }

    @Test
    public void getName() {

        assertThat(store.getName())
                .isEqualTo("alex");
    }

    @Test
    public void getLocation() {
        assertThat(store.getLocation())
                .isEqualTo("titan");
    }

    @Test
    public void getDate() {
        assertThat(store.getDate())
                .isEqualTo(null);
    }


}
