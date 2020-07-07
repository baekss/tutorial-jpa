package com.bss.jpa.tutorial;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.tutorial.entity.User;

public class Read 
{
    public static void main(String[] args) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		User user = new User();
    		//id는 자동생성 전략 썼으므로 null이 되게 한다.
    		user.setUserName("S.S. BAEK");
    		user.setCreateDate(new Date());
    		
    		User user2 = new User();
    		user2.setUserName("홍길동");
    		user2.setCreateDate(new Date());
    		System.out.println("-----------------------");
    		//자동생성 시퀀스 값을 entity의 키값으로 해서 1차 캐시에 넣어야 하므로 call next value for MEMBER_SEQUENCE 쿼리를 먼저 날려서 primary key를 얻어옴
    		//1차 캐시에 시퀀스 값을 키값으로 해서 entity 저장함
    		em.persist(user);
    		em.persist(user2);
    		System.out.println("-----------------------");
    		List<User> list = em.createQuery("SELECT u FROM User u").getResultList(); //persist 한 데이터를 조회에 포함시키게 하기 위해 insert문 flush 발생시킨다.
    		list.stream().map((u)->{return u.getUserName();}).forEach(System.out::println);
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
