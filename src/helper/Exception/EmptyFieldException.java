package helper.Exception;

public class EmptyFieldException extends Exception {
    private String fieldName;
    
    public EmptyFieldException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
    
    
}
