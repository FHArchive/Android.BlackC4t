package com.fredhappyface.blackc4t;

final class PublicKeyEncryption {

    /*
    Checks that the argument is a prime number
     */
    static boolean pkeIsPrime(final int testVal) {
        if (testVal % 2 == 0 && testVal > 2) {
            return false;
        }
        int iteration = 3;
        final double sqrt = Math.sqrt(testVal);
        while (iteration < sqrt) {
            if (testVal % iteration == 0) {
                return false;
            }
            iteration += 2;
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
    Finds the Highest Common Factor or argument value1 and argument value2
     */
    static int pkeHighestCommonFactor(final int value1, final int value2) {
        if (value2 == 0) {
            return value1;
        } else {
            return pkeHighestCommonFactor(value2, value1 % value2);
        }
    }

    /*
    Calculates the private key exponent from the public key exponent
     */
    static int pkeModInverse(final int publicKeyInt, final int eulerTotient) {
        for (int pKCandidate = 1; pKCandidate < eulerTotient; pKCandidate++) {
            if ((publicKeyInt * pKCandidate) % eulerTotient == 1) {
                return pKCandidate;
            }
        }
        return 1;
    }

    static int pkeExpMod(final int base, final int exp, final int mod) {
        if (exp == 0){
            return 1;
        }
        if (exp % 2 == 0) {
            final int expMod = pkeExpMod(base, (exp / 2), mod);
            return (int) (Math.pow(expMod, 2) % mod);
        } else {
            return (base * pkeExpMod(base, (exp - 1), mod)) % mod;
        }
    }
}
