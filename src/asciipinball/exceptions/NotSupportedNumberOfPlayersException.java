package asciipinball.exceptions;

/**
 * Exception für eine nicht unterstützte Spieleranzahl
 */
public class NotSupportedNumberOfPlayersException extends RuntimeException {

    /**
     * Erstellt eine NotSupportedNumberOfPlayersException
     */
    public NotSupportedNumberOfPlayersException() {

    }


    /**
     * Erstellt eine NotSupportedNumberOfPlayersException mit Nachricht
     */
    public NotSupportedNumberOfPlayersException(String message) {
        super(message);
    }
}
