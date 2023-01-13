package study.datajpa.entity;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass @Getter
public class JpaBaseEntity {

    @Column(updatable = false) // 변경 불가능
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist //값이 생성 될 때 실행
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now; //일부러 채워 넣음
    }

    @PreUpdate //값이 업데이트 될 때 실행
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }

}
