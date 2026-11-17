package com.example.tourist.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tourists")
public class Tourist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String visitedDestinations;

    public Tourist() {}
    public Tourist(String name, String email, String phone, String visitedDestinations) {
        this.name = name; this.email = email; this.phone = phone;
        this.visitedDestinations = visitedDestinations;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getVisitedDestinations() { return visitedDestinations; }
    public void setVisitedDestinations(String visitedDestinations) { this.visitedDestinations = visitedDestinations; }
}
