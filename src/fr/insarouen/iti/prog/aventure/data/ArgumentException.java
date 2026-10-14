
package fr.insarouen.iti.prog.aventure.data;

import fr.insarouen.iti.prog.aventure.ITIAventureException;

public class ArgumentException extends ITIAventureException {
    public ArgumentException (String message){
        super(message);
    }

    public ArgumentException(String message, Throwable cause){
        super(message,cause);
    }
}  
