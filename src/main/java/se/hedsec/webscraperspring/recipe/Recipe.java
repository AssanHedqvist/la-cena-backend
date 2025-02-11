package se.hedsec.webscraperspring.recipe;

import jakarta.persistence.*;
import java.sql.Date;

@Entity(name = "recipe")
@Table
public class Recipe {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ingredients;
    private String instructions;
    private Date date;
    private String video_url;
    private String image_url;

    public Recipe(Long id,
                  String name,
                  String ingredients,
                  String instructions,
                  Date date,
                  String video_url,
                  String image_url) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.date = date;
        this.video_url = video_url;
        this.image_url = image_url;


    }
    public Recipe () {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}' + "\n";
    }

}
