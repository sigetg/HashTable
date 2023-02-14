/*
 * Author: George Sigety - sigetyg@bc.edu
 */

import java.util.ArrayList;

public class PrimeFinder implements java.util.Iterator<Integer> {



  //data members
  private boolean[] isPrime;
  private ArrayList<Integer> primes;
  private static final int SIEVE_SIZE = 1 << 8;
  int currentIndex = 0;

  //Public methods
  public PrimeFinder(int sieveSize) {
    boolean[] isPrime = new boolean[sieveSize];
    this.isPrime = isPrime;
    java.util.Arrays.fill(isPrime, true);
    currentIndex = -1;
    ArrayList<Integer> primes = new ArrayList<Integer>();
    this.primes = primes;
  }

  public PrimeFinder() {
    this(SIEVE_SIZE);
  }


  public Integer next() {
    if (currentIndex == -1) {
      primes = findPrimes();
    }
    return primes.get(currentIndex++);
  }

  public boolean hasNext() {
    if (currentIndex == -1) {
      primes = findPrimes();
    }
    if (currentIndex < primes.size()) {
      return true;
    } else {
      return false;
    }
  }

  //private method
  private ArrayList<Integer> findPrimes() {
    currentIndex = 0;
    for (int i = 2; i < Math.sqrt(isPrime.length); i++) {
      if (isPrime[i]) {
        for (int j = i * i; j < isPrime.length; j += i) {
            isPrime[j] = false;
        }
      }
    }
    for(int i = 2; i < isPrime.length; i++) {
      if (isPrime[i]) {
        primes.add(i);
      }
    }
    return primes;
  }
}
