package com.soojung.jpastudy;

import com.soojung.jpastudy.entity.Child;
import com.soojung.jpastudy.entity.Parent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CascadeParentMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜젝션 시작

//            saveNoCascade(em);
//            saveWithCascade(em);
            deleteWithCascade(em);

            tx.commit();    // 트랜젝션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();  // 트랜젝션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }
        emf.close(); // 엔티티 매니저 팩토리 종료
    }

    private static void saveNoCascade(EntityManager em) {
        Parent parent = new Parent();
        em.persist(parent);

        // 1번 자식 저장
        Child child1 = new Child();
        child1.setParent(parent);   // 자식 -> 부모 연관관계 설정
        parent.getChildren().add(child1);   // 부모 -> 자식
        em.persist(child1);

        // 2번 자식 저장
        Child child2 = new Child();
        child2.setParent(parent);   // 자식 -> 부모 연관관계 설정
        parent.getChildren().add(child2); // 부모 -> 자식
        em.persist(child2);

    }

    private static void saveWithCascade(EntityManager em) {
        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        child1.setParent(parent);   // 연관 관계 추가
        child2.setParent(parent);   // 연관 관계 추가

        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        // 부모 저장, 연관된 자식들 저장
        em.persist(parent);

    }

    // TODO 삭제가 안되욤..
    private static void deleteWithCascade(EntityManager em) {
        Parent parent = em.find(Parent.class, 2);
        parent.getChildren().remove(0); // 자식엔티티를 컬렉션에서 제거 
    }
}
