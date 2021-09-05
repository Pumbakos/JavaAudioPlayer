package pl.pumbakos.japwebservice.japresources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Optional;

@Service
public class DefaultUtils<T extends DBModel> {
    private static boolean isColumnUpdatable(Field field) {
        if (field.getAnnotation(Column.class) != null
                && field.getAnnotation(Column.class).nullable()
                && field.getAnnotation(Column.class).insertable()
                && field.getAnnotation(Column.class).updatable()) {
            return true;
        }
        return false;
    }

    /**
     * Updates object under given ID with params from given object in given repository
     *
     * @param repository repository that extends JpaRepository
     * @param object     object were data come from
     * @param id         id of object in repository
     * @return updated object
     * @see JpaRepository
     */
    public T update(JpaRepository<T, Long> repository, T object, Long id) {
        Optional<T> optionalObject = repository.findById(id);

        if (optionalObject.isPresent()) {
            T updatableObject = optionalObject.get();

            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field : fields) {
                try {
                    if (field.getName().equalsIgnoreCase("id"))
                        continue;

                    Field updatableObjectField = updatableObject.getClass().getDeclaredField(field.getName());
                    Field objectField = object.getClass().getDeclaredField(field.getName());

                    updatableObjectField.setAccessible(true);
                    objectField.setAccessible(true);

                    if (field.getAnnotation(Basic.class) != null && objectField.get(object) == null || isColumnUpdatable(field)) {
                        continue;
                    }

                    updatableObjectField.set(updatableObject, objectField.get(object));

                    updatableObjectField.setAccessible(false);
                    objectField.setAccessible(false);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    return null;
                }
            }

            return repository.save(updatableObject);
        }
        return null;
    }
}
