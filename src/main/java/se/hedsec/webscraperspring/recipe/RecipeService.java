package se.hedsec.webscraperspring.recipe;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.hedsec.webscraperspring.*;
import se.hedsec.webscraperspring.author.Author;
import se.hedsec.webscraperspring.author.AuthorRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final AuthorRepository authorRepository;


    @Autowired
    public RecipeService(RecipeRepository recipeRepository,
                         AuthorRepository authorRepository) {
        this.recipeRepository = recipeRepository;
        this.authorRepository = authorRepository;

    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
    public List<Recipe> scrapeRecipesFromUser(String username) throws SQLException, IOException, InterruptedException {
        List<Recipe> addedRecipes = new ArrayList<>();
        List<String> videoUrls = Webscraper.getVideoUrls(username);

        videoUrls.removeIf(this::existsByUrl);
        Author author = new Author(username);
        for(String url : videoUrls) {
            String videoDesc = Webscraper.fetchVideoDesc(url);
            if(videoDesc == null) {
                continue;
            }
            Recipe recipe = Webscraper.createRecipeFromDesc(videoDesc);
            ImageHandler.generateImage(recipe.getName());
            String image_url = uploadImage("image.jpg");
            recipe.setAuthor(author);
            recipe.setImage_url(image_url);
            recipe.setDate(Date.valueOf(LocalDate.now()));
            recipe.setVideo_url(url);
            addRecipe(recipe);
            addedRecipes.add(recipe);
        }
        return addedRecipes;
    }
    private boolean existsByUrl(String url) {
        return recipeRepository.recipeExist(url) != null;
    }
    public String uploadImage(String image_url) throws IOException {
        return ImageHandler.uploadImage(image_url);
    }
    @Transactional
    public Recipe addRecipe(Recipe recipe, String username) {
        Author author = authorRepository.findByUsername(username);
        if(author == null) {
            author = new Author();
            author.setUsername(username);
            authorRepository.save(author);
        }
        recipeRepository.save(recipe);
        return recipe;
    }

    public void removeNonRecipes(){
        List<Recipe> recipes =  recipeRepository.findAll();
        for(Recipe recipe : recipes){
            if(!recipe.getIngredients().toLowerCase().contains("dl")){
                recipeRepository.delete(recipe);
            }
        }
    }
    @Transactional
    public Recipe addRecipe(Recipe recipe) {
        String username = recipe.getAuthor().getUsername();
        Author author = authorRepository.findByUsername(username);
        if (author == null) {
            author = new Author();
            author.setUsername(username);
            authorRepository.save(author);
        }
        recipe.setAuthor(author); // Set the author on the recipe
        recipeRepository.save(recipe);
        return recipe;
    }
}
