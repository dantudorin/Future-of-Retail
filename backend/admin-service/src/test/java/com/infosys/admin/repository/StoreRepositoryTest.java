package com.infosys.admin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import com.infosys.admin.AdminService;
import com.infosys.admin.model.Store;
import org.junit.jupiter.api.Test;
import java.util.Optional;

@SpringBootTest(classes = AdminService.class)
public class StoreRepositoryTest{
    @Autowired
    private  StoreRepository storeRepository;

    @Test
    public void whenValidName_thenStoreShouldBeFound() {
        String name = "alex";
        Store s = new Store(name, "paris", null);
        storeRepository.save(s);

        Optional<Store> found = storeRepository.findByName(name);
            assertThat(found.get().getName())
                .isEqualTo(name);
    }
}