package com.example.managestore.testRepository;

import com.example.managestore.entity.UserCredential;
import com.example.managestore.repository.manageEmployee.UserCredentialRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
public class testUserRepository {


    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Test
    public void allComponentAreNotNull() {
        UserCredential userCredential = new UserCredential();
        userCredential.setId(0L);
        userCredential.setUsername("admin");
        userCredential.setPassword("admin");
        userCredentialRepository.save(userCredential);
        Assertions.assertEquals(3,userCredentialRepository.findAll().size());
    }
}
