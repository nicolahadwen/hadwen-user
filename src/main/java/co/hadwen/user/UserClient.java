package co.hadwen.user;

import co.hadwen.hibernate.HibernateContext;
import co.hadwen.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hibernate.Session;

@AllArgsConstructor
public class UserClient {
    private final HibernateContext hibernateContext;

    public void createUser(@NonNull UserEntity user) {
        Session session = hibernateContext.openSession();
        session.beginTransaction();
        session.save(user);
        hibernateContext.shutdown();
    }
}
