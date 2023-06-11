package com.bilgeadam.rentacar.common.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "BaseEntity")
@EntityListeners({AuditingEntityListener.class})
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "base_entities")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseEntity {
    @Id
    @Column(length = 32)
    private String id = UUID.randomUUID().toString().replace("-", "").substring(0, 32);

    @CreatedDate
    @Column
    private LocalDateTime createDate;

    @CreatedBy
    @Column
    private String createdUser;

    @LastModifiedDate
    @Column
    private LocalDateTime updateDate;

    @LastModifiedBy
    @Column
    private String updatedUser;

    @Column
    private short state=1;

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(final String createdUser) {
        this.createdUser = createdUser;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(final LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(final String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getId() {
        return id;
    }

    public void setState(final short state) {
        this.state = state;
    }

    public short getState() {
        return state;
    }
}
