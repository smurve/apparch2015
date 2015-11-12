package org.smurve.hsr2014.domain.mongo;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface PersonRepo extends PagingAndSortingRepository<Person, String> {

    List<Person> findByName(String name);
}
