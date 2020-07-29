package com.bss.jpa.shop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Client;

public class JPQLJoinMain {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		String innerJoin ="SELECT c FROM Order o inner join o.client c";
    		String leftOuterJoin ="SELECT c FROM Order o left outer join o.client c";
    		String crossJoin ="SELECT c FROM Order o, Client c where o.status = c.name";
    		List<Client> clients = em.createQuery(innerJoin, Client.class).getResultList();
    		clients = em.createQuery(leftOuterJoin, Client.class).getResultList();
    		clients = em.createQuery(crossJoin, Client.class).getResultList();
    		
    		System.out.println("----------------묵시적 조인----------------");
    		//객체 그래프 탐색만으로 조인쿼리가 발생
			/*
			from
			ORDERS order0_ cross 
			join
			Client client1_ 
			inner join
			ORDERS orders2_ 
			    on client1_.CLIENT_ID=orders2_.FK_CLIENT_ID 
			where
			order0_.FK_CLIENT_ID=client1_.CLIENT_ID
			*/
    		em.createQuery("SELECT o.client.orders FROM Order o").getResultList();
    		
    		System.out.println("----------------명시적 조인----------------");
    		//명시적 조인으로 조인쿼리 발생 여부도 예측하고 컬렉션 타입의 필드의 객체그래프 탐색도 도모한다.
    		/*
			from
			Client client0_ 
			inner join
			ORDERS orders1_ 
			    on client0_.CLIENT_ID=orders1_.FK_CLIENT_ID
			*/
    		em.createQuery("SELECT o.status FROM Client c inner join c.orders o").getResultList();
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
