package lib;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solution {

    // Solution class store each traversal ordering, tsp cycle

    private float obj; // Objective Value of Solution
    private int numberCity; // Number of city
    private List<City> visitingOrder; // Store Visit Order (Each item must be City)

    // Default Constructor

    public Solution(int numberCity) {
        this.numberCity = numberCity;
        visitingOrder = new ArrayList<>();
    }



    // Create Random Visit Order
    // Params:
    // Cities: Each location, city in tsp file
    // startPoint: Index of starting point
    // endPoint: Index of end point

    public void createRndVisitOrder(List<City> cities, int startPoint, int endPoint) {

        // First city must be in first index and the last city must be in last index.
        // That's why we didn't add them to the list.
        for (int i = 0; i < cities.size(); i++) {
            if (i == startPoint || i == endPoint) {
                continue;
            } else
                // We add each city sequentially except starting and ending points.
                visitingOrder.add(cities.get(i));
        }

        Collections.shuffle(visitingOrder); // Randomly shuffle the location of the cities.
        visitingOrder.add(0, cities.get(startPoint)); // Add start city to visiting order
        visitingOrder.add(visitingOrder.size(), cities.get(endPoint));
    }

    public List<City> createRandomNeighbour() {
        List<City> newCities = new ArrayList<>(visitingOrder); // create a copy of cities List
        // Pick two random number between 0 and size of the cities List
        // Create two random number without replacement
        int[] randIndexes = new Random().ints(1, newCities.size() - 1).distinct().limit(2).toArray();
        Collections.swap(newCities, randIndexes[0], randIndexes[1]); // swap random two cities in newCities List
        return newCities; // return the newCities
    }

    // Calculate objective value of solution
    public void calculateObjective() {
        for (int i = 0; i < visitingOrder.size() - 1; i++) {
            City cityOne = visitingOrder.get(i);
            City cityTwo = visitingOrder.get(i + 1);
            obj += Utils.euclideanDistance(cityOne, cityTwo);
        }

        // Calculate Distance Between Start and End point, and then add this value to objective
        obj += Utils.euclideanDistance(visitingOrder.get(0), visitingOrder.get(visitingOrder.size() - 1));

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // To operate on a copy of the solution without changing the original solution.
        Solution solution = new Solution(numberCity);
        solution.getVisitingOrder().clear();

        for (City city : visitingOrder) {
            City new_city = new City(city.getName(), city.getX_coord(), city.getY_coord());
            new_city.setIsSelected(city.getIsSelected());
            solution.getVisitingOrder().add(new_city);
        }
        return solution;
    }

    public void printVisitingOrder() {
        for (City city : visitingOrder) {
            System.out.print(city.getName() + " ");
        }
    }



    public List<City> getVisitingOrder() {
        return visitingOrder;
    }

    public float getObj() {
        return obj;
    }

}
