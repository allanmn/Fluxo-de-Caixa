/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author allan
 */
public class ValidationException extends Exception {
    
    String validationMessage;
    
    public ValidationException (String message) {
        this.validationMessage = message;
    }

    public Object getErrorMessage() {
        System.out.println(this.validationMessage);
        return "O campo " + this.validationMessage + " é obrigatório.";
    }
}
