package se.hedsec.webscraperspring.recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.hedsec.webscraperspring.author.Author;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path ="api/v1/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    public List<RecipeDTO> getAllRecipes() {
        List<RecipeDTO> recipeDTOs = new ArrayList<>();
        List<Recipe> recipes = recipeService.getAllRecipes();
        for(Recipe recipe : recipes){
            recipeDTOs.add(RecipeDTO.toRecipeDTO(recipe));
        }
        return recipeDTOs;
    }
    //TODO move to admin controller
    @GetMapping("/scrape")
    public void scrapeRecipes() {
        try {
            //recipeService.scrapeRecipesFromUser("ketorecipes");
            recipeService.scrapeRecipesFromUser("jalalsamfit");
            //recipeService.scrapeRecipesFromUser("its.razi");
            //respond with how many recipes were scraped and maybe their names
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/clean")
    public void removeNonRecipes(){
        recipeService.removeNonRecipes();
    }

}
