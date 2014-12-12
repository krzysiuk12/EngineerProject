package services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.interfaces.IBaseService;
import services.interfaces.IDataGeneratorService;
import services.interfaces.ILoggerService;

/**
 * Created by Krzysztof Kicinger on 2014-11-21.
 */
@Service
public class BaseService implements IBaseService {

    private ILoggerService loggerService;
    private IDataGeneratorService dataGeneratorService;

    @Autowired
    public BaseService(ILoggerService loggerService, IDataGeneratorService dataGeneratorService) {
        this.loggerService = loggerService;
        this.dataGeneratorService = dataGeneratorService;
    }

}
