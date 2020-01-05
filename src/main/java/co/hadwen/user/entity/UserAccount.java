package co.hadwen.user.entity;

import co.hadwen.hibernate.Encryption;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Data
@Entity
@Table(name = "user_account", schema="hadwen", uniqueConstraints = {
        @UniqueConstraint(columnNames = "USER_ID"),
        @UniqueConstraint(columnNames = "EMAIL") })
public class UserAccount implements Serializable {
    @Getter
    public enum Attributes {
        USER_ID("userId"),
        EMAIL("email"),
        PASSWORD("password"),
        FIRST_NAME("firstName"),
        LAST_NAME("lastName"),
        SALT("salt"),
        IS_SUSPENDED("isSuspended"),
        EXTERNAL_ID("externalId"),
        EXTERNAL_ID_TYPE("externalIdType"),
        CREATED_AT("createdAt"),
        UPDATED_AT("updatedAt");

        @Getter
        private final String properName;
        Attributes(@NonNull String properName) {
            this.properName = properName;
        }
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "co.hadwen.hibernate.IdGenerator"
    )
    @Column(name = "USER_ID", unique = true, nullable = false, length = 36)
    private String userId;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", unique = true, length = 300)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

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

    @PreUpdate
    void prePersist() {
        this.password = new Encryption().encryptPassword(password);
    }

    public boolean doesPasswordMatch(@NonNull String other) {
        return new Encryption().verifyPassword(password, other);
    }
}
