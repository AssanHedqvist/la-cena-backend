package se.hedsec.webscraperspring;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRecipeRepository extends JpaRepository<AuthorRecipe, Long> {
}
