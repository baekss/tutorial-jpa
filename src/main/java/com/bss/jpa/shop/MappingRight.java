package com.bss.jpa.shop;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Client;
import com.bss.jpa.shop.domain.Order;
import com.bss.jpa.shop.domain.OrderStatus;

public class MappingRight {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		Client client = new Client();
    		client.setName("홍길동");
    		client.setCity("경기");
    		client.setStreet("경기로");
    		client.setZipcode("012-345-6-789");
    		
    		Order order = new Order();
    		order.setClient(client);
    		order.setOrderDate(LocalDateTime.now());
    		order.setStatus(OrderStatus.ORDER);
    		
    		//mappedBy 속성. 쿼리에 영향을 주는 필드는 아니어도 1차 캐시에 넣어놓고 쓸 땐 client의 orders 값이 존재해야 사용 가능하니 무조건 넣어두자
    		//client.getOrders().add(order); //Order setClient 메소드에서 한번에 다 셋팅한다.
    		
    		em.persist(client); //SQL 쓰기지연 저장소에 insert 쿼리 놓음
    		em.persist(order); //SQL 쓰기지연 저장소에 insert 쿼리 놓음
    		
    		//em.flush(); //client.getOrders에 add를 신경쓰지 않았다면 사용하자
    		//em.clear(); //client.getOrders에 add를 신경쓰지 않았다면 사용하자
    		
    		Client c = em.find(Client.class, client.getId()); //1차 캐시에서 얻어옴
    		c.getOrders().stream().forEach(o->System.out.println("결과 : "+o.getId())); //결과 : order의 id 값 출력
    		
    		//만일 client.getOrders에 add를 신경쓰지 않았다면? 1차 캐시에서 얻어온 c.getOrders()는 empty List 이다. em.flush(), em.clear()로 select 쿼리실행해야 orders 값 획득 가능.
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
