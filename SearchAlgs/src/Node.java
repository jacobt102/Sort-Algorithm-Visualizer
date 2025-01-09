import java.util.*;

/**
 * Used to measure the time certain sorting take with different size sets
 */
public class Searches {
    private int size;
    public Searches(int size){
        this.size=size;
    }

    /**
     * returns the time elapsed in milliseconds for bubble sort (Best O(n) | Average/Worst O(n^2)
     * @param size size of test array to be sorted
     * @return time elapsed in milliseconds
     */
    public long BubbleSortTest(int size){
        int[] array = new int[size];
        for(int i = 0; i < array.length; i++){
            array[i] = (int)(Math.random()*100);

        }
        long startTime = System.nanoTime();
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
    public long SelectionSortTest(){

    }


    public static void main(String[] args) {
        Searches searches = new Searches(1);
        System.out.println(searches.BubbleSortTest(10));
    }
}

