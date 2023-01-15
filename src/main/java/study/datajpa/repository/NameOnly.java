package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

//Open Projection
public interface NameOnly {
    // entity를 다 가져오고 name 컬럼과 age 컬럼을 이어 붙히는 경오
    @Value("#{target.name + ' ' + target.age}")
    String getName();
}
