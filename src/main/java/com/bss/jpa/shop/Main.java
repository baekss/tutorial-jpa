package com.bss.jpa.shop;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Client;
import com.bss.jpa.shop.domain.Order;
import com.bss.jpa.shop.domain.OrderStatus;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		//ORDERS 테이블의 CLIENT_ID FK 제약으로 인해 auto DDL시 DROP Table 순서 중요하다.
    		Client client = new Client();
    		client.setName("S.S. BAEK");
    		client.setCity("서울");
    		client.setStreet("억수로");
    		client.setZipcode("012-345-6-789");
    		
    		System.out.println("<-------시퀀스로 primary key 가져오느라 call next value for hibernate_sequence------->");
    		em.persist(client);//시퀀스로 primary key 가져오느라 call next value for hibernate_sequence
    		System.out.println("</-------시퀀스로 primary key 가져오느라 call next value for hibernate_sequence------->");
    		
    		Order order = new Order();
    		//반드시 영속화시킨 객체를 set 한다.
    		order.setClient(client);
    		order.setOrderDate(LocalDateTime.now());
    		order.setStatus(OrderStatus.ORDER);
    		System.out.println("<-------시퀀스로 primary key 가져오느라 call next value for hibernate_sequence------->");
    		em.persist(order); //시퀀스로 primary key 가져오느라 call next value for hibernate_sequence
    		System.out.println("</-------시퀀스로 primary key 가져오느라 call next value for hibernate_sequence------->");
    		
    		Order order2 = new Order();
    		order2.setClient(client);
    		order2.setOrderDate(LocalDateTime.now());
    		order2.setStatus(OrderStatus.CANCEL);
    		em.persist(order2);
    		
    		em.flush(); //insert쿼리만 날림. 아직 commit은 안함.
    		em.clear(); //1차 캐시 비움
    		System.out.println("-----------------------flush & clear-----------------------");
    		Order findOrder = em.find(Order.class, order.getId()); //client와 outer join 으로 하여 select 쿼리 날림 Order 결과 얻어옴. insert commit 전이지만 read uncommited(dirty read) 컨셉처럼 이해바람
    		Order findOrder2 = em.find(Order.class, order2.getId()); //client와 outer join 으로 하여 select 쿼리 날림 Order 결과 얻어옴. insert commit 전이지만 read uncommited(dirty read) 컨셉처럼 이해바람
    		Stream<Order> orders = Stream.of(findOrder, findOrder2);
    		//orders 스트림에서 주문번호와 주문상태 보기
    		orders.map(o -> "주문번호 : "+o.getId()+". 주문상태 : "+o.getStatus()).forEach(System.out::println);
    		
    		Stream<Client> clients = Stream.of(findOrder.getClient(), findOrder2.getClient());
    		
    		//clients에는 같은 인원에 대해서 하나로 치부하기 위해 distinct 메소드 호출
    		clients.distinct().flatMap(c->c.getOrders().stream())
    		//select from ORDERS 쿼리 날림 
    		.forEach( (o) -> { System.out.println("주문번호 : "+o.getId()+". 주문상태 : "+o.getStatus()); } );
        	tx.commit(); //insert문 flush and commit
    	}catch(Exception e){
    		e.printStackTrace();
    		tx.rollback();
    	}finally{
    		em.close();
    	}
    	
    	emf.close();
	}

}
