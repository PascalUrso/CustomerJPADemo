package com.example.accessingdatajpa;

import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Municipality {
    @Id
    private String name;

    @OneToOne
    private Customer mayor;


    @OneToMany(mappedBy = "city", fetch=FetchType.EAGER)
    private Collection<Customer> citizens;
    
    public Collection<Customer> getCitizens() {
        return citizens;
    }

    protected Municipality() {}

    public Municipality(String string) {
        this.name = string;
        //citizens = new java.util.ArrayList<>();
    }

    public Customer getMayor() {
        return mayor;
    }

    public String getName() {
        return name;
    }

    public void setMayor(Customer customer) {
        this.mayor = customer;
    }

    @Override
    public String toString() {
        return String.format(
                "Municipality[name='%s', mayor='%s']",
                name, mayor == null ? "none" : mayor.getFirstName() + " " + mayor.getLastName());
    }
}
