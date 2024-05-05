package com.kolaykira.webapp.exception;

import com.kolaykira.webapp.Config.Time;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.ExecutionException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = { ResourceNotFoundException.class})
    public ResponseEntity<ErrorObject> handleResourceNotFoundException (ResourceNotFoundException ex)
    {
        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimestamp(Time.getCurrentTimestamp());
        return new ResponseEntity<ErrorObject>(eObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { NoDataFoundException.class})
    public ResponseEntity<ErrorObject> handleNoDataFoundException(NoDataFoundException ex)
    {
        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.NO_CONTENT.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimestamp(Time.getCurrentTimestamp());
        return new ResponseEntity<ErrorObject>(eObject, HttpStatus.OK);
    }



    @ExceptionHandler(value = { InterruptedException.class, ExecutionException.class})
    public ResponseEntity<ErrorObject> handleInterruptedException(InterruptedException exception)
    {
        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.NO_CONTENT.value());
        eObject.setMessage("Sistem hatası");
        eObject.setTimestamp(Time.getCurrentTimestamp());
        return new ResponseEntity<ErrorObject>(eObject, HttpStatus.OK);
    }

    @ExceptionHandler(value = { RuntimeException.class})
    public ResponseEntity<ErrorObject> handleErrors(RuntimeException exception)
    {
        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.NO_CONTENT.value());
        eObject.setMessage("Sistem hatası");
        eObject.setTimestamp(Time.getCurrentTimestamp());
        return new ResponseEntity<ErrorObject>(eObject, HttpStatus.OK);
    }
}
