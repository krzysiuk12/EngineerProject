package repository.interfaces;

import domain.common.implementation.BaseObject;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-21.
 */
public interface IBaseHibernateRepository {

    public <T extends BaseObject> void saveOrUpdate(T entity);

    public <T> T returnSingleOrNull(List<T> accounts);

}
