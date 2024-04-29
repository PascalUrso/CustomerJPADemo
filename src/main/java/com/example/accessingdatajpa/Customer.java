package com.example.accessingdatajpa;

import java.util.Collection;

import jakarta.persistence.*;

@Entity
@Table(name = "nobels")
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String firstName;
	@Column(name = "surname")
	private String lastName;

	protected Customer() {}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Customer(String firstName, String lastName, Municipality city) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
	}
	
	@OneToOne(mappedBy = "mayor")
	private Municipality municipality;

	public Municipality getMunicipality() {
		return municipality;
	}
	
	@ManyToOne
	private Municipality city;

	public Municipality getCity() {
		return city;
	}

	public void setCity(Municipality city) {
		this.city = city;
	}

	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}


	@ManyToMany(mappedBy = "followers", fetch=FetchType.EAGER)
	Collection<Customer> follows;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "follows", 
		joinColumns = @JoinColumn(name = "followed_id"),
		inverseJoinColumns = @JoinColumn(name = "follower_id"))
	Collection<Customer> followers;

	void addFollows(Customer c) {
		follows.add(c);
		c.followers.add(this);
	}

	public Collection<Customer> getFollowers() {
		return followers;
	}

	public Collection<Customer> getFollows() {
		return follows;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, firstName='%s', lastName='%s', city='%s']%s",
				id, firstName, lastName, city.getName(), municipality == null ? "" : " mayor of " + municipality.getName());
	}
}
