package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "age"})
@NamedQuery(
        name = "Member.findByName",
        query = "select m from Member m where m.name = :name"
)
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String name) {
        this.name = name;
    }
    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        if (team != null){
            this.team = team;
        }
    }

    public void changeMemberName(String changeName){
        this.name = changeName;
    }

    public void changeTeam(Team newTeam){
        this.team = newTeam;
        newTeam.getMembers().add(this);
    }
}
