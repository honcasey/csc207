public class InvalidTransactionException extends Exception{
    InvalidTransactionException(String errorMessage){
        super(errorMessage);
    }
}
