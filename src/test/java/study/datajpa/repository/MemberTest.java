package study.datajpa.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
@Rollback(value = false)
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testEntity() {
        Team teamA = new Team("FC서울");
        Team teamB = new Team("전북현대");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("김민재", 27, teamB);
        Member member2 = new Member("조규성", 25, teamB);
        Member member3 = new Member("차두리", 34, teamA);
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

    @Test
    public void findNameList() {
        Team team = new Team("마드리드");
        teamRepository.save(team);
        Member m1 = new Member("AAA", 10, team);
        Member m3 = new Member("AAA", 20, team);
        Member m4 = new Member("BBB", 30, team);
        Member m5 = new Member("DDD", 40, team);
        Member m6 = new Member("CCC", 50, team);
        Member m7 = new Member("ccc", 60, team);
        Member m2 = new Member("BBB", 20, team);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);
        memberRepository.save(m5);
        memberRepository.save(m6);
        memberRepository.save(m7);

        int age = 20;

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "name"));
        Page<Member> paging = memberRepository.findByAge(age, pageRequest);

        //Dto로 변환
        paging.map(member -> new MemberDto(member.getId(), member.getName(), null));

        int totalPages = paging.getTotalPages(); //전체 페이지
        long totalElements = paging.getTotalElements(); //전체 데이터수
        int pageNumber = paging.getNumber(); //현재 페이지 번호
        boolean First = paging.isFirst(); //현재 페이지가 첫번째 페이지인지
        boolean hasNext = paging.hasNext(); //다음 페이지가 존재하는지
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);
        for (Member m : paging) {
            System.out.println("m = " + m); //전체 데이터
        }

    }

    @Test
    public void TTTTest(){
        Team teamA = new Team("레알마드리드");
        Team teamB = new Team("바르셀로나");
        Team teamC = new Team("아틀레티코");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        teamRepository.save(teamC);
        Member m1 = new Member("AAA", 10, teamA);
        Member m2 = new Member("BBB", 20, teamB);
        Member m3 = new Member("AAA", 20, teamC);
        Member m4 = new Member("BBB", 30, teamC);
        Member m5 = new Member("DDD", 40, teamA);
        Member m6 = new Member("CCC", 50, teamB);
        Member m7 = new Member("zzz", 60, teamA);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);
        memberRepository.save(m5);
        memberRepository.save(m6);
        memberRepository.save(m7);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findEntityGraphByName("AAA");

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("teamClass = " + member.getTeam().getClass());
            System.out.println("Team = " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint(){
        memberRepository.save(new Member("member1", 10,null));
        em.flush();
        em.clear();

        List<Member> findMember = memberRepository.findLockByName("member1");

    }

    @Test
    public void callCustom(){
        List<Member> memberCustom = memberRepository.findMemberCustom();

    }

    @Test
    public void JpaEventBaseEntity() throws Exception{
        Member member = new Member("member1");
        memberRepository.save(member); //@PrePersist 발생

        Thread.sleep(100);
        member.changeMemberName("member2");

        em.flush(); //@PreUpdate 발생
        em.clear();

        Optional<Member> findMember = memberRepository.findById(member.getId());

        System.out.println("findMemberC = " + findMember.get().getCreatedDate());
        System.out.println("findMemberD = " + findMember.get().getLastModifiedDate());

    }

}
