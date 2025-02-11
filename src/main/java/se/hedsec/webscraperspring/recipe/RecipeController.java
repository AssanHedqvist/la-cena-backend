package se.hedsec.webscraperspring.recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path ="api/v1/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    public String getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/scrape")
    public void scrapeRecipes() {
        try {
            recipeService.scrapeRecipesFromUser("ketorecipes");
            //respond with how many recipes were scraped and maybe their names
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/upload")
    public String uploadImage() {
        try {
            return recipeService.uploadImage("image.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload image";
        }
    }

    @GetMapping("/addtest")
    public void addTestRecipe() {
        try {
            Recipe recipe = new Recipe();
            recipe.setName("Test Recipe");
            recipe.setIngredients("Test Ingredients");
            recipe.setInstructions("Test Instructions");
            recipe.setVideo_url("Test Video URL");
            recipe.setImage_url("Test Image URL");
            recipe.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
            recipeService.addRecipe(recipe, "ketorecipes");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
