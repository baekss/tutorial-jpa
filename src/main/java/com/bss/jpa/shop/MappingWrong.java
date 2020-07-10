package com.bss.jpa.shop;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Client;
import com.bss.jpa.shop.domain.Order;
import com.bss.jpa.shop.domain.OrderStatus;

public class MappingWrong {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		Client client = new Client();
    		
    		Order order = new Order();
    		//order.setClient(client); //이 구문이 빠지면 order의 FK가 null로 셋팅된다.
    		order.setOrderDate(LocalDateTime.now());
    		order.setStatus(OrderStatus.ORDER);
    		em.persist(client); //SQL 쓰기지연 저장소에 insert 쿼리 놓음
    		em.persist(order); //SQL 쓰기지연 저장소에 insert 쿼리 놓음
    		
    		//앞서 persist로 1차 캐시에 넣은 client 객체의 변경감지됨. SQL 쓰기지연 저장소에 update 쿼리 놓음.
    		//앞선 insert시 not null 컬럼이 있을 수도 있고, update 쿼리가 생기는 것도 비효율적이고, 가독성도 좋지 않으니 한번에 셋팅 후 persist 하자.
    		client.setName("홍길동");
    		client.setCity("경기");
    		client.setStreet("경기로");
    		client.setZipcode("012-345-6-789");
    		client.getOrders().add(order); //mappedBy인 필드는 읽기전용으로 jpa에 의해 관리되어 쓰기가 되는 필드가 아니다. 따라서 이 필드를 통해 ORDERS 테이블의 FK 값을 쓸 수 없다.
        	tx.commit(); 
        	//CLIENT_ID  CITY  NAME  	STREET  ZIPCODE
        	//65		 경기 	홍길동 	경기로 	012-345-6-789
        	//ORDER_ID  ORDERDATE  					STATUS  	FK_CLIENT_ID_MAPPED_PK_CLIENT_ID_COLUMN_OF_CLIENT_TABLE  
        	//66 		2020-07-10 23:42:55.992 	ORDER 		null 
    	}catch(Exception e){
    		e.printStackTrace();
    		tx.rollback();
    	}finally{
    		em.close();
    	}
    	
    	emf.close();
	}

}
