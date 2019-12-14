package com.fredhappyface.blackc4t;

import java.security.SecureRandom;

final class Tools {

    private Tools() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static StringBuffer doOneTimePad(String keyString, final String messageString, final boolean decrypt) {

        final StringBuffer outputString = new StringBuffer();
        int keyLength = keyString.length();
        final int messageLength = messageString.length();

        for (int index = 0; index < messageLength; index++) {
            if (index >= keyLength) {
                keyString += keyString;
                keyLength += keyLength;
            }
            final char keyChar = keyString.charAt(index);
            final char messageChar = messageString.charAt(index);
            final char outChar;
            if (decrypt) {
                outChar = (char) (messageChar - keyChar);
            } else {
                outChar = (char) (messageChar + keyChar);
            }
            outputString.append(outChar);


        }
        return outputString;

    }

    static StringBuffer doTwoKey(final String pKeyString1, final String pKeyString2, final String messageString, final boolean decrypt) {

        String keyString1 = pKeyString1;
        String keyString2 = pKeyString2;

        final StringBuffer outputString = new StringBuffer();
        int keyLength1 = keyString1.length();
        int keyLength2 = keyString2.length();
        final int messageLength = messageString.length();

        for (int index = 0; index < messageLength; index++) {

            if (index >= keyLength1) {
                keyString1 += keyString1;
                keyLength1 += keyLength1;
            }
            if (index >= keyLength2) {
                keyString2 += keyString2;
                keyLength2 += keyLength2;
            }
            final int keyChar = keyString1.charAt(index) + keyString2.charAt(index);
            final char messageChar = messageString.charAt(index);
            final char outChar;
            if (decrypt) {
                outChar = (char) (messageChar - keyChar);
            } else {
                outChar = (char) (messageChar + keyChar);
            }
            outputString.append(outChar);
        }

        return outputString;

    }


    static StringBuffer doPassword(final int wordsInt, final int numbersInt, final int symbolsInt, final String... allWords) {

        final StringBuffer outputString = new StringBuffer();

        /*
        Populate arrays
        */
        final String[] wordsArray = Password.getRandomWord(wordsInt, allWords);
        final int[] digitArray = Password.getRandomDigit(numbersInt);
        final char[] symbolsArray = Password.getRandomSymbol(symbolsInt);
        /*
        Add contents to outputString
         */
        for (final String word : wordsArray) {
            outputString.append(Password.capitaliseFirstLetter(word));
        }
        for (final int digit : digitArray) {
            outputString.append(digit);
        }
        for (final char symbol : symbolsArray) {
            outputString.append(symbol);
        }

        return outputString;
    }


    /*
   Returns random int between min and max (inclusive)
    */
    static int getRandomInt(final int min, final int max) {
        final SecureRandom random = new SecureRandom();
        return (int) ((random.nextDouble() * (max - min)) + min);
    }
}
