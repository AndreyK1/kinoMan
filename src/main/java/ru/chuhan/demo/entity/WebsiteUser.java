package ru.chuhan.demo.entity;

import javax.persistence.*;

//not working
@Table(name = "website_users")
@Entity
public class WebsiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String email;

    // standard getters and setters
}
