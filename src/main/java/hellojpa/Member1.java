package hellojpa;

import jakarta.persistence.*;

@Entity
public class Member1 {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team; // JPA에게 둘이 무슨 관계인지 알려주지 않으면 에러남

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
//    public void changeTeam(Team team) { // 연관관계 편의 메소드이며, JpaMain에서 member.changeTeam(team);로 작성해주면 됨
//        this.team = team;
//        team.getMembers().add(this);
//    }
}
