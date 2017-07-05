package crafthelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Data {

	private char[][] ingredients;
	private Map<String, Object> recipe;
	private Map<String, Map<String, Object>> keys;
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public Data() {
		ingredients = new char[3][3];
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				ingredients[i][j] = ' ';
		recipe = new HashMap<>();
		keys = new HashMap<>();
		recipe.put("type", "minecraft:crafting_shaped");
	}
	
	/**
	 * Sets the item used at the given slot of the crafting recipe
	 * @param pos the position of the slot
	 * @param id the id of the item
	 */
	public void setIngredient(int pos, String id) {
		setIngredient(pos/3, pos%3, id);
	}
	
	public void setIngredient(int x, int y, String id) {
		
		int domainSeparation = id.indexOf(':');
		Character key = id.charAt(0);
		
		if(domainSeparation >= 0)
			key = id.charAt(domainSeparation+1);
		else
			id = "minecraft:" + id;
		
		Map<String, Object> item = new HashMap<>();
		item.put("item", id);
		
		while(keys.containsKey(key) && !keys.get(key).equals(item)) {
			key++;
		}
		
		key = Character.toUpperCase(key);
		
		keys.put(key.toString(), item);
		this.ingredients[x][y] = key;
		clearUnusedKeys();
		System.out.println(keys);
	}
	
	public String getIngredient(int pos) {
		Map<String, Object> ingredient = keys.get(String.valueOf(ingredients[pos/3][pos%3]));
		return ingredient == null ? "" : (String) ingredient.get("item");
	}
	
	/**
	 * Sets the resulting item of this recipe
	 * @param s the id of the item
	 */
	public void setResult(String s) {
		Map<String, Object> result = new HashMap<>();
		result.put("item", s);
		recipe.put("result", result);
	}
	
	public String getResult() {
		Map<String, Object> result = (Map<String, Object>) recipe.get("result");
		return result == null ? "" : (String) result.get("item");
	}
	
	/**
	 * Toggles whether this recipe is shapeless
	 * @param shapeless
	 */
	public void setShapeless(boolean shapeless) {
		recipe.put("type", shapeless ? "minecraft:crafting_shapeless" : "minecraft:crafting_shaped");
	}
	
	/**
	 * Readies the recipe for saving
	 */
	private void compileRecipe() {
		clearUnusedKeys();
		List<String> pattern = new ArrayList<>();
		for(int i = 0; i < ingredients.length; i++)
			pattern.add(new String(ingredients[i]));
		recipe.put("pattern", pattern);
		recipe.put("key", keys);
		System.out.println(recipe);
	}
	
	/**
	 * Saves the recipe to a json file
	 * @param f the file in which the recipe should be saved
	 */
	public void saveRecipe(File f) {
		compileRecipe();
		try (FileWriter w = new FileWriter(f)) {
			GSON.toJson(recipe, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes all character keys that are not used in this recipe
	 */
	private void clearUnusedKeys() {
		Iterator<String> iterator = keys.keySet().iterator();
		while(iterator.hasNext()) {
			String c = iterator.next();
			boolean contained = false;
			for(int i = 0; i < ingredients.length; i++) {
				for(int j = 0; j < ingredients[i].length; j++) {
					if(c.equals(String.valueOf(ingredients[i][j]))) {
						contained = true;
						break;
					}
				}
			}
			if(!contained)
				iterator.remove();
		}
	}
	
}
