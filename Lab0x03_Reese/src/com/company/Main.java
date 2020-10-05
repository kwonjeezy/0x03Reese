package com.company;

import java.util.Arrays;
import java.util.Random;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
public class Main {

    public static int number = 249932;
    public static int maximum = 100000;
    //creates a randomized list with positive and negative numbers
    public static int[] genList(int length, int val){
        //creates the list[] to be returned
        int[] list = new int[length];
        //creates the minimum value needed which is the negative
        //equivalent of the value acquired
        int minVal = -val;
        int num;
        //for loop to add a new random number for each value in the
        //list[]
        for(int i = 0;i<length;i++){
            //creates a random number generator
            Random r = new Random();
            //equation needed to create the random values
            num = r.nextInt(val - minVal +1) +minVal;
            //inputs the random variable in the the list[].
            list[i]= num;
        }
        //returns the list[]
        return list;
    }

    public static int brute(int[] list){
        int count =0;
        //creates the variables for the 3 for loops needed to brute force the solution.
        int bruteOne, bruteTwo, bruteThree;
        //creates the length to be used for the for loops
        int arrayLen = list.length;

        //first for loop, will look @ the arrayLen -2(because the loop will iterate two more times)
        for(bruteOne = 0; bruteOne<arrayLen-2;bruteOne++){
            //nested for-loop where brute 2 will be the value of brute 1 to 1, and will end one value short
            // of the array length.
            for(bruteTwo = bruteOne+1; bruteTwo<arrayLen-1;bruteTwo++){
                //similar to brute 2, but will end on the actual array length.
                for(bruteThree = bruteTwo+1;bruteThree<arrayLen;bruteThree++){
                    //if statement check the values of brute 1 - 3, if = 0, then
                    //will add to the count.
                    if(list[bruteOne]+list[bruteTwo]+list[bruteThree]==0){
                        count++;
                    }
                }

            }

        }
        //returns the count.
        return count;
    }
    public static int faster(int[] list){
    int count =0;
    //sorts the array
    Arrays.sort(list);
    int length = list.length;

    int fasterOne, fasterTwo;
    //creates a nest for loop to help look through all available array variables
    for(fasterOne =0; fasterOne<length;fasterOne++){
        for(fasterTwo =fasterOne+1;fasterTwo<length;fasterTwo++){
            //using binary search will look to see if there is a 3 sum in the array
            if ( Arrays.binarySearch(list,-list[fasterOne]-list[fasterTwo])>fasterTwo){
                count++;
            }
        }
    }
    return count;
    }
    public static int fastest(int[] list) {
        //getting necessary variables
    int length = list.length;
        Arrays.sort(list);
        int count = 0;
//one for loop
        for(int i = 0; i<length-1; i++){
            //will be "splitting" the array in half,
            //looking for variables on either side.
            //Only works when the array is sorted.
            int lower = i+1;
            int upper= length-1;

            while(lower<upper){
                //if the variables at upper/lower/i == 0,
                //then list will iterate and lower and count will increase by 1
                //upper will decrease by one
                if(list[i]+list[upper]+list[lower]==0){
                    upper--;
                    lower++;
                    count++;
                }
                //if it is less than 0 then that means that the lowest we are at a point
                //where the lowest value will not satisfy the requirements,
                //so lower will increase by one
                else if (list[i]+list[upper]+list[lower]<0){
                    lower++;
                }
                //similar to else if statement, the value is too high, so the upper limit will decrease by one and reiterate.
                else{
                    upper--;
                }
            }
        }
    return count;
    }
//view CPU time.
    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
    }
    public static void main(String[] args) {

        //creating necessary variables.
        int [] randomList;
        double doubleRatio;
        double expDoubleRatio;
       long start, end, run;
       long pBrute= 0;
       long pFaster =0;
       long pFastest = 0;


       boolean mTime = false;
       long mTimeReached = 10000000L;

       System.out.printf("%12s%24s%19s%19s%24s%19s%19s%24s%19s%19s\n", "", "Brute", "", "", "Faster ", "", "", "Fastest", "", "");
        System.out.printf("%12s%24s%19s%19s%24s%19s%19s%24s%19s%19s\n","N", "Time", "DblRatio", "ExpDblRatio", "Time", "DblRatio", "ExpDblRatio", "Time", "DblRatio", "ExpDblRatio");

        for(int i = 4;i<= number;i=i*4) {
            System.out.printf("%12s", i);
            randomList = genList(i, maximum);

            start =getCpuTime();
            brute(randomList);
            end=getCpuTime();
            run = end-start;
            System.out.printf("%24s", run);

            if(pBrute==0){
                System.out.printf("%19s%19s", "N/a","N/a");
            }
            else{
                doubleRatio = (double)run/pBrute;
                expDoubleRatio = (double)Math.pow(run,3)/Math.pow(pBrute,3);
                System.out.printf("%19f%19f", doubleRatio,expDoubleRatio);
            }
            pBrute = run;
            if(run>maximum){
                mTime =true;
            }

            start =getCpuTime();
            faster(randomList);
            end=getCpuTime();
            run = end-start;
            System.out.printf("%24s", run);

            if(pBrute==0){
                System.out.printf("%19s%19s", "N/a","N/a");
            }
            else{
                doubleRatio = (double)run/pFaster;
                expDoubleRatio = (Math.pow(run,2)*Math.log((double)run))/(Math.pow(pFaster,2)*Math.log((double)pFaster));
                System.out.printf("%19f%19f", doubleRatio,expDoubleRatio);
            }
            pFaster = run;
            if(run>maximum){
                mTime =true;
            }

            start =getCpuTime();
            fastest(randomList);
            end=getCpuTime();
            run = end-start;
            System.out.printf("%24s", run);

            if(pBrute==0){
                System.out.printf("%19s%19s", "N/a","N/a");
            }
            else{
                doubleRatio = (double)run/pFastest;
                expDoubleRatio = Math.pow(run,2)/Math.pow(pFastest,2);
                System.out.printf("%19f%19f", doubleRatio,expDoubleRatio);
            }
            pFaster = run;
            if(run>maximum){
                mTime =true;
            }
            System.out.println("");

        }
    }
}
