package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> , MemberRepositoryCustom{

    List<Member> findByNameAndAgeGreaterThan(String name, int age);
    @Query("select m from Member m where m.name = :name")
    Member findByName(@Param("name") String name);

    @Query("select m from Member m where m.name = :name and m.age = :age")
    List<Member> findByNameAndAge(@Param("name") String name , @Param("age") int age);
    @Query("select m.name from Member m")
    List<String> findByNameList();
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.name, t.name) from Member m join m.team t")
    List<MemberDto> findByNameDto();
    @Query("select m from Member m where m.name in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    Member findListByName(@Param("name") String name);
    Member findMemberByName(@Param("name") String name);
    Optional<Member> findOptionalByName(@Param("name") String name);

    Page<Member> findByAge(int age, Pageable pageable);
//    Slice<Member> findBySlice(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age+1 where m.age >= :age")
    int updateAgePlusOne(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override //원래는 멤버만 lazy loading으로 조회
    @EntityGraph(attributePaths = "team") //team만 fetch join으로 한방쿼리 조회
    List<Member> findAll();

    @EntityGraph(attributePaths = "team")
    @Query("select m from Member m")
    List<Member> findQueryByAll();

    @EntityGraph(attributePaths = "team")
    List<Member> findEntityGraphByName(@Param("name") String name);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Member> findQueryHintByName(@Param("name") String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByName(@Param("name") String name);
    List<NameOnly> findProjectionsByName(@Param("name") String name);
    <T> Type findProjectionsClassByName(@Param("name") String name, Class<T> type);
}
