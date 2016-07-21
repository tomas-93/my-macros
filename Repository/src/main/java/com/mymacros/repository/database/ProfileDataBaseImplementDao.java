package com.mymacros.repository.database;

import com.mymacros.repository.dao.entity.ProfileRepositoryDao;
import com.mymacros.dto.entity.ProfileDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomas on 18/07/2016.
 */
@Repository
public class ProfileDataBaseImplementDao implements ProfileRepositoryDao
{
     private final Map<Long, ProfileDto> profileDtoDatabase = new Hashtable<>();
     private volatile long idProfile = 1L;

     /**
      * <h1>getAllProfiles</h1>
      * <p>Se obtiene una lista de perfile de toda la base de datos</p>
      * @return
      */
     @Override
     public  List<ProfileDto> getAllProfiles(long idUser)
     {
          //TODO recuperar la lista deacuerdo al idUser
          List<ProfileDto> list = new ArrayList<>();
          for(Long id: this.profileDtoDatabase.keySet())
               list.add(this.profileDtoDatabase.get(id));

          return list;
     }

     /**
      * <h1>getProfile</h1>
      * <p>Se obtiene un elemeto especifico de la base de datos</p>
      * @param id Identificador que especifica el objeto a buscar.
      * @return Retorna el objeto con los valores obtenidos de la base de datos.
      */
     @Override
     public  ProfileDto getProfile(long id)
     {
          return this.profileDtoDatabase.get(id);
     }

     /**
      *
      * <h1>createProfile</h1>
      * <p>Añade un nuevo elemento a la base de datos</p>
      * @param profileDto Objeto que encapsula la informacion a guardar
      */
     @Override
     public  void createProfile(ProfileDto profileDto)
     {
          this.profileDtoDatabase.put(profileDto.getId(), profileDto);
     }

     /**
      * <h1>updateProfile</h1>
      * <p>Actualiza un elemeto de la base de datos</p>
      * @param profileDto Objeto que tiene los elemetos actualizados de la base de datos.
      */
     @Override
     public void updateProfile(ProfileDto profileDto)
     {

          this.profileDtoDatabase.replace(profileDto.getId(), profileDto);
     }

     /**
      * <h1>deleteProfile</h1>
      * <p>Elimina un elemeto de la base de datos</p>
      * @param id Identificador que representa el elemto a eliminar.
      */
     @Override
     public void deleteProfile(long id)
     {
          this.profileDtoDatabase.remove(id);
     }

     @Override
     public synchronized long getIncrementID()
     {
          return this.idProfile++;
     }
}
