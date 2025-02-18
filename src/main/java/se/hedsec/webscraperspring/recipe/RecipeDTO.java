package se.hedsec.webscraperspring.recipe;

import java.sql.Date;

public class RecipeDTO {
    private Long id;
    private String name;
    private String ingredients;
    private String instructions;
    private String imageUrl;
    private String videoUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
        dto.imageUrl = recipe.getImage_url();
        dto.videoUrl = recipe.getVideo_url();
        dto.ingredients = recipe.getIngredients();
        dto.instructions = recipe.getInstructions();
        dto.name = recipe.getName();
        return dto;
    }
}