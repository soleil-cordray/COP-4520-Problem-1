# Multi Threaded Prime Number Generator

# Description

This Java program finds all primes between 1 and 10^8, dividing the task among 8 different streams of computation. It then lists information about the primes found to a file named "primes.txt." This program uses a single class with three methods: main (run the program), isPrime (determine if a number is prime), and writeResults (print information about the program and primes found).

The main function executes the threads, calls isPrime and writeResults, and performs major calculations.

The isPrime method returns false for any number less than 2 (because 0 and 1 do not count as prime), any even number greater than 2, and any odd number divisible by a number. The method returns true for 2 and 3, as well as any number that proves to be not divisible by a number outside of itself.

The writeResults method prints out the program's execution time, the total number of primes found, the sum of all the primes found, and a list of the top 10 largest prime numbers found to a file named "primes.txt."

# Evaluation

First implementing this code, the runtime averaged around 26 seconds. Once I made the function that determines whether a number is prime or not more efficient, the program runtime started averaging around 11 seconds. Since each thread is consistently executed, they should be doing the same amount of work. Thus, each thread should be taking around the same time.

# Install & Run

Ensure Java is installed to your computer. Run code through terminal by typing out the following:
javac Program1.java
java Program1.java
