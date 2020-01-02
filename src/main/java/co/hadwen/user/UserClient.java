package co.hadwen.user;

import co.hadwen.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hibernate.Session;

@AllArgsConstructor
public class UserClient {
    private final Session session;
    public void createUser(@NonNull UserEntity user) {
        createUser(user, false);
    }

    public void createUser(@NonNull UserEntity user, boolean isPartOfTransaction) {
        if(!isPartOfTransaction) {
            session.beginTransaction();
        }
        session.save(user);
        if(!isPartOfTransaction) {
            session.getTransaction().commit();
        }
    }
}
