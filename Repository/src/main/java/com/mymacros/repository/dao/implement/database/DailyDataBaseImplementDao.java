package com.mymacros.repository.dao.implement.database;

import com.mymacros.repository.dao.entity.DailyRepositoryDao;
import com.mymacros.database.entity.DailyEntity;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;


/**
 * @author Tomas Yussef Galicia Guzman
 */
@Repository
public class DailyDataBaseImplementDao extends HibernateTemplate implements DailyRepositoryDao
{
    @Inject
    public DailyDataBaseImplementDao(SessionFactory sessionFactory)
    {
        super(sessionFactory);
    }



    /**
     * <h1>getAllDaily</h1>
     * <p>Se obtienen todos lo usuarios de la base de datso</p>
     *
     * @return Retorna una lista del objecto DailyDto
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DailyEntity> getAllDaily(long idDaily)
    {
        return (List<DailyEntity>) this.find("from DailyEntity where DailyEntity.id = :idUser", idDaily);
    }

    /**
     * <h1>getDaily</h1>
     * <p>Se obtiene un objecto especifico de la base de datos</p>
     *
     * @param id Este parametro es el valor del id de objecto que se quiere obtener
     * @return El objecto que se obtuvo de la base detos
     */
    @Override
    public DailyEntity getDaily(long id)
    {
        return this.load(DailyEntity.class, id);
    }

    /**
     * <h1>createDaily</h1>
     * <p>Se almacena un objeto DailyDto a la base de datos</p>
     *
     * @param dailyEntity entidad que sera almacenado en la base de datos
     */
    @Override
    public void createDaily(DailyEntity dailyEntity)
    {
        this.save(dailyEntity);
    }

    /**
     * <h1>deleteDaily</h1>
     * <p>Elimina un objeto diario de la base de datos</p>
     *
     * @param id indetificador de la entidad que sera eliminada
     */
    @Override
    public void deleteDaily(long id)
    {
        DailyEntity dailyEntity = this.load(DailyEntity.class, id);
        if (dailyEntity != null)
            this.delete(dailyEntity);
    }

    /**
     * <h1>updateDaily</h1>
     * <p>Actualiza un objeto de la base de datos</p>
     *
     * @param dailyEntity entidad que contendra los datos actualizados
     */
    @Override
    public void updateDaily(DailyEntity dailyEntity)
    {
        if (this.load(DailyEntity.class, dailyEntity.getId()) != null)
            this.update(dailyEntity);
    }
}
