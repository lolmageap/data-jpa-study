package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){
//        if (member.getId() == null) {
            em.persist(member);
//        }
//        else {
//            Member newMember = find(member.getId());
//            newMember = member;
//            em.persist(newMember);
//        }
        return member;
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public long count(){
        return findAll().size();
    }

    public Member findByNameAndAgeGreaterThen(String name , int age){
        Member result = em.createQuery("select m from Member m where m.name = :name and m.age > :age", Member.class)
                .setParameter("name", name)
                .setParameter("age", age)
                .getSingleResult();
        return result;
    }

    public List<Member> findByName(String name){
        var findMembers = em.createNamedQuery("Member.findByName", Member.class)
                .setParameter("name", name)
                .getResultList();
        return findMembers;
    }

    public List<Member> findByPage(int age, int offset, int limit){
        var findMembers = em.createQuery("select m from Member m where m.age > :age order by m.name desc ", Member.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("age", age)
                .getResultList();
        return findMembers;
    }
    public Long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age > :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    public int bulkAgePlus(int age){
        return em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
    }


}
