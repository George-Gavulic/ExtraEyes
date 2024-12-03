import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetIngredients {
    List<String> ingredientList;
    List<String> allergenList;

    

    public List<String> getIngredients() {
        return ingredientList;
    }
    public List<String> getAllergens() {
        return allergenList;
    }
    


    public void findIngredients(String barcode) {
        try {
            // Connect to the webpage
            String urlStart = "https://world.openfoodfacts.org/product/";
            String urlEnd = barcode;//"0030000065310";  ////barcode; //
            String barcodeurl = urlStart + urlEnd;

            Document doc = Jsoup.connect(barcodeurl).get();
 
            Element ingredientsDiv = doc.select("#panel_ingredients_content").first();

            if (ingredientsDiv != null) {
                // Select the nested div with class 'panel_text' for ingredients
                Element panelText = ingredientsDiv.selectFirst("div.panel_text");
                if (panelText != null) {
                    
                    String ingredientsText = panelText.text();
                    //System.out.println("Ingredients: " + ingredientsText);

                    ingredientList = processIngredients(ingredientsText);

                    // System.out.println("\nIngredients List:");
                    // for (String ingredient : ingredientList) {
                    //     //System.out.println(ingredient);
                    // }
                } else {
                    System.out.println("Panel text not found for ingredients!");
                }

                // Select the div with allergens (if present)
                Element allergenDiv = ingredientsDiv.select("div.panel_text:contains(Allergens:)").first();
                if (allergenDiv != null) {
                    String allergenText = allergenDiv.text();
                    //System.out.println("\nAllergen Information: " + allergenText);

                    // Process allergens and print them
                    allergenList = processAllergens(allergenText);
                    // System.out.println("\nAllergen List:");
                    // for (String allergen : allergenList) {
                    //     System.out.println(allergen);
                    // }
                } else {
                    System.out.println("Allergen information not found!");
                }
            } else {
                System.out.println("Ingredients content not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to process ingredients list
    public static List<String> processIngredients(String ingredientsText) {
        List<String> ingredientList = new ArrayList<>();
        
        // Split ingredients by commas outside parentheses
        //String regex = ",(?![^()]*\\))";
        //String[] ingredients = ingredientsText.split(regex);
        String[] ingredients = ingredientsText.split(",");

        for (String ingredient : ingredients) {
            ingredient = ingredient.trim();
            if (!ingredient.isEmpty()) {
                ingredientList.add(ingredient);
            }
        }

        // Clean up ingredients by removing parentheses and extra spaces
        for (int i = 0; i < ingredientList.size(); i++) {
            String ingredient = ingredientList.get(i);
            ingredient = ingredient.replaceAll("\\(([^)]+)\\)", "$1"); // Clean parentheses
            ingredientList.set(i, ingredient.trim());
        }

        return ingredientList;
    }

    // Method to process allergens (assuming it starts with "Allergens:" and can have multiple items)
    public static List<String> processAllergens(String allergenText) {
        List<String> allergenList = new ArrayList<>();
    
        // Remove the "Allergens:" prefix, if it exists
        if (allergenText.toLowerCase().startsWith("allergens:")) {
            allergenText = allergenText.substring(10).trim(); // Remove "Allergens:" and trim any leading space
        }
    
        // Split allergens by commas or newlines
        String[] allergens = allergenText.split(",|\\s*\\n\\s*");
    
        for (String allergen : allergens) {
            allergen = allergen.trim();
            if (!allergen.isEmpty()) {
                allergenList.add(allergen);
            }
        }
    
        return allergenList;
    }
}
