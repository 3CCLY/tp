package ymfc.recipelist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import ymfc.recipe.Recipe;


public class RecipeListTest {

    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;

    private RecipeList testRecipeList;

    @BeforeEach
    void setUp() {
        // Sample ingredients and steps
        ArrayList<String> ingredients1 = new ArrayList<>();
        ingredients1.add("Rice");
        ArrayList<String> ingredients2 = new ArrayList<>();
        ingredients2.add("Flour");
        ArrayList<String> ingredients3 = new ArrayList<>();
        ingredients3.add("Potatoes");

        ArrayList<String> steps1 = new ArrayList<>();
        steps1.add("Boil rice in water for 15min.");
        ArrayList<String> steps2 = new ArrayList<>();
        steps2.add("Add yeast and bake for 30min");
        ArrayList<String> steps3 = new ArrayList<>();
        steps3.add("Add butter and salt and mash the potatoes.");

        recipe1 = new Recipe("White Rice", ingredients1, steps1);
        recipe2 = new Recipe("Plain Bread", ingredients2, steps2);
        recipe3 = new Recipe("Mash Potatoes", ingredients3, steps3);
    }

    @Test
    public void getRecipes_emptyRecipeList_returnEmptyArrayList() {
        assertEquals(new ArrayList<Recipe>(), new RecipeList().getRecipes());
    }

    @Test
    public void addRecipe_addingMultipleRecipes_success() {
        testRecipeList = new RecipeList();
        assertEquals(0, testRecipeList.getCounter());

        testRecipeList.addRecipe(recipe1);
        assertEquals(1, testRecipeList.getCounter());

        testRecipeList.addRecipe(recipe2);
        assertEquals(2, testRecipeList.getCounter());

        testRecipeList.addRecipe(recipe3);
        assertEquals(3, testRecipeList.getCounter());
    }

    @Test
    public void removeRecipe_deletingMultipleRecipes_success() {
        testRecipeList = new RecipeList();
        testRecipeList.addRecipe(recipe1);
        testRecipeList.addRecipe(recipe2);
        testRecipeList.addRecipe(recipe3);

        testRecipeList.removeRecipe(0);
        assertEquals(2, testRecipeList.getCounter());
        assertSame(recipe3, testRecipeList.getRecipe(1));

        testRecipeList.removeRecipe(1);
        assertEquals(1, testRecipeList.getCounter());
        assertSame(recipe2, testRecipeList.getRecipe(0));
    }

    @Test
    public void removeRecipe_inputOutOfBounds_exceptionThrown() {
        testRecipeList = new RecipeList();
        testRecipeList.addRecipe(recipe1);
        testRecipeList.addRecipe(recipe2);
        testRecipeList.addRecipe(recipe3);

        assertThrows(IndexOutOfBoundsException.class, () -> testRecipeList.removeRecipe(-1));
    }


    @Test
    public void removeRecipeByName_deletingMultipleRecipes_success() {
        testRecipeList = new RecipeList();
        testRecipeList.addRecipe(recipe1);
        testRecipeList.addRecipe(recipe2);
        testRecipeList.addRecipe(recipe3);

        testRecipeList.removeRecipeByName(recipe2.getName());
        assertEquals(2, testRecipeList.getCounter());
        assertSame(recipe3, testRecipeList.getRecipe(1));

        testRecipeList.removeRecipeByName(recipe1.getName());
        assertEquals(1, testRecipeList.getCounter());
        assertSame(recipe3, testRecipeList.getRecipe(0));
    }
}
