package com.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {
	List<Contact> findByName(String name); 
}
