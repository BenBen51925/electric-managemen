package exception;

public class SendException extends RuntimeException {
    private int megcode;

    public int getMegcode() {
        return megcode;
    }

    public SendException(String message, int megCode){
        super(message);
        this.megcode = megCode;
    }

    @Override
    public String getMessage() {
        return super.getMessage()+",错误代码:"+megcode;
    }
}
