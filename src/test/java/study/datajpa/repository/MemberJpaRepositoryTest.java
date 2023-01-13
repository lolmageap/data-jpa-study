package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberC");
//        member.changeMemberName("memberA");
        Member save = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(save.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member);
        assertThat(member.equals(findMember));

    }
    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member memberA = memberJpaRepository.findById(member1.getId()).get();
        Member memberB = memberJpaRepository.findById(member2.getId()).get();
        assertThat(memberA).isEqualTo(member1);
        assertThat(memberB).isEqualTo(member2);

        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long last = memberJpaRepository.count();
        assertThat(last).isEqualTo(0);

    }

    @Test
    public void findByNameAndAgeGreaterThen(){
        Member member1 = new Member("kim", 25,null);
        Member member2 = new Member("jung", 20,null);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> a = memberRepository.findByNameAndAgeGreaterThan("kim", 20);

        assertThat(a.get(0)).isEqualTo(member1);
        assertThat(a.get(0).getName()).isEqualTo(member1.getName());
        assertThat(a.get(0).getAge()).isEqualTo(25);
        assertThat(a.size()).isEqualTo(1);
    }

}