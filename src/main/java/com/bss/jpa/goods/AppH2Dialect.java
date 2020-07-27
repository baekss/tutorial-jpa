package com.bss.jpa.goods;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

//H2Dialect을 상속 받은 객체를 만들고
public class AppH2Dialect extends H2Dialect {
	
	//DB에 만들어 놓은 펑션을 등록한다.
	public AppH2Dialect() {
		registerFunction( "stopword_removal", new StandardSQLFunction( "stopword_removal", StandardBasicTypes.CLASS ) );
	}
}
//persistence.xml에 hibernate.dialect 속성을 패키지명 포함 AppH2Dialect 로 설정한다.
//query에서 사용할 땐 function('stopword_removal', 인자값) 형태로 사용