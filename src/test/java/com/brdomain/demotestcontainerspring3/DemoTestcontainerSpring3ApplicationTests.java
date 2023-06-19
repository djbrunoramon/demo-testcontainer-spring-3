package com.brdomain.demotestcontainerspring3;

import com.brdomain.demotestcontainerspring3.domain.Person;
import com.brdomain.demotestcontainerspring3.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class DemoTestcontainerSpring3ApplicationTests {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:14")
            .withDatabaseName("testcontainer")
            .withUsername("usertest")
            .withPassword("1234");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> container.getJdbcUrl());
        registry.add("spring.datasource.username", () -> container.getUsername());
        registry.add("spring.datasource.password", () -> container.getPassword());
    }

    @Autowired
    private PersonRepository repository;

    @Test
    void contextLoads() {
        System.out.println("Hello");
        assertThat(container.isRunning()).isTrue();
    }

    @Test
    void addPerson_ExpectOneAdded() {
        Person person = new Person();
        person.setName("Bruno Ramon");
        person.setAge(23);

        Person personSaved = repository.save(person);

        assertThat(repository.findAll().size()).isOne();
        assertThat(personSaved.getId()).isNotNull();
        assertThat(personSaved.getName()).isEqualTo(person.getName());
        assertThat(personSaved.getAge()).isEqualTo(person.getAge());
    }

}
