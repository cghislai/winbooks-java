package be.valuya.jbooks.exception;

import be.valuya.winbooks.domain.error.WinbooksException;
import be.valuya.winbooks.domain.error.WinbooksError;


/**
 *
 * @author Yannick Majoros <yannick@valuya.be>
 */
public class WinbooksLoginException extends WinbooksException {

    public WinbooksLoginException(WinbooksError winbooksError) {
        super(winbooksError);
    }

    public WinbooksLoginException(WinbooksError winbooksError, String message) {
        super(winbooksError, message);
    }

    public WinbooksLoginException(WinbooksError winbooksError, String message, Throwable cause) {
        super(winbooksError, message, cause);
    }

    public WinbooksLoginException(WinbooksError winbooksError, Throwable cause) {
        super(winbooksError, cause);
    }
}
