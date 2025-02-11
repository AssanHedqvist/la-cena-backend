package se.hedsec.webscraperspring;

import jakarta.persistence.*;
import se.hedsec.webscraperspring.recipe.Recipe;

@Entity(name = "author_recipe")
public class AuthorRecipe {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public AuthorRecipe(Author author, Recipe recipe) {
        this.author = author;
        this.recipe = recipe;
    }

    public AuthorRecipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
