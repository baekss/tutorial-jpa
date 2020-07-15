package com.bss.jpa.goods;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		
    		Movie movie = new Movie();
    		movie.setName("멜로영화"); //Goods 테이블
    		movie.setPrice(50000); //Goods 테이블
    		movie.setDirector("장비"); //Movie 테이블
    		movie.setActor("관우"); //Movie 테이블
    		
    		em.persist(movie);
    		
    		//부모 객체와 inner join
			/*
			from
			Movie movie0_ 
			inner join
			    Goods movie0_1_ 
			        on movie0_.id=movie0_1_.id 
			*/
    		em.find(Movie.class, movie.getId()); //자식 테이블에서 기본키는 PK이자 FK
    		
    		//em.clear(); //clear는 영속 컨텍스트(1차캐시의 영속객체 + 쓰기지연 저장소의 SQL)를 비워버린다. SQL비워버리는 건 이슈다. DB반영 할거면 꼭 clear 전에 flush 쓰자.
    		
    		//상속관계의 모든 자식 객체와 outer join
			/*
			from
			Goods goods0_ 
			left outer join
			Book goods0_1_ 
			    on goods0_.id=goods0_1_.id 
			left outer join
			Movie goods0_2_ 
			    on goods0_.id=goods0_2_.id 
			left outer join
			Album goods0_3_ 
			    on goods0_.id=goods0_3_.id 
			where
			goods0_.id=?
			*/
    		em.find(Goods.class, movie.getId()); //부모 테이블에서 기본키는 PK
    		//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) 일 때는 부모 타입으로 (Goods.class) find시 모든 자식 객체 테이블 union 해서 가져온다. 구체적인 타입이 아니니 당연함.
        	
    		em.flush();
    		em.clear();
    		
    		//clear 했으면 영속객체가 될수 있도록 만든다.
    		Movie mv = em.find(Movie.class, movie.getId());
    		mv.setDirector("손상향");
    		
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
