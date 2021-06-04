# mysite03

* desc
	* war project - web.xml 필요

```
com.douzone.mysite.controller
com.douzone.mysite.service
com.douzone.mysite.repository
com.douzone.mysite.vo
```
	
* 설정
	* pom.xml
		* spring web mvc, context, mvc
		* jstl
		* mariadb
		* build 설정
	* web.xml
		* applicationContext.xml (root application context) 설정
			* listener에서 사용할 Context Parameter설정
		* spring-servlet.xml (web application context) 설정
			* servlet
		* EncodingFilter 설정
			* 이 filter는 spring-web에서 제공해줌
	* applicationContext.xml
		* scanning : Repository, Service 경로 설정
			* spring의 3단 구성 (servlet, service, repository ) annotation 설정
				* Servlet --(사용)--> Service --(사용)--> Repository
		* 이 xml을 사용해서 Root Application Context만듦
			* ContextLoadListner 만들어지면 listener라서 ContextInitialize()가 실행되면서 application.xml참고하여 Root Application Context만듦
	* spring-servlet.xml
		* scanning : Controller 경로 설정		
		* defaultServletHandler 설정
			* defaultServlet : 정적 자원 처리
				* DispatcherServlet의 경로가 "/" : 모든 request가 이 servlet으로 가기에 defaultServlet으로 요청이 가지 않음
				* css등이 처리 되게 하기 위해서는 defaultServlet으로 갈 수 있도록 HandlerMapping내에 defaultServletHandler 설정 해야함
			* defaultServlet 그림 넣기
		
 		
	