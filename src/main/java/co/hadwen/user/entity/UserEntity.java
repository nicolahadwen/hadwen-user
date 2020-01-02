package co.hadwen.user.entity;

import lombok.Data;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.OptimisticLockType;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "IS_SUSPENDED", length = 255)
    private Boolean isSuspended;

    @Column(name = "EXTERNAL_ID", length = 255)
    private String externalId;

    @Column(name = "EXTERNAL_ID_TYPE", length = 255)
    private String externalIdType;
}
