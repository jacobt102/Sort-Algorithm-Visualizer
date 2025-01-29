import bridges.base.LineChart;
import bridges.connect.Bridges;
import bridges.validation.RateLimitException;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

/**
 * Used to measure the time certain sorting take with different size arrays
 */
public class Sorts {

    public static Bridges initializeBridges(){
        Properties properties = new Properties();
        try{
            //Change file path for your application.properties file (for .gitignore)
            properties.load(new FileInputStream(".idea/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Problem with application.properties file OR file may not exist.");
        }
        Bridges bridges = new Bridges(1, properties.getProperty("user"),properties.getProperty("key"));
        bridges.setTitle("Sort Test");
        bridges.setDescription("Used to measure the time certain sorting take with different size arrays");
        return bridges;

    }

    /**
     * Could be used to line plots based on data from Sort tests.
     * Would be best used for different sized arrays for same sort alg
     * @param xVals size of array made (recommended)
     * @param yVals time in milliseconds taken (recommended)
     */
    public static void makeLinePlot(double[] xVals, double[] yVals){
        Bridges bridge = initializeBridges();
        LineChart plot = new LineChart();
        plot.setDataSeries("Sort Algorithm Array Size vs Time taken",xVals,yVals);
        //
        plot.setTitle("Sort Algorithm Array Size vs Time taken");
        bridge.setDataStructure(plot);

        //For any problems with visualization
        try {
            bridge.visualize();
        } catch (IOException | RateLimitException e) {
            throw new RuntimeException("Problem with visualization.");
        }

    }

    /**
     * returns the time elapsed in milliseconds for bubble sort (Best O(n) | Average/Worst O(n^2)
     * @return time elapsed in milliseconds
     */
    public static long BubbleSortTest(int[] array){
        //Fills with random ints 1-100
        for(int i = 0; i < array.length; i++){
            array[i] = (int)(Math.random()*100);

        }
        long startTime = System.nanoTime(); //Logs start time
        for(int i = 1; i < array.length; i++ ){
            for(int j = 0; j < array.length; j++){
                if(array[i]<array[j]){
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        long endtime = System.nanoTime();
        return endtime-startTime;
    }

    /**
     * Converts nanoseconds to milliseconds
     * @param nano time in nanoseconds
     * @return time in milliseconds
     */
    public static double nanoToMilliseconds(long nano){
        return nano/1000000.0;
    }

    /**
     * Use selection sort alg based on size and records time elapsed
     * (suitable for small sets) | Best and Worst Case O(n^2)
     * @return time elapsed for sort in nanoseconds
     */
    public static long SelectionSortTest(int[] array){
        for(int j = 0; j < array.length; j++) { //j=beginning of unsorted portion
            int lowestIndex = 0;

            for (int i = 0; i < array.length; i++) {
                if (array[i] < array[lowestIndex]) {
                    lowestIndex = i;
                }
            }
        }
        return 1;
    }


    public static void main(String[] args){
        double[] exampleX = new double[]{1,3,6};
        double[] exampleY = new double[]{3,7,10};
        makeLinePlot(exampleX,exampleY);
    }
}

