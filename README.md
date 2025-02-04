# Sorting Algorithms Visualization with BRIDGES API
This project implements and compares sorting algorithms such as **Selection Sort** and **Bubble Sort**, measuring their performance for different input sizes. The program utilizes the [BRIDGES API](http://bridgesuncc.github.io/) to create visualizations of the sorting performances.
## Features
- **Sorting Algorithm Implementation**:
    - Bubble Sort
    - Selection Sort

- **Performance Measurement**:
    - Measures execution time of each algorithm for arrays of different sizes.
    - Tracks and converts execution times from nanoseconds to milliseconds for better readability.

- **Visualization**:
    - Uses the BRIDGES API to plot and visualize the relationship between input array sizes and sorting time.
    - Generates an interactive plot with detailed axes (Array Size vs. Time in Seconds).

## Prerequisites
1. **Java Setup**:
    - Ensure you have **Java 8 or later** installed.
    - Ideally, use an IDE such as [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/).

2. **BRIDGES API Account**:
    - Sign up for a free BRIDGES account at [BRIDGES](http://bridgesuncc.github.io/).
    - Obtain your BRIDGES username and API key from your account.
    - Download the latest .jar file for [BRIDGES](http://bridgesuncc.github.io/). 

## Setting Up the Project
1. Clone or download this repository to your computer.
2. Open the project in your IDE.
3. **Configure the BRIDGES API**:
    - Create a file named `application.properties` in the root directory of your project (or adjust the existing `.idea/application.properties` path in `initializeBridges()`).
    - Add the following lines to `application.properties`:
``` properties
     user=YOUR_BRIDGES_USERNAME
     key=YOUR_BRIDGES_API_KEY
```
Replace `YOUR_BRIDGES_USERNAME` and `YOUR_BRIDGES_API_KEY` with your actual credentials from the BRIDGES website.
1. Adjust the path to your `application.properties` file if it's located in a different directory. Update the line in the `initializeBridges` method in the `Sorts` class:
``` java
   properties.load(new FileInputStream("path/to/application.properties"));
```
1. Build the project to ensure no missing dependencies.

## Running the Project
1. Choose the desired sorting algorithm to measure and visualize:
    - Bubble Sort: `BubbleSortTest`
    - Selection Sort: `SelectionSortTest`

2. Create a `main` method to use `createLinePlot` as needed. 
3. After a successful run, **a URL will be printed in the console**. Open the URL in your browser to view the interactive graph plotting the performance of the sorting algorithm.

## Example Usage
``` java
// Initialize an array and measure sorting time using Bubble Sort
int[] array = new int[100];
long bubbleSortTime = BubbleSortTest(array);
System.out.println("Bubble Sort Time (nanoseconds): " + bubbleSortTime);

// Plot performance data
double[] xVals = {10, 50, 100}; // Different array sizes
double[] yVals = {0.005, 0.013, 0.022}; // Example times in seconds
makeLinePlot(xVals, yVals);
```

**BRIDGES API Visualization**: A line chart showing:
- **X-Axis**: Array Size
- **Y-Axis**: Execution Time (in seconds)

## Notes
1. **Limits While Visualizing**:
    - The BRIDGES API might rate-limit requests if they are too frequent. Handle exceptions such as `RateLimitException`.

2. **Algorithm Performance**:
    - Sorting algorithms in this project are intended for demonstration purposes and may not be efficient for large datasets (e.g., Selection Sort and Bubble Sort both have **O(n²)** time complexity).
