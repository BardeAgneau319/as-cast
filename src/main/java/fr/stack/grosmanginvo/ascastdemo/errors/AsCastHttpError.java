package fr.stack.grosmanginvo.ascastdemo.errors;

import java.io.IOException;

public class AsCastHttpError extends IOException {

    public AsCastHttpError() {
    }

    public AsCastHttpError(String message) {
        super(message);
    }

    public AsCastHttpError(String message, Throwable cause) {
        super(message, cause);
    }

    public AsCastHttpError(Throwable cause) {
        super(cause);
    }
}
