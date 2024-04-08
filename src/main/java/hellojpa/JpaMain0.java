package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain0 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜잭션 안에서 일어나야한다
        tx.begin();

        try {
            /*// ▶️회원 등록
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");
            em.persist(member);
            // 회원 수정
            Member findMember = em.find(Member.class, "1L");
            findMember.setName("HelloJPA"); // 수정하고 저장 안해도 됨!
            // 회원 삭제
            em.remove(findMember);*/

            /*// ▶️회원 조회
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1) // 페이징 1부터
                    .setMaxResults(5) // 페이징 5까지만
                    .getResultList();*/

            /*// ▶️비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");
            // 영속
            System.out.println("=== BEFORE ===");
            em.persist(member); // 이때 DB에 저장되는게 아니라 영속 컨텍스트에 등록되는거임!
            System.out.println("=== AFTER ===");

            Member findMember = em.find(Member.class, 101L); // DB에서 조회하는게 아니라서 select쿼리문 안나가고 1차 캐시에서 조회됨!

            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());*/
            
            /*// ▶️엔티티 등록 - 쓰기지연 SQL 저장소
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2); // 이렇게 2개 모아서 커밋때 insert쿼리문 날림 = 쓰기지연*/

            /*// ▶️엔티티 수정 - 변경 감지(Dirty Checking)
            Member0 memberA = em.find(Member0.class, "memberA");

            memberA.setUsername("hi");
            // em.update(memberA); 이런 코드가 있어야 하지 않을까? 생각하겠지만 절대 아님!*/

            /*// ▶️강제 플러시, 커밋과 JPQL은 플러시 자동호출!
//            Member memberB = new Member(200L, "member200");
//            em.persist(memberB);

            em.flush(); // 플러시는! 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화!⭐
            System.out.println("============");*/

            /*// ▶️준영속(쓸 일이 잘없음)
            Member0 member = em.find(Member0.class, 200L);
            member.setUsername("AAAAA");

            em.detach(member); // member엔티티를 더이상 JPA가 관리하지 않아서 영속 컨텍스트에서 빠져버림*/

            // 😃섹션5 단방향 연관관계
            // 팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);
            // 회원 저장
            Member1 member = new Member1();
            member.setName("member1");
//            member.setTeamId(team.getId); // 외래키 식별자를 직접 다룸
            member.setTeam(team); // 단방향 연관관계 설정, 참조 저장
            em.persist(member);

            // 영속성 컨텍스트 말고 DB에서 데이터 가져오는 쿼리가 보고싶다면!
            em.flush(); // 강제 호출하고
            em.clear(); // 영속성 컨텍스트를 초기화 시켜버리고

            // 조회
            Member1 findMember = em.find(Member1.class, member.getId());

//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId);

            Team findTeam = findMember.getTeam();

            System.out.println("findTeam = " + findTeam.getName()); // soutv단축키

            // 회원의 팀수정
            Team newTeam = em.find(Team.class, 100L);
            findMember.setTeam(newTeam);

            // 😃섹션5 양방향 연관관계
            List<Member1> members = findMember.getTeam().getMembers();

            for (Member1 m : members) {
                System.out.println("m.getUserName() = " + m.getName());
            }

            // 양방향 연관관계와 연관관계의 주인
            Team team2 = new Team();
            team.setName("TeamB");
            em.persist(team);

            Member1 member2 = new Member1();
            member.setName("member2");
            member.setTeam(team); // ✅순수한 객체 관계를 고려하면 항상 양쪽 다 값을 입력해야한다.
            em.persist(member);

            team.getMembers().add(member2); // ✅순수한 객체 관계를 고려하면 항상 양쪽 다 값을 입력해야한다.

            tx.commit(); // 커밋을 꼭 해줘야한다 ⭐이때 DB에 저장되는 쿼리문 나오는거임!
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 자원을 다 쓰면 꼭 닫아줘야만 데이터베이스 커넥션 반환 되거나 함
        }
        emf.close(); // WAS가 내려갈때 이 emf를 닫아줘야 내부적으로 리소스가 릴리즈됨
    }
}
