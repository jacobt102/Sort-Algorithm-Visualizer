//imports

import bridges.base.LineChart;
import bridges.connect.Bridges;
import bridges.validation.RateLimitException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Used to measure the time certain sorting take with different size arrays
 *
 */
public class Sorts {

    /**
     * Connects to BRIDGES API using a username and API key
     * @return new Bridges Object
     */
    public static Bridges initializeBridges(){
        Properties properties = new Properties();
        try{
            //Change file path for your application.properties file (for .gitignore)
            properties.load(new FileInputStream(".idea/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Problem with application.properties file OR file may not exist.");
        }
        //Use your BRIDGES API username and API key in the second and third params
        Bridges bridges = new Bridges(1, properties.getProperty("user"),properties.getProperty("key"));
        bridges.setTitle("Sort Test");
        bridges.setDescription("Used to measure the time certain sorting take with different size arrays");

        return bridges;

    }

    /**
     * Used to line plots based on data from Sort tests.
     * Would be best used for different sized arrays for same sort alg
     * A link will be put in the console to show the chart
     * @param bubbleXVals size of array made (recommended)
     * @param bubbleYVals time in milliseconds taken for bubble sort(recommended)
     * @param selectionX size of array for selection sort (should be the same as "bubbleXVals" for simplicity)
     * @param selectionY time in milliseconds take for selection sort
     */
    public static void makeLinePlot(double[] bubbleXVals, double[] bubbleYVals, double[] selectionX, double[] selectionY){
        Bridges bridge = initializeBridges();
        LineChart plot = new LineChart();
        plot.setDataSeries("Bubble Algorithm Array Size vs Time taken", bubbleXVals, bubbleYVals);
        //Formatting look of plot
        plot.setTitle("Sort Algorithm Array Size vs Time taken");
        bridge.setDataStructure(plot);
        plot.setXLabel("Array Size");
        plot.setYLabel("Time Taken to sort (in Milliseconds)");
        plot.toggleMouseTrack(true);
        plot.setDataSeries("Selection Algorithm Array Size vs Time taken", selectionX, selectionY);


        //Catch any problems with visualization
        try {
            bridge.visualize();
        } catch (IOException | RateLimitException e) {
            throw new RuntimeException("Problem with visualization. Check program and try again");
        }

    }

    /**
     * returns the time elapsed in milliseconds for bubble sort (Best O(n) | Average/Worst O(n^2)
     * @param array array to be sorted
     * @return time elapsed in milliseconds
     */
    public static long BubbleSortTest(int[] array){

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
     * Fills array with of random ints 1-100
     * @param array array to be filled
     * @return array with new filled slots (for quick samples)
     */
    public static int[] fillArray(int[] array){
        for(int i = 0; i < array.length-1; i++){
            array[i] = (int)(Math.random()*100);
        }
        return array;
    }

    /**
     * Use selection sort alg based on size and records time elapsed (Continuously swaps smallest element of unsorted portion of array
     * with the beginning of the unsorted portion
     * (suitable for small sets) | Best and Worst Case O(n^2)
     * @return time elapsed for sort in nanoseconds
     */
    public static long SelectionSortTest(int[] array){
        long startTime = System.nanoTime();
        for(int j = 0; j < array.length; j++) { //j=beginning of unsorted portion
            int lowestIndex = j;

            for (int i = j; i < array.length; i++) {
                if (array[i] < array[lowestIndex]) {
                    lowestIndex = i;
                }
            }
            if(lowestIndex!=j){
                int temp = array[lowestIndex];
                array[lowestIndex] = array[j];
                array[j] = temp;
            }
        }
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

}

