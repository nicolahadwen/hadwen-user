package co.hadwen.user;

import co.hadwen.hibernate.HibernateSession;
import co.hadwen.user.entity.UserAccount;
import co.hadwen.user.entity.UserAccount.Attributes;
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

    public Optional<UserAccount> get(@NonNull String userId) {
        return Optional.ofNullable(session.get(UserAccount.class, userId)).map(obj -> (UserAccount) obj);
    }

    public Optional<UserAccount> getByEmail(@NonNull String email) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserAccount> query = criteriaBuilder.createQuery(UserAccount.class);
        Root<UserAccount> root = query.from(UserAccount.class);
        query.select(root)
                .where(criteriaBuilder.equal(root.get(Attributes.EMAIL.getProperName()), email));
        List<UserAccount> results = session.createQuery(query).list();
        return results.size() > 0 ? Optional.of(results.get(0)) : Optional.empty();
    }

    public void create(@NonNull UserAccount user) {
        create(user, false);
    }

    public void create(@NonNull UserAccount user, boolean isPartOfTransaction) {
        if(!isPartOfTransaction) {
            session.beginTransaction();
        }

        session.save(user);
        if(!isPartOfTransaction) {
            session.getTransaction().commit();
        }
    }

    public void update(@NonNull UserAccount user) {
        update(user, false);
    }

    public void update(@NonNull UserAccount user, boolean isPartOfTransaction) {
        if(!isPartOfTransaction) {
            session.beginTransaction();
        }
        session.update(user);
        if(!isPartOfTransaction) {
            session.getTransaction().commit();
        }
    }
}
