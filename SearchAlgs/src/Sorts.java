/**
 * Used to measure the time certain sorting take with different size arrays
 */
public class Sorts {
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
    }


    public static void main(String[] args) {

    }
}

