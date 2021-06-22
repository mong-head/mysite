# mysite06

* spring boot

* war project
	* 배포를 위해 war
	* spring boot는 jar로 해도 됨

### 설정

* pom.xml
	* spring boot parent
		* version : 2.5.1
	* spring boot 부모 관리 O
		* spring boot
			* devtools
			* web starter
			* aop starter
			* validator ( version 2.5.1 부터 필요)
			* spring test
		* maria db
		* jackson
		* jstl
		* tomcat jasper : tomcat jsp engine
	* spring boot 부모 관리 X
		* mybatis
		* common DBCP
		* common fileupload
	* for 배포 (deployment)
		* build 정보
			* plugin : web.xml 없어도 에러 메세지 나오지 않게 설정
* customizing (세부 설정)
	* 방법 2가지
		* application.properties
		* application.yml
			* 요즘 많이 사용
* WebConfig.java
	* 
* BootInitializer
	