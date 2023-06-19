package com.brdomain.demotestcontainerspring3.repository;

import com.brdomain.demotestcontainerspring3.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
