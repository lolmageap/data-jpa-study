package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {

    @PersistenceContext
    EntityManager em;

    public Team save(Team team){
        em.persist(team);
        return team;
    }

    public Team find(Long id){
        return em.find(Team.class, id);
    }

    public Optional<Team> findById(Long id){
        Team team = em.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    public List<Team> findAll(){
        return em.createQuery("select t from Team t", Team.class).setMaxResults(10).getResultList();
    }

    public void delete(Team team) {
        em.remove(team);
    }

    public long count(){
        return findAll().size();
    }
}