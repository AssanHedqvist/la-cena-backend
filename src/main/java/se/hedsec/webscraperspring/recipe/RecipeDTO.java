package se.hedsec.webscraperspring.recipe;

import java.sql.Date;

public class RecipeDTO {
    private Long id;
    private String name;
    private String ingredients;
    private String instructions;
    private String image_url;
    private String video_url;
    private Date date;

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
    public static RecipeDTO toRecipeDTO(Recipe recipe){
        RecipeDTO dto = new RecipeDTO();
        dto.date = recipe.getDate();
        dto.id = recipe.getId();
        dto.image_url = recipe.getImage_url();
        dto.video_url = recipe.getVideo_url();
        dto.ingredients = recipe.getIngredients();
        dto.instructions = recipe.getInstructions();
        dto.name = recipe.getName();
        return dto;
    }
}