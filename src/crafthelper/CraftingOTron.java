package crafthelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CraftingOTron {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static void main(String[] args) {
		Data data = new Data();
		Gui gui = new Gui(data);
	}

}
