package com.example.accessingdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class AccessingDataJpaApplication {

	private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataJpaApplication.class);
	}

	@Autowired
	CustomerRepository repository;

	@Autowired
	MunicipalityRepository mRep;

	@PostConstruct
	public void demo() {
		// Cities
		Municipality n = new Municipality("Nice"),
				m = new Municipality("Marseille");
		mRep.save(n);
		mRep.save(m);

		// save a few customers
		repository.save(new Customer("Jack", "Bauer", m));
		repository.save(new Customer("Chloe", "O'Brian", n));
		repository.save(new Customer("Kim", "Bauer", n));
		repository.save(new Customer("David", "Palmer", m));
		repository.save(new Customer("Michelle", "Dessler", m));

		// fetch all customers
		log.info("Customers found with findAll():");
		log.info("-------------------------------");
		repository.findAll().forEach(customer -> {
			log.info(customer.toString());
		});
		log.info("");

		// fetch an individual customer by ID
		Customer customer = repository.findById(1L);
		log.info("Customer found with findById(1L):");
		log.info("--------------------------------");
		log.info(customer.toString());
		log.info("");

		n.setMayor(customer);
		mRep.save(n);

		log.info("Cities found with findAll():");
		log.info("-------------------------------");
		mRep.findAll().forEach(city -> {
			log.info(city.toString());
		});
		log.info("");

		log.info("Nice citizens:");
		log.info("---------------");
		mRep.findByName("Nice").getCitizens().forEach(h -> {
			log.info(h.toString());
		});
		log.info("");

		// fetch customers by last name
		log.info("Customer found with findByLastName('Bauer'):");
		log.info("--------------------------------------------");
		repository.findByLastName("Bauer").forEach(bauer -> {
			log.info(bauer.toString());
		});
		log.info("");

		customer = repository.findById(1L);
		repository.findById(2L).addFollows(customer);
		repository.findById(4L).addFollows(customer);
		repository.save(customer);

		// fetch followers of a customer
		log.info("Followers of Jack Bauer:");
		log.info("-------------------------------");
		repository.findById(1L).getFollowers().forEach(follower -> {
			log.info(follower.toString());
		});
		log.info("");
	}
}
