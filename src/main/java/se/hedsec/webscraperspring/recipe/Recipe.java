package se.hedsec.webscraperspring.recipe;

import jakarta.persistence.*;
import se.hedsec.webscraperspring.author.Author;

import java.sql.Date;

@Entity(name="recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String video_url;

    private String name;

    @Column(length = 2048)
    private String ingredients;

    @Column(length = 2048)
    private String instructions;

    private String image_url;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Recipe(String name, String ingredients, String instructions, Author author) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.author = author;
    }

    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "date=" + date +
                ", image_url='" + image_url + '\'' +
                ", instructions='" + instructions + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", name='" + name + '\'' +
                ", video_url='" + video_url + '\'' +
                ", id=" + id +
                '}';
    }
}