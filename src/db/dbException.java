package db;

public class dbException extends Exception {
	public dbException()  {}                //用来创建无参数对象
    public dbException(String message) {        //用来创建指定参数对象
        super(message);                             //调用超类构造器
    }
}
