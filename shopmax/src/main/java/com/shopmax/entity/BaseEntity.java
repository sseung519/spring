package com.shopmax.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value={AuditingEntityListener.class}) //audit기능을 사용하기 위해 작성
@MappedSuperclass //엔티티에서 부모클래스로 사용
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy //최초로 등록한 사람의 id를 저장
    @Column(updatable = false) //해당 컬럼에 대한 값은 업데이트 X
    private String createdBy; //등록한 사람

    @LastModifiedBy
    private String modifiedBy; //수정한 사람
}
