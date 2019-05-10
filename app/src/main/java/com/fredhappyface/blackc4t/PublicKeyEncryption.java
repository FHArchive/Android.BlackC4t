package com.fredhappyface.blackc4t;

class PublicKeyEncryption {

    /*
    Checks that the argument is a prime number
     */
    static boolean pkeIsPrime(final int x) {
        if (x % 2 == 0 && x > 2) {
            return false;
        }
        int i = 3;
        double sqrt = Math.sqrt(x);
        while (i < sqrt) {
            if (x % i == 0) {
                return false;
            }
            i += 2;
        }
        return true;
    }

    /*
    Generates a number that may be prime
     */
    static int pkePrimeGen(final int min, final int max) {
        return Tools.getRandomInt((int) (min / 2.0), (int) (max / 2.0)) * 2 + 1;
    }



    /*
    Finds the Highest Common Factor or argument a and argument b
     */
    static int pkeHighestCommonFactor(final int a, final int b) {
        if (b == 0) {
            return a;
        } else {
            return pkeHighestCommonFactor(b, a % b);
        }
    }

    /*
    Calculates the private key exponent from the public key exponent
     */
    static int pkeModInverse(final int a, final int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    static int pkeExpMod(final int base, final int exp, final int mod) {
        if (exp == 0) return 1;
        if (exp % 2 == 0) {
            int expMod = pkeExpMod(base, (exp / 2), mod);
            return (int) (Math.pow(expMod, 2) % mod);
        } else {
            return (base * pkeExpMod(base, (exp - 1), mod)) % mod;
        }
    }
}
