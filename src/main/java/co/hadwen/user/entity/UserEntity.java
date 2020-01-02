package co.hadwen.user.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Data
@javax.persistence.Entity
@Entity(optimisticLock = OptimisticLockType.ALL, dynamicUpdate=true)
@Table(name = "user", schema="hadwen", uniqueConstraints = {
        @UniqueConstraint(columnNames = "USER_ID"),
        @UniqueConstraint(columnNames = "EMAIL") })
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "co.hadwen.hibernate.IdGenerator"
    )
    @Column(name = "USER_ID", unique = true, nullable = false, length = 36)
    private String userId;

    @Column(name = "EMAIL", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "PASSWORD", unique = true, length = 255)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false, length = 255)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 255)
    private String lastName;

    @Setter(AccessLevel.NONE)
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "SALT", nullable = false)
    private byte[] salt = createSalt();

    @Column(name = "IS_SUSPENDED")
    private boolean isSuspended;

    @Column(name = "EXTERNAL_ID")
    private String externalId;

    @Column(name = "EXTERNAL_ID_TYPE")
    private String externalIdType;

    @Column(name = "CREATED_AT", nullable = false, insertable= false, updatable= false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false, insertable= false, updatable= false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private static byte[] createSalt() {
        byte[] salt = new byte[16];
        new Random().nextBytes(salt);
        return salt;
    }
}
