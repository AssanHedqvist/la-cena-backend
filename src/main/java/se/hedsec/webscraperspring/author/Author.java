package se.hedsec.webscraperspring.author;

import jakarta.persistence.*;
import se.hedsec.webscraperspring.recipe.Recipe;

import java.util.List;

@Entity(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;

    public Author(String username) {
        this.username = username;
    }

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}