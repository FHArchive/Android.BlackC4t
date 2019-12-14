package com.fredhappyface.blackc4t;

final class PublicKeyEncryption {

    private PublicKeyEncryption() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

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
        return (Tools.getRandomInt((int) (min / 2.0), (int) (max / 2.0)) << 1) + 1;
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
        if (exp == 0) {
            return 1;
        }
        if (exp % 2 == 0) {
            final int expMod = pkeExpMod(base, (exp / 2), mod);
            return (int) (StrictMath.pow(expMod, 2) % mod);
        } else {
            return (base * pkeExpMod(base, (exp - 1), mod)) % mod;
        }
    }

    static int[] generate() {
        /*
            Generate the seed prime numbers
             */
        boolean isPrime;
        int prime0;
        int prime1;
        do {
            prime0 = pkePrimeGen(64, 256);
            isPrime = pkeIsPrime(prime0);
        }
        while (!isPrime);

        do {
            prime1 = pkePrimeGen(64, 256);
            isPrime = pkeIsPrime(prime1);
        }
        while (!isPrime);
            /*
            Generate the key modulus - this is used in the encryption and decryption methods
             */
        final int modulusInt = prime0 * prime1;

            /*
            Generate the euler totient - this is used to generate the private key exponent from
            the public key exponent
             */
        final int eulerTotient = (prime0 - 1) * (prime1 - 1);

            /*
            Use Euclid's Algorithm to verify that publicKeyExponent and eulerTotient are coprime
             */

        int factor;
        int publicKeyInt;
        do {
            publicKeyInt = Tools.getRandomInt(1, eulerTotient);
            factor = pkeHighestCommonFactor(publicKeyInt, eulerTotient);
        }
        while (factor != 1);

        return new int[]{modulusInt, eulerTotient, publicKeyInt};
    }
}
