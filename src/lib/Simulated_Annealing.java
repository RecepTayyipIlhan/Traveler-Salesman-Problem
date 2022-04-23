/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

public class Simulated_Annealing {

    public static Solution simulatedAnnealing(List<City> cities) throws CloneNotSupportedException {
        double T_0 = 10;//Başlangıç Sıcaklığı
        double T_current = T_0;//Mevcut sıcaklık 
        int T_final = 0; //Sonlandırma sıcaklığı
        int max_inner_loop = 0; //maximum inner loop
        //her inner loop sonunda max sıcaklığı düşürürüz 
        double cooling_rate = 0.95;

        Solution s = new Solution(cities.size());
        s.createRndVisitOrder(cities, 0, (cities.size() - 1));

        Solution bestSolution = s;

        while (T_current > T_final) {
            int inner_iter = 0;

            while (inner_iter < max_inner_loop) {
                List<Solution> neighbors = Utils.getNeighbors(s);
                Collections.shuffle(neighbors);

                Solution s_prime = neighbors.get(0);
                float delta = s_prime.getObj() - s.getObj();
                if (delta > 0) {
                    s = s_prime;
                    if (s_prime.getObj() > bestSolution.getObj()) {
                        bestSolution = s_prime;//s_prime şimdi en iyi çözümümüz
                    } else {
                        //exp hesaplaması yapılır kötü çözümlerin hangi olasılıkla kabul ediliceği olasılığı hesaplanır

                        if (Math.random() < Math.exp(delta / T_current)) {
                            s = s_prime;
                        }
                    }

                }
                inner_iter++;

            }
            T_current = T_current - (T_current * cooling_rate);
            System.out.println("Temperature " + T_current + ", Best Solution: " + bestSolution.getObj());

        }

        return bestSolution;

    }

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        List<City> citys = Utils.readFile("C:\\Users\\ilhan\\OneDrive\\Belgeler\\NetBeansProjects\\SimulatedAnnealing\\src\\lib\\tsp_70_1.txt");
        Solution besSolution = simulatedAnnealing(citys);
        besSolution.printVisitingOrder();
    }

}
