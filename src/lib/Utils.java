package lib;

//Dosya Okuma İşlemleri yapılır
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    // We need to write a function that will read the file.
    // We will read the given file and store data in City List.
    public static List<City> readFile(String fileName) throws FileNotFoundException {
        Scanner reader = new Scanner(new File(fileName));

        int nbrLine = 0;
        int solutionNumber = 0;
        List<City> cityList = new ArrayList<>();

        // The loop will run until the lines in the file are finished.
        while (reader.hasNext()) {
            String nextLine = reader.nextLine(); // Get Next Line

            if (nbrLine == 0) {
                nbrLine++;
                continue;
            }

            // Split Line
            String[] coordinates = nextLine.replace("\n", "").split(" ");
            float xCoord = Float.parseFloat(coordinates[0]); // Get X Coordinate
            float yCoord = Float.parseFloat(coordinates[1]); // Get Y Coordinate

            // Create the city based on the information read from the file.
            cityList.add(new City("" + solutionNumber++, xCoord, yCoord)); // Add city
        }

        return cityList; // Return read cities from given tsp file
    }

    //Euclidean Distance hesaplama methodu
    public static float euclideanDistance(City cityOne, City cityTwo) {

        float xDistance = (float) Math.pow(cityOne.getX_coord() - cityTwo.getX_coord(), 2);

        float yDistance = (float) Math.pow(cityOne.getY_coord() - cityTwo.getY_coord(), 2);

        return (float) Math.sqrt(xDistance + yDistance);

    }

    public static List<Solution> getNeighbors(Solution givenSolution) throws CloneNotSupportedException {
        // The method of generating the neighbors of the relevant solution.

        List<Solution> neighbors = new ArrayList<>();

        // Creating neighbors by navigating each item, changing the relevant index and keeping the others constant.
        for (int i = 0; i < givenSolution.getVisitingOrder().size(); i++) {
            Solution temp = (Solution) givenSolution.clone();
            City selectedCity = temp.getVisitingOrder().get(i);
            int isSelected = selectedCity.getIsSelected();
            if (isSelected == 1) {
                isSelected = 0;
            } else {
                isSelected = 1;
            }

            selectedCity.setIsSelected(isSelected);
            temp.calculateObjective();
            neighbors.add(temp);
        }

        return neighbors;

    }

}
