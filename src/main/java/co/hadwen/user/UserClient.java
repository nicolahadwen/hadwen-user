package co.hadwen.user;

import co.hadwen.hibernate.HibernateSession;
import co.hadwen.user.entity.UserEntity;
import co.hadwen.user.entity.UserEntity.Columns;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class UserClient {
    private final Session session;

    public UserClient(@NonNull HibernateSession session) {
        this.session = session.getSession();
    }

    public Optional<UserEntity> get(@NonNull String userId) {
        return Optional.ofNullable(session.get(UserEntity.class, userId)).map(obj -> (UserEntity) obj);
    }

    public Optional<UserEntity> getByEmail(@NonNull String email) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        query.select(root)
                .where(criteriaBuilder.equal(root.get(Columns.EMAIL.getProperName()), email));
        List<UserEntity> results = session.createQuery(query).list();
        return results.size() > 0 ? Optional.of(results.get(0)) : Optional.empty();
    }

    public void create(@NonNull UserEntity user) {
        create(user, false);
    }

    public void create(@NonNull UserEntity user, boolean isPartOfTransaction) {
        if(!isPartOfTransaction) {
            session.beginTransaction();
        }

        session.save(user);
        if(!isPartOfTransaction) {
            session.getTransaction().commit();
        }
    }

    public void update(@NonNull UserEntity user) {
        update(user, false);
    }

    public void update(@NonNull UserEntity user, boolean isPartOfTransaction) {
        if(!isPartOfTransaction) {
            session.beginTransaction();
        }
        session.update(user);
        if(!isPartOfTransaction) {
            session.getTransaction().commit();
        }
    }
}
