package services.interfaces;

/**
 * Created by Krzysiu on 2014-06-13.
 */
public interface ILoggerService {

    public void info(String message);
    public void debug(String message);
    public void warn(String message);
    public void error(String message);

}
