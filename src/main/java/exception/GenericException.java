package exception;

public class GenericException extends Exception {

	private static final long serialVersionUID = -8071122815276901061L;

	public GenericException(){
        // empty constructor
    }

    public GenericException(String msg){
        super(msg);
    }

    public GenericException(Exception e){
        super(e);
    }
}
