package mate.academy.internetshop.exception;

import java.sql.SQLException;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, SQLException exception) {
        super(message,exception);
    }
}
