package asciipinball.corelogic.AsciiStringBuilder;

import asciipinball.fonts.Font;

/**
 * Konvertiert einen String in Ascii-Art
 */
public class AsciiStringBuilder {

    private Font font;

    /**
     * Erstellt einen AsciiStringBuilder mit einem Font
     * @param font Font der Schrift
     */
    public AsciiStringBuilder(Font font) {
        this.font = font;
    }

    /**
     * Konvertiert einen String zu einem 2D-char Array
     * @param string String der Convertiert werden soll
     * @return Char-Array mit String in AsciiArt
     */
    public char[][] buildAsciiString(String string) {
        int maxColumns = 0;
        int currentColumns = 0;
        int maxRows = 0;
        int currentRows;
        int totalRows = 0;

        for (int i = 0; i < string.length(); i++) {

            if (string.charAt(i) == '\n') {
                totalRows += maxRows;
                currentColumns = 0;
                maxRows = 0;
            } else {
                currentColumns += countColumns(new FontReader().getStringOfLetter(string.charAt(i), font));
                maxColumns = currentColumns > maxColumns ? currentColumns : maxColumns;
                currentRows = countRows(new FontReader().getStringOfLetter(string.charAt(i), font));
                if (currentRows > maxRows) {
                    maxRows = currentRows;
                }
            }


        }
        totalRows += maxRows;

        char[][] returnArray = new char[totalRows][maxColumns];

        for(int row = 0; row < returnArray.length; row++){
            for(int column = 0; column < returnArray[0].length; column++){
                returnArray[row][column] = ' ';
            }
        }

        int columnTracker = 0;
        int rowTracker = 0;
        int maxRowCountSinceWordwrap = 0;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '\n') {
                rowTracker += maxRowCountSinceWordwrap;
                maxRowCountSinceWordwrap = 0;
                columnTracker = 0;
            } else {
                char[][] currentAsciiChar = convertStringToArray(new FontReader().getStringOfLetter(string.charAt(i), font));

                maxRowCountSinceWordwrap = currentAsciiChar.length > maxRowCountSinceWordwrap ? currentAsciiChar.length : maxRowCountSinceWordwrap;
                for (int column = columnTracker; column < columnTracker + currentAsciiChar[0].length; column++) {
                    for (int row = rowTracker; row < rowTracker + currentAsciiChar.length; row++) {
                        returnArray[row][column] = currentAsciiChar[row - rowTracker][column - columnTracker];
                    }
                }
                columnTracker += currentAsciiChar[0].length;
            }

        }

        return returnArray;
    }


    private char[][] convertStringToArray(String string) {

        char[][] returnCharArray = new char[countRows(string)][countColumns(string)];

        int stringIterator = -1;

        for (int row = 0; row < returnCharArray.length; row++) {
            for (int column = 0; column < returnCharArray[0].length; column++) {
                stringIterator++;
                if (stringIterator >= string.length()) {
                    returnCharArray[row][column]=' ';
                    continue;
                }
                if (string.charAt(stringIterator) == '\n') {
                    returnCharArray[row][column] = ' ';
                } else {
                    returnCharArray[row][column] = string.charAt(stringIterator);
                }
            }
        }
        return returnCharArray;
    }

    private int countRows(String string) {
        int numberOfRows = 1;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '\n') {
                numberOfRows++;
            }
        }
        return numberOfRows;
    }

    private int countColumns(String string) {
        int maxColumns = 0;
        int columnsInCurrentRow = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) != '\n') {
                columnsInCurrentRow++;
            } else {
                columnsInCurrentRow++;
                if (columnsInCurrentRow > maxColumns) {
                    maxColumns = columnsInCurrentRow;
                }
                columnsInCurrentRow = 0;
            }
        }

        return maxColumns;

    }
}
