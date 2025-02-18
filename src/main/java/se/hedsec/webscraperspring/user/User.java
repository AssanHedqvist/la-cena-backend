package se.hedsec.webscraperspring.user;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }
    public User() {
    }


}
