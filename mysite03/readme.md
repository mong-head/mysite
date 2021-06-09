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
			* spring-context
				* common-logging 제외 (JCL 제외) : LogBack 사용 위함
		* LogBack
			*SLF4J 구현체
		* spring jdbc : MyBatis db 연결 설정 관련
		* common dncp (DataSource - connection pool) : MyBatis db 연결 관련
		* MyBatis
		* jstl
		* mariadb
		* build 설정
		* aspect 설정 : advisor관련
		* common fileUpload 설정 : 파일 업로드 관련
		
	* web.xml
		* applicationContext.xml (root application context) 설정
			* listener에서 사용할 Context Parameter설정
		* spring-servlet.xml (web application context) 설정
			* servlet
		* EncodingFilter 설정
			* 이 filter는 spring-web에서 제공해줌
		* 404, 500 error page 설정
	* applicationContext.xml
		* scanning : Repository, Service, Aspect 경로 설정
			* spring의 3단 구성 (servlet, service, repository ) annotation 설정
				* Servlet --(사용)--> Service --(사용)--> Repository
		* 이 xml을 사용해서 Root Application Context만듦
			* ContextLoadListner 만들어지면 listener라서 ContextInitialize()가 실행되면서 application.xml참고하여 Root Application Context만듦
		* MyBatis 설정 : SqlSessionFactory만들기 위해 db 연결 설정
			* Bean 설정
			* SqlSessionFactoryTemplate
		* auto proxy 설정 (aspect관련)
			* servlet설정에서도 하는 것이 좋음
	* spring-servlet.xml
		* scanning : Controller 경로 설정, Exception 경로 설정
		* defaultServletHandler 설정
			* defaultServlet : 정적 자원 처리
				* DispatcherServlet의 경로가 "/" : 모든 request가 이 servlet으로 가기에 defaultServlet으로 요청이 가지 않음
				* css등이 처리 되게 하기 위해서는 defaultServlet으로 갈 수 있도록 HandlerMapping내에 defaultServletHandler 설정 해야함
			* defaultServlet 그림 넣기
		* Sercurity 관련 설정
			* argument-resolver 등록 (AuthUserHandlerMethodArgumentResolver)
			* interceptor 설정
		* auto proxy 설정 (aspect관련)
		* file upload 설정
			* file upload mapping 관련 설정
			* Multipart Resolver 설정
	* configuation.xml (resources내에 위치)
		* MyBatis 설정 : mapper file loading 설정
 	* mappers xml
		* guestbook.xml : guestbook query 와 object(Guestbook
	