package org.example.exceptions;

public class CancellazioneNonRiuscitaException extends RuntimeException {
    public CancellazioneNonRiuscitaException(String message) {
        super(message);
    }
}
