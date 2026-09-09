package com.acook.magmutualusersapi.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Bridge object for converting from all lower column names to camel case without affecting API return types
 */
@JsonPropertyOrder({"id", "firstname", "lastname", "email", "profession", "dateCreated", "country", "city"})
// user is a reserved name so we must use something else
public class CsvUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    private String email;
    private String profession;
    private String country;
    private String city;
    private LocalDate dateCreated;

    // Should we have an empty constructor at all?
    public CsvUser() {}

    public CsvUser(Long id, String firstName, String lastName, String email, String profession, String country, String city, LocalDate dateCreated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profession = profession;
        this.country = country;
        this.city = city;
        this.dateCreated = dateCreated;
    }

    public CsvUser(String firstName, String lastName, String email, String profession, String country, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profession = profession;
        this.country = country;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

}
