package se.hedsec.webscraperspring.recipe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository
        extends JpaRepository<Recipe, Long> {


    @Query(value = "SELECT * FROM recipe WHERE video_url = ?1", nativeQuery = true)
    Recipe recipeExist(String videoUrl);


}
