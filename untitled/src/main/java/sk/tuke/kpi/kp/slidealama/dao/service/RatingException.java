package sk.tuke.kpi.kp.slidealama.dao.service;

public class RatingException extends RuntimeException {
    public RatingException(String message) {
        super(message);
    }

    public RatingException(String message, Throwable cause) {
        super(message, cause);
    }
}
