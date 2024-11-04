package ymfc.commands;

import ymfc.list.IngredientList;
import ymfc.list.RecipeList;
import ymfc.recipe.Recipe;
import ymfc.storage.Storage;
import ymfc.ui.Ui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import static ymfc.YMFC.logger;

/**
 * Represents the command to recommend the user a recipe from {@code RecipeList}.
 * Recipes with ingredients that matches what the user has in {@code IngredientList}
 * are recommended to the user via {@code Ui}.
 */
public class RecommendCommand extends Command {

    public static final String USAGE_EXAMPLE = """
            Use example:
            \trecommend
            """;

    public RecommendCommand() {
        super();

        logger.log(Level.FINEST, "Creating RecommendCommand");
    }

    /**
     * Executes the {@code RecommendCommand}.
     *
     * @param recipes The {@code RecipeList} to choose the recipes from. Must not be {@code null}.
     * @param ingredients The {@code IngredientList} to ascertain user available ingredients.
     * @param ui The {@code Ui} to inform the user of the recommended recipes.
     * @param storage The {@code Storage}. Unused in this command.
     */
    public void execute(RecipeList recipes, IngredientList ingredients, Ui ui, Storage storage) {
        logger.log(Level.FINEST, "Executing RecommendCommand");
        assert recipes.getCounter() > 0;

        RecipeList recommendList = new RecipeList();
        ArrayList<Float> percentMatchList = new ArrayList<Float>();
        ArrayList<ArrayList<String>> mismatchList= new ArrayList<ArrayList<String>>();
        ArrayList<String> ingredientsList = ingredients.getIngredientsString();

        // Iterate through all recipes and find recipes with matching ingredients
        for (int i = 0; i < recipes.getCounter(); i++) {
            Recipe targetRecipe = recipes.getRecipe(i);

            // Find ingredients of recipe that matches ingredient list
            // Clone is used so that a new arraylist is created
            // and the retainAll method doesn't overwrite the original recipe
            ArrayList<String> matchIngredients = (ArrayList<String>) targetRecipe.getIngredients().clone();
            int recipeIngredientsCount = matchIngredients.size();
            matchIngredients.retainAll(ingredientsList);

            // Add recipe to list of recommended recipes if matching ingredients found
            if (!matchIngredients.isEmpty()) {
                recommendList.addRecipe(targetRecipe);

                // Calculate percentage of recipe's ingredient available to the user
                float percentMatch = 100 * (float)matchIngredients.size() / (float)recipeIngredientsCount;
                percentMatchList.add(percentMatch);
            }
        }

        ui.printRecommendedRecipes(recommendList, percentMatchList);
    }
}