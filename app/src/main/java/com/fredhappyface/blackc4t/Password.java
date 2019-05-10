package com.fredhappyface.blackc4t;

final class Password {

    /*
    Return a string with the fist letter capitalised
     */
    static String capitaliseFirstLetter(final String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }


    /*
   Return random symbols
    */
    static char[] getRandomSymbol(final int quantity) {
        final char[] symbols = new char[quantity];
        for (int index = 0; index < quantity; index++) {
            symbols[index] = (char) Tools.getRandomInt(33, 44);
        }
        return symbols;
    }

    /*
    Return random digits
     */
    static int[] getRandomDigit(final int quantity) {
        final int[] digits = new int[quantity];
        for (int index = 0; index < quantity; index++) {
            digits[index] = (char) Tools.getRandomInt(0, 9);
        }
        return digits;
    }

    /*
    Return random words
     */
    static String[] getRandomWord(final int quantity, final String... allWords) {
        final String[] words = new String[quantity];
        for (int index = 0; index < quantity; index++) {
            final int line = Tools.getRandomInt(0, allWords.length);
            words[index] = allWords[line];
        }
        return words;
    }
}
