import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {

    private static final String DATABASE_FILE = "DataBase.txt";

    private static List<String> ingredientsInDatabase = new LinkedList<>();
    private static List<String> colorsInDatabase = new LinkedList<>();
    private static List<String> doisInDatabase = new LinkedList<>();

    static {
        loadDatabase();
    }

    // Add a new ingredient, color, and DOI
    public static void addIngredient(String ingredient, String color, String doi) {
        ingredientsInDatabase.add(ingredient);
        colorsInDatabase.add(color);
        doisInDatabase.add(doi);
        saveDatabase();
    }

    // Save the current lists to a file
    private static void saveDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
            for (int i = 0; i < ingredientsInDatabase.size(); i++) {
                writer.write(ingredientsInDatabase.get(i) + "," +
                             colorsInDatabase.get(i) + "," +
                             doisInDatabase.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the lists from a file
    private static void loadDatabase() {
        File file = new File(DATABASE_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    ingredientsInDatabase.add(parts[0]);
                    colorsInDatabase.add(parts[1]);
                    doisInDatabase.add(parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Accessors
    public static boolean isIngredientInDatabase(String ingredient) {
        return ingredientsInDatabase.contains(ingredient.toLowerCase());
    }

    // Method to get the color for an ingredient
    public static String getColorForIngredient(String ingredient) {
        int index = ingredientsInDatabase.indexOf(ingredient.toLowerCase());
        return (index != -1) ? colorsInDatabase.get(index) : "transparent";
    }

    // Method to get the DOI for an ingredient
    public static String getDoiForIngredient(String ingredient) {
        int index = ingredientsInDatabase.indexOf(ingredient.toLowerCase());
        return (index != -1) ? doisInDatabase.get(index) : null;
    }
}
