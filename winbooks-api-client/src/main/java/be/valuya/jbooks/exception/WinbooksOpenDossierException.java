package be.valuya.jbooks.exception;

import be.valuya.winbooks.domain.error.WinbooksException;
import be.valuya.winbooks.domain.error.WinbooksError;

/**
 *
 * @author Yannick Majoros <yannick@valuya.be>
 */
public class WinbooksOpenDossierException extends WinbooksException {

    public WinbooksOpenDossierException(WinbooksError winbooksError) {
        super(winbooksError);
    }

    public WinbooksOpenDossierException(WinbooksError winbooksError, String message) {
        super(winbooksError, message);
    }

    public WinbooksOpenDossierException(WinbooksError winbooksError, String message, Throwable cause) {
        super(winbooksError, message, cause);
    }

    public WinbooksOpenDossierException(WinbooksError winbooksError, Throwable cause) {
        super(winbooksError, cause);
    }
}
