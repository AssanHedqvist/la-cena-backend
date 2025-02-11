package se.hedsec.webscraperspring.recipe;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.hedsec.webscraperspring.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;


@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final AuthorRepository authorRepository;
    private final AuthorRecipeRepository authorRecipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository,
                         AuthorRepository authorRepository,
                         AuthorRecipeRepository authorRecipeRepository) {
        this.recipeRepository = recipeRepository;
        this.authorRepository = authorRepository;
        this.authorRecipeRepository = authorRecipeRepository;

    }

    public String getAllRecipes() {
        return recipeRepository.findAll().toString();
    }
    public void scrapeRecipesFromUser(String username) throws SQLException, IOException, InterruptedException {
        List<String> videoUrls = Webscraper.getVideoUrls(username);

        videoUrls.removeIf(this::existsByUrl);
        for(String url : videoUrls) {

            String videoDesc = Webscraper.fetchVideoDesc(url);
            if(videoDesc == null) {
                continue;
            }
            Recipe recipe = Webscraper.createRecipeFromDesc(videoDesc);
            ImageHandler.generateImage(recipe.getName());
            String image_url = uploadImage("image.jpg");
            recipe.setImage_url(image_url);
            recipe.setDate(Date.valueOf(LocalDate.now()));
            recipe.setVideo_url(url);
            recipeRepository.save(recipe);
        }
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
        authorRecipeRepository.save(new AuthorRecipe(author, recipe));
        return recipe;
    }
}
