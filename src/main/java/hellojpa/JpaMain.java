package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜잭션 안에서 일어나야한다
        tx.begin();

        try {
            Order order = new Order();
//            Order.addOrderItem(new OrderItem());

            tx.commit(); // 커밋을 꼭 해줘야한다 ⭐이때 DB에 저장되는 쿼리문 나오는거임!
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 자원을 다 쓰면 꼭 닫아줘야만 데이터베이스 커넥션 반환 되거나 함
        }
        emf.close(); // WAS가 내려갈때 이 emf를 닫아줘야 내부적으로 리소스가 릴리즈됨
    }
}
