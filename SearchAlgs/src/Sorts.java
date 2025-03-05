//imports

import bridges.base.LineChart;
import bridges.connect.Bridges;
import bridges.validation.RateLimitException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;


/**
 * Used to measure the time selection and bubble sort take with different size arrays and creates plot graph
 * Adjust assignment numbers to save previous Line Plots on Bridges website
 */
public class Sorts {

    /**
     * Connects to BRIDGES API using a username and API key
     * @return new Bridges Object needed for API usage
     */
    public static Bridges initializeBridges(int assignmentNum){
        Properties properties = new Properties();
        try{
            //Change file path for your application.properties file (for .gitignore)
            properties.load(new FileInputStream(".idea/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Problem with application.properties file or file may not exist.");
        }
        //Use your BRIDGES API username and API key in the second and third params
        //Adjust assignment num as needed
        Bridges bridges = new Bridges(assignmentNum, properties.getProperty("user"),properties.getProperty("key"));
        bridges.setTitle("Selection & Bubble Sort Test");
        bridges.setDescription("Used to measure the time selection and bubble sort take with different size arrays");
        return bridges;

    }
    //Initializes connection with MySQL database

    /**
     * TODO: Add implementation to make table for other users (prepare statement)
     * CREATE DATABASE SortingDB;
     * USE SortingDB;
     *
     * CREATE table SortResults (
     *     id INT AUTO_INCREMENT PRIMARY KEY,
     *     sort_type enum ('Selection', 'Bubble') not null,
     *     array_size INT NOT NULL,
     *     time_taken long NOT NULL      -- Execution time in milliseconds
     * );
     *
     */
    public static Connection initializeDataBase(){
        Properties properties = new Properties();
        try{
            //Change file path for your application.properties file (for .gitignore)
            properties.load(new FileInputStream(".idea/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Problem with application.properties file or file may not exist.");
        }
        try {
            Connection connection = DriverManager.getConnection(properties.getProperty("url"),properties.getProperty("user"),properties.getProperty("sqlpwd"));
            System.out.println("Success!");
            return connection;
        } catch (SQLException e) {
            System.out.print("Connection to SQL failed");
        }
        return null;
    }

    /**
     * Used to line plots based on data from Sort tests.
     * Would be best used for different sized arrays for same sort alg
     * A link will be put in the console to show the chart on Bridges website.
     * Use nanoToMilliseconds method for conversion
     * @param bubbleXVals array of sizes of arrays sorted (recommended)
     * @param bubbleYVals time in milliseconds taken for bubble sort(recommended)
     * @param selectionX size of array for selection sort (should be the same as "bubbleXVals" for simplicity)
     * @param selectionY time in milliseconds take for selection sort
     * @param assignmentNum assignment number for current Line Plot
     */
    public static void makeLinePlot(double[] bubbleXVals, double[] bubbleYVals, double[] selectionX, double[] selectionY,int assignmentNum){
        Bridges bridge = initializeBridges(assignmentNum);

        LineChart plot = new LineChart();
        plot.setDataSeries("Bubble Sort Algorithm Array Size vs Time taken", bubbleXVals, bubbleYVals);
        //Formatting look of plot graph. Adjust data series names if changing functionality
        plot.setTitle("Bubble & Selection Sort Algorithm Array Size vs Time taken");
        bridge.setDataStructure(plot);
        plot.setXLabel("Array Size");
        plot.setYLabel("Time Taken to sort (in Milliseconds)");
        plot.toggleMouseTrack(true);
        plot.setDataSeries("Selection Sort Algorithm Array Size vs Time taken", selectionX, selectionY);


        //Catch any problems with visualization
        try {
            bridge.visualize();
        } catch (IOException | RateLimitException e) {
            throw new RuntimeException("Problem with visualization. If \"RateLimitException\", wait some time before running again." +
                    " Else, check program and try again");
        }

    }

    /**
     * Override makeLinePlot that takes in an array of sizes to be charted
     * @param arraySizes size of arrays to be sorted and compared in visualization
     * @param assignmentNum assignment number for BRIDGES
     */
    public static void makeLinePlot(double[] arraySizes, int assignmentNum){
        //arrays to store time sort times of each sort type
        double[] bubbleTimes = new double[arraySizes.length];
        double[] selectionTimes = new double[arraySizes.length];
        Arrays.sort(arraySizes);
        String newQuery = "INSERT into SortResults (sort_type, array_size, time_taken) VALUES (?, ?, ?);";
        try (Connection db = initializeDataBase()) {
            long currentTime = 0L;
            for (int i = 0; i < bubbleTimes.length; i++) {
                currentTime = BubbleSortTest((int) arraySizes[i]);
                bubbleTimes[i] = nanoToMilliseconds(currentTime);
                long currentBubbleTime = currentTime;
                currentTime = SelectionSortTest((int) arraySizes[i]);
                selectionTimes[i] = nanoToMilliseconds(currentTime);
                long currentSelectionTime =  currentTime;

                try {
                    PreparedStatement statement = db.prepareStatement(newQuery);
                    statement.setString(1, "Bubble");
                    statement.setInt(2, (int) arraySizes[i]);
                    statement.setLong(3,currentBubbleTime);
                    statement.executeUpdate();
                    statement.setString(1,"Selection");
                    statement.setLong(3,currentSelectionTime);
                    statement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Issue with SQL database ");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL implementation failed");
        }

        makeLinePlot(arraySizes,bubbleTimes,arraySizes,selectionTimes,assignmentNum);

    }

    /**
     * returns the time elapsed in milliseconds for bubble sort (Best O(n) | Average/Worst O(n^2)
     * @param size size of array to be sorted
     * @return time elapsed in milliseconds
     */
    public static long BubbleSortTest(int size){
        //creates an array of "size" length and fills with random numbers
        int[] array = new int[size];
        fillArray(array);

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
     * Can be used with bubble and selection sort methods to make numbers more readable
     * @param nano time in nanoseconds
     * @return time in milliseconds
     */
    public static double nanoToMilliseconds(long nano){
        return nano/1000000.0;
    }

    /**
     * Fills array with of random ints 0-99
     * @param array array to be filled
     * @return array with new filled slots (for quick samples)
     */
    public static int[] fillArray(int[] array){
        for(int i = 0; i < array.length; i++){
            array[i] = (int)(Math.random()*100);
        }

        return array;
    }

    /**
     * Uses selection sort alg based on size and records time elapsed (Continuously swaps smallest element of unsorted portion of array
     * with the beginning of the unsorted portion
     * (suitable for small sets) | Best and Worst Case O(n^2)
     * @param size size of array to be sorted
     * @return time elapsed for sort in nanoseconds
     */
    public static long SelectionSortTest(int size){
        int[] array = new int[size];
        fillArray(array);
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

