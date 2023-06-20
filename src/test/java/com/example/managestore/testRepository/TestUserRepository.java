package com.example.managestore.testRepository;

import com.example.managestore.entity.employee.UserCredential;
import com.example.managestore.repository.manageEmployee.UserCredentialRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
public class TestUserRepository {



//    @Test
//    public void allComponentAreNotNull() {
//        UserCredential userCredential = new UserCredential();
//        userCredential.setId(0L);
//        userCredential.setUsername("admin");
//        userCredential.setPassword("admin");
//        userCredentialRepository.save(userCredential);
//        Assertions.assertEquals(3,userCredentialRepository.findAll().size());
//    }
}
