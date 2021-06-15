# Mysite04

* war

* 설정
	* pom.xml
	* web.xml
		* DispatcherServlet
			* init-param
				* ContextClass : AnnotationConfigWebApplicationContext
				* ContextConfigLocation : WebConfig.java 경로 설정
		* listener
			* context-param
				* ContextClass : AnnotationConfigWebApplicationContext
				* ContextConfigLocation : AppConfig.java 경로 설정
	* mysite 설정 (com.douzone.mysite.config)
		* WebConfig.java
			* autoScan : controller, exception
			* auto-proxy
			* import 
				* MvcConfig.class
				* MessageConfig.class
				* Security.class
				* FileuploadConfig.class
		* AppConfig.java
			* autoScan : aspect, service, repository
			* auto-proxy 
			* import
				* 
				* MyBatisConfig
	* 회사 설정 (com.douzone.config)
		* web
			* MvcConfig.java (web mvc 설정)
				* View Resolver 설정 : prefix, suffix
				* Message Converter 설정
					* StringHttpMessageConverter : string type - utf-8 (한글 되도록)
					* MappingJacksonHttpMessageConverter : json type (string 이외의 message type되도록)
				* Default Servelt Handler : servelt container의 default servlet 위임 handelr
			* MessageConfig.java
				* message_ko - basename 설정
					* property 설정 : resources 내에 존재
				* valid check시 defaultMessage가 아닌 custom message 사용하기 위함
			* FileuploadConfig.java
				* multipartResolver : 여러 img 파일 업로드 가능
				* file upload mapping : img 저장 경로 
			* SecurityConfig.java
				* argumentResolver
				* interceptor
					* mapping
		* app
			* DBConfig
				* connection pool dataSource 설정 : db 연결관련, 초반 10개 connection, 최대 100개 connection
				* transaction 설정
			* MyBatisConfig
				* MyBatis SqlSessionFactory Bean : sql Session Factory
				* sqlSessionTemplate : sql Session 만들기
			
### overview

```
[src]
  |---[main]
       |---[java]
       |      |---/com
       |            |---/douzone
       |                    |---/config (회사의 config)
       |                    |       |---/app
       |                    |       |     |---DBConfig.java
       |                    |       |     |---MyBatisConfig.java (or HibernateConfig.java)
       |                    |       |---/web
       |                    |             |---MvcConfig.java
       |                    |             |---SecurityConfig.java
       |                    |             |---MessageConfig.java
       |                    |             |---FileUploadConfig.java
       |                    |---/mysite
       |                            |---controller
       |                            |---service
       |                            |---repository
       |                            |---vo
       |                            |---exception
       |                            |---aop
       |                            |---config
       |                                  |---AppConfig.java
       |                                  |---WebConfig.java
       |---[resources]
       |      |---logback.xml
       |      |---/com
       |            |---/douzone
       |                    |---/mysite
       |                            |---/config
       |                                  |---/app
       |                                  |     |---jdbc.properties (port, db name, db login 정보)
       |                                  |     |---/mybatis
       |                                  |             |---configuration.xml (mapper loading 정보, alias 설정)
       |                                  |             |---/mappers
       |                                  |                     |---user.xml
       |                                  |                     |---board.xml
       |                                  |                     |---guestbook.xml
       |                                  |---/web
       |                                        |---message_ko.properties
       |                                        |---fileupload.properties (upload 위치 등)
```
			* 