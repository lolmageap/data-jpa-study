package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity implements Persistable<String> {
    @Id
    private String id;
    public Item(String id) {
        this.id = id;
    }
    @Override
    public String getId() {
        return null;
    }
    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }
}
