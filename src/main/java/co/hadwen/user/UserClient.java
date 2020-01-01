package co.hadwen.user;

import co.hadwen.hibernate.HibernateConfig;
import co.hadwen.hibernate.HibernateConfigFactory;
import co.hadwen.user.entity.UserEntity;
import co.hadwen.user.exception.CreateUserException;
import co.hadwen.user.exception.CreateUserException.Type;
import lombok.NonNull;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.hibernate.Session;

public class UserClient {
    private final LazyInitializer<HibernateConfig> hibernateConfig;

    public UserClient(HibernateConfigFactory hibernateConfigFactory) {
        this.hibernateConfig = hibernateConfigFactory.createLazy(getClass().getClassLoader());
    }

    public UserClient() {
        this.hibernateConfig = new HibernateConfigFactory().createLazy(getClass().getClassLoader());
    }

    public void createUser(@NonNull UserEntity user) {
        try {
            Session session = hibernateConfig.get().openSession();
            session.beginTransaction();
            session.save(user);
            hibernateConfig.get().shutdown();
        } catch (ConcurrentException e) {
            throw new CreateUserException(Type.HIBERNATE_CONFIG);
        }
    }
}
