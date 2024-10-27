package be.boets.addressbook.exception;


public class ClientException extends RuntimeException {

    private final int httpStatus;

    public ClientException(final int httpStatus, final String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

}
