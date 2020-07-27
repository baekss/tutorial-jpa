package com.bss.jpa.goods;

import java.util.List;

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
    		movie.setName("코믹영화"); //Goods 테이블
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
    		
    		System.out.println("---------------------TYPE Query---------------------");
    		//@DiscriminatorColumn 으로 생성한 DTYPE 컬럼으로 부모 테이블에서 해당하는 자식 테이블 정보를 조회한다.
    		List<Goods> list = em.createQuery("SELECT g FROM Goods g WHERE TYPE(g) = :p", Goods.class).setParameter("p", Movie.class).getResultList();
    		System.out.println(list.size());
    		
    		/**
	    	select
	            case 
	                when goods0_.name=? then '베스트 영화' 
	                when goods0_.name=? then '인기 영화' 
	                else '그냥 영화' 
	            end as col_0_0_ 
	        from
	            Goods goods0_
    		*/
    		String queryCASE = "SELECT " + 
    							"CASE WHEN g.name = :param1 THEN CONCAT(g.name, '베스트 영화') " +
    							"WHEN g.name = :param2 THEN CONCAT(g.name, '인기 영화') " +
    							"ELSE '그냥 영화' END " +
    							"FROM Goods g";
    		List<String> result = em.createQuery(queryCASE, String.class)
    				.setParameter("param1", "멜로영화")
    				.setParameter("param2", "호러영화")
    				.getResultList();
    		result.forEach(System.out::println);
    		//size 함수는 연관관계에서 OneToMany인 필드의 사이즈를 구할 때 쓴다.
    		
    		//다운캐스팅 컨셉 처럼 자식테이블과 join 후 자식테이블의 컬럼값으로 조회한다.
    		/**
		    select
	            goods0_.id as id2_8_,
	            goods0_.name as name3_8_,
	            goods0_.price as price4_8_,
	            goods0_1_.artist as artist1_0_,
	            goods0_2_.author as author1_1_,
	            goods0_2_.isbn as isbn2_1_,
	            goods0_3_.actor as actor1_12_,
	            goods0_3_.director as director2_12_,
	            goods0_.DTYPE as DTYPE1_8_ 
	        from
	            Goods goods0_ 
	        left outer join
	            Album goods0_1_ 
	                on goods0_.id=goods0_1_.id 
	        left outer join
	            Book goods0_2_ 
	                on goods0_.id=goods0_2_.id 
	        inner join
	            Movie goods0_3_ 
	                on goods0_.id=goods0_3_.id 
	        where
	            goods0_3_.director='손상향'
    		*/
    		em.createQuery("SELECT g FROM Goods g where treat(g as Movie).director = '손상향'", Goods.class).getResultList();
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
