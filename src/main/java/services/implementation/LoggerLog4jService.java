package services.implementation;

import org.apache.log4j.Logger;
import services.interfaces.ILoggerService;


/**
 * Created by Krzysiu on 2014-06-13.
 */
public class LoggerLog4jService implements ILoggerService {

    private String tag;
    private Logger logger;


    public LoggerLog4jService(String tag) {
        this.tag = tag;
        this.logger = Logger.getLogger(tag);
    }

    @Override
    public void info(String message) {
        logger.info(getMessage(message));
    }

    @Override
    public void debug(String message) {
        logger.debug(getMessage(message));
    }

    @Override
    public void warn(String message) {
        logger.warn(getMessage(message));
    }

    @Override
    public void error(String message) {
        logger.error(getMessage(message));
    }

    private String getMessage(String message) {
        return new StringBuilder(tag).append(" ").append(message).toString();
    }

}
