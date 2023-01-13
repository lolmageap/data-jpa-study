package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@SpringBootTest
@Rollback(value = false)
public class TeamTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity(){
        Team teamA = new Team("FC서울");
        Team teamB = new Team("전북현대");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("김민재" , 27 , teamB);
        Member member2 = new Member("조규성" , 25 , teamB);
        Member member3 = new Member("차두리" , 34 , teamA);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        em.flush();
        em.clear();

        List<Member> findM = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : findM) {
            System.out.println("member -> " + member);
            System.out.println("Team -> " + member.getName() + " -> " + member.getTeam().getName());
        }

    }


}
