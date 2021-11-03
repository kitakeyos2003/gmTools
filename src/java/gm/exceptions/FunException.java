package gm.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodj
 * Date: 11-3-24
 * Time: 下午6:09
 */
public class FunException extends Exception{
    public FunException(String message) {
        super(message);
    }

    public FunException() {
        super();
    }

    public FunException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public Throwable getCause() {
        return super.getCause();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
