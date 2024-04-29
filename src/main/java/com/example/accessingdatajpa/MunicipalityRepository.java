package com.example.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

public interface MunicipalityRepository extends CrudRepository<Municipality, String> {
	Municipality findByName(String name);
}
