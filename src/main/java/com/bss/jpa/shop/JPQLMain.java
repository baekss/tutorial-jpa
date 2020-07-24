package com.bss.jpa.shop;

import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Order;
import com.bss.jpa.shop.domain.OrderStatus;

public class JPQLMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		//JPQL은 테이블이 아닌 객체를 대상으로 질의한다.
    		//객체명은 엔티티명@Entity(name="Order")으로 하여 질의 한다.
    		//객체는 별칭(Order o)을 줘서 사용한다.
    		Stream<Order> stream = em.<Order>createQuery("SELECT o FROM Order o WHERE status = :status", Order.class)
    									.setParameter("status", OrderStatus.CANCEL).getResultStream();
    		System.out.println("------------------Order 객체에 대한 JPQL 끝------------------");
    		stream.forEach(o->System.out.println(o.getStatus())); //연관관계인 Client를 select 하는 쿼리가 날라감
    		System.out.println("------------------JPQL RESULT 끝------------------");
    		Order order49 = em.find(Order.class, 49L); //OrderStatus.ORDER 데이터여서 select쿼리 날리고 1차 캐시에 넣음
    		System.out.println("------------------PK 49 Order 끝------------------");
    		Order order50 = em.find(Order.class, 50L); //OrderStatus.CANCEL 데이터여서 이미 JPQL로 select되고 1차 캐시에 넣어져 있어서 select쿼리 날리지 않음
    		System.out.println("------------------PK 50 Order 끝------------------");
    		tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		tx.rollback();
    	}finally{
    		em.close();
    	}
    	
    	emf.close();
	}

}
