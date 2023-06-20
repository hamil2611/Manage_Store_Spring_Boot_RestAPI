package com.example.managestore.testRepository;

import com.example.managestore.entity.employee.Role;
import com.example.managestore.repository.manageEmployee.RoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
public class TestRoleRepository {

//    @Test
//    public void testGetAll(){
//        Assert.assertEquals(1,roleRepository.findAll().size());
//        Assert.assertEquals("EMPLOYEE",roleRepository.findById(1L).get().getName());
//    }
}
