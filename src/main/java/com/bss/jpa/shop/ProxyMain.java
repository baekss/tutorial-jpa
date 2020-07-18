package com.bss.jpa.shop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Hibernate;

import com.bss.jpa.shop.domain.Client;

public class ProxyMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		Client client = new Client();
    		client.setName("여포");
    		client.setCity("서울");
    		client.setStreet("서울로");
    		client.setZipcode("012-345-6-789");
    		em.persist(client);
    		
    		Client client2 = new Client();
    		client2.setName("화웅");
    		client2.setCity("경기");
    		client2.setStreet("경기로");
    		client2.setZipcode("013-345-5-777");
    		em.persist(client2);
    		
    		em.flush();
    		em.clear();
    		
    		//Client realCient = em.find(Client.class, client.getId());
    		//프록시 객체는 target(Client.class)을 상속받은 객체가 리턴 됨(다만, 1차 캐시에 이미 실제 entity 있으면 프록시 객체가 아닌 실제 entity 리턴. 바로 위 코드 참고)
    		Client proxyClient = em.getReference(Client.class, client.getId());
    		//프록시 초기화는 영속 컨텍스트를 통해 이루어지므로 프록시객체 사용 전에 영속화를 끊지마라.
    		System.out.println("프록시 객체에서 필드 값을 사용 때 조회 쿼리 수행 및 프록시 초기화 진행 : "+ proxyClient.getName()+" by "+proxyClient.getClass());
    		//em.clear(); //영속 컨텍스트 clear 했다고 proxyClient가 참조하는 객체 주소값 마저 없애는 것은 당연히 아니다.
    		//System.out.println(proxyClient.getCity()); //proxy객체도 1차캐시 활용
    		
    		//1차 캐시에 있으므로 find시 프록시 객체 리턴 받음
    		Client realCient = em.find(Client.class, client.getId());
    		System.out.println(realCient.getClass());//class com.bss.jpa.shop.domain.Client$HibernateProxy$cqJsU4ec
    		System.out.println("entity 비교는 instanceof로 : " + (proxyClient instanceof Client));
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
