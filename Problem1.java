import java.io.*;
import java.util.*;

// find all primes between 1 and 10^8, dividing the task among 8 different streams of computation
// print information about the program and the primes found to "primes.txt"
class Problem1 
{
    // build a list of prime numbers using multiple threads & print out related information
    public static void main(String[] args) 
    {
        long startTime = System.currentTimeMillis();
        final int maxNum = (int) Math.pow(10, 8);
        final int numThreads = 8;
        List<Thread> threads = new ArrayList<>();

        // store prime numbers found by threads
        List<Integer> primes = Collections.synchronizedList(new ArrayList<>());

        // calculate range for each thread
        final int range = (maxNum - 1) / numThreads + 1;
        final int[] starts = new int[numThreads];
        final int[] ends = new int[numThreads];

        for (int i = 0; i < numThreads; i++) 
        {
            starts[i] = i * range + 1;
            ends[i] = Math.min((i + 1) * range, maxNum);
        }

        // create & start threads
        for (int i = 0; i < numThreads; i++) 
        {
            // calculate current thread's range
            final int start = starts[i];
            final int end = ends[i];
            
            // define a thread
            Thread thread = new Thread(() -> 
            {
                for (int j = start; j <= end; j++) 
                {
                    if (isPrime(j)) 
                    {
                        // add prime to primes list, ensuring only one thread execution at a time
                        synchronized (primes)
                        {
                            primes.add(j);
                        }
                    }
                }
            });

            // start thread execution & store it in a thread list
            thread.start();
            threads.add(thread); 
        }

        // make a copy of primes list to avoid ConcurrentModificationException error
        List<Integer> finalPrimes = new ArrayList<>(primes);
        Collections.sort(finalPrimes);

        long endTime = System.currentTimeMillis();
        writeResults(finalPrimes, startTime, endTime, "primes.txt");
    }

    // check if a number is prime
    // return true if 2, 3, or another number that is not divisible by any other number than itself
    // return false if 0, 1, an even number greater than 2, or if odd & divisible by another number
    public static boolean isPrime(int num)
    {
        if (num < 2) return false; 
        if (num == 2 || num == 3) return true;
        if (num % 2 == 0) return false;

        int sqrtNum = (int) Math.sqrt(num);
        for (int i = 3; i <= sqrtNum; i += 2)
        {
            if (num % i == 0) return false; 
        }

        return true;
    }

    // print out execution time, total primes found, sum of all primes, and the top 10 max primes
    public static void writeResults(List<Integer> primes, long startTime, long endTime, String file)
    {
        long runtime = endTime - startTime;
        int size = primes.size();

        long sum = 0;
        for (Integer prime : primes) 
            sum += prime;

        // get top 10 primes from primes list, starting from 0 if there's less than 10 primes
        List<Integer> top10 = new ArrayList<>();
        for (int i = size - Math.min(10, size); i < size; i++)
            top10.add(primes.get(i));

        try (FileWriter writer = new FileWriter(file))
        {
            writer.write("Execution Time: " + runtime);
            writer.write("\nTotal Primes: " + size);
            writer.write("\nSum of Primes: " + sum);

            writer.write("\n\nTop 10 Maximum Primes (Lowest to Highest):\n");

            for (int prime : top10)
                writer.write(prime + " ");
            
            writer.write("\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}