package com.infosys.admin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import com.infosys.admin.AdminService;
import com.infosys.admin.model.Store;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = AdminService.class)
public class StoreRepositoryTest{
    @Autowired
    private  StoreRepository storeRepository;

    @Test
    public void whenValidName_thenStoreShouldBeFound() {
        String name = "alex";
        Store s = new Store(name, "paris", (Date) null);
        storeRepository.save(s);

        Optional<List<Store>> found = storeRepository.findByName(name);
            assertThat(found.get().get(0).getName())
                .isEqualTo(name);
    }
}