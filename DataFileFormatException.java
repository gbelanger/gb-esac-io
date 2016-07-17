package gb.esac.io;


public class DataFileFormatException extends Exception {

    public DataFileFormatException () {
        super();
    }
    
    public DataFileFormatException (String msg) {
        super(msg);
    }
    
    public DataFileFormatException (String msg, Exception e) {
        super(msg+"\n", e);
    }

}
