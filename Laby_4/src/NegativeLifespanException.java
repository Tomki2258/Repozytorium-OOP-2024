public class NegativeLifespanException extends Exception{
    private final String message;
    public NegativeLifespanException(String message){
        this.message = message;
    }
    @Override
    public String getMessage() {
        return "Wyjebalo cos tam srake pierdake : " + message;
    }
}
