package xyz.crediblepulse.crediblepulsebackend.model.technical;

import static jakarta.persistence.TemporalType.TIMESTAMP;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BusinessEntity<U> {

    @CreatedBy
    @Column(name = "created_by")
    protected U createdBy;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    protected Date createdAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    protected U updatedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name = "updated_at")
    protected Date updatedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
