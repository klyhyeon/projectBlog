# projectBlog
이번에 시도하는 것들 :

ERD

JWT를 활용하기

JPA 연관관계 설정

1. ERD (Entity Relation Diagram)
   작성중..

2. API 명세서
1) 새로 구현하는 기능
작성중..
2) 
2) 수정하는 기능
작성중..

3. 프로젝트 시작
   추가하는 클래스

Entity User

UserService

UserRepository

SignupRequestDto

LoginRequestDto

JwtUtil



22.12.16 트러블 슈팅

- 클래스를 생성한 후 테스트를 위해 실행 : secret key 설정을 깜빡함 -> app 설정에 추가

- 회원가입 기능을 테스트 하기 위해 서버 구동 및 Postman으로 POST 쿼리 발송 :

email 값이 제대로 전송되지 않음 : Http 500, not-null property references a null or transient value 오류

-> 클래스 간 값의 이동이 제대로 되지 않는 경우, 내 경우엔 ajax 형식으로 UserController에서 값을 받기 때문에 @ResponeBody, 파라미터에 @RequestBody를 붙여줘야 했으나 이를 잊어 배달사고 발생 ~ 해결

- 회원가입 시 아이디, 비밀번호의 글자 수 및 속성 알파벳, 숫자 제한 조건 추가하기 : UserService에 추가

+ 정규표현식 사용

참조한 글: https://zzang9ha.tistory.com/322


[Java] - 자바 정규 표현식(Regular Expression) + 2021 카카오 코테(신규 아이디 추천)

안녕하세요~ 이전에 운영하던 블로그 및 GitHub, 공부 내용을 정리하는 Study-GitHub 가 있습니다! • 네이버 블로그 • GitHub • Study-GitHub • 🐔 ✔ 정규 표현식(Regular Expression) 안녕하세요, 이번에 정

zzang9ha.tistory.com
- JWT 토큰 값이 올바르게 생성되지 않습니다 : 쿠키 헤더가 받은 Bearer 값이 올바르지 않은 쿠키라고 하며 JWT 토큰이 생성되지 않음(로그인을 제대로 하지 않고 글 작성한 경우 발생하는 오류였다) -> PostService의 createPost 메서드에서 토큰을 확인한 후 토큰에 저장된 사용자 정보를 조회 및 요청 DTO의 내용을 객체에 담아 Flush 하는 코드를 잊고 작성하지 않음 : 작성 후 테스트 : 로그인까진 잘 되나, 글 작성 POST 메서드를 날리면 오류는 없지만 DB에 저장이 되지 않고, null 출력 + 디버깅 : token 값이 null이다. -> Postman에서 JWT를 저장해야하는데 이를 놓침 -> JWT 만드는 방법을 공부하다 Postman에서 출력되는 것 확인 후 Postman에 입력 : 해결



+Optional 타입 메서드는 void 설정할 수 없다?



22.12.18

수정/ 삭제메서드 진행중..





연관관계 설정 :

회원가입 하며 만들어 둔 JWT 토큰을 참조하여 게시물 작성 시 해당 토큰을 불러오고 검증을 통해 그 회원이 맞는 지 인증 후 게시물 작성 기능이 수행될 수 있도록 인가

수정/ 삭제 기능도 동일하게 적용, 다만 저번 과제 때 boolean으로 접근해 만들어둔 비밀번호 확인 기능이 더 이상 필요가 없어 수정/ 삭제 메서드 및 PostController에 있는 관련 메서드의 타입을 void로 변경

4. 체크포인트
   처음 설계한 API 명세서에 변경사항이 있었나요? 변경 되었다면 어떤 점 때문 일까요? 첫 설계의 중요성에 대해 작성해 주세요!

-

ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?

-

JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요?

-

반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?

-

만약 댓글 기능이 있는 블로그에서 댓글이 달려있는 게시글을 삭제하려고 한다면 무슨 문제가 발생할까요? Database 테이블 관점에서 해결방법이 무엇일까요?

-

IoC / DI 에 대해 간략하게 설명해 주세요!

-
======================================================================================================================================================
1. Usecase 작성
![img](https://user-images.githubusercontent.com/110814973/206435978-5dbd3c82-e741-4b25-831f-014eea9f7ce4.png)


2. API 설계 CRUD
![image](https://user-images.githubusercontent.com/110814973/206610813-d2184c5f-d331-4ca7-a69d-e9046a30cd3d.png)


3. 프로젝트 시작

** 엔티티는 DB의 테이블( 객체 지향적 언어의 속성을 살려 자바 관점에서 본 객체 )과 속성이 같아야 한다.

22.12.08 트러블 슈팅

Post 생성자를 만들 때 Dto 인자를 주입하며 @RequestBody를 사용하지 않음

(@ResponseBody와 혼동: @RestController) -> 값이 할당되지 않음 : 해결

+@RequestBody로 받을 때는 반드시 받는 객체가 기본 생성자를 가지고 있어야 한다( 인자의 필드가 하나인 경우에, 인자를 가진 생성자 메서드만 있으면 안된다, 둘 다 있는 건 가능 + 하나만 있는 경우는 Setter가 있어도 안된다 )



&& 메모장 프로젝트 실습보다 추가된 점 :

- 프론트엔드에서 받아오는 정보가 추가되었다. -> dto 객체의 속성값이 증가했다.

- 키 값을 가지고 게시물을 선택 조회 및 수정 / 삭제하는 기능



22.12.08 트러블 슈팅

1. 게시물 선택 조회 기능을 만들때 지식과 경험 부족으로 인한 컨트롤러, 서비스 구현이 막힘

시도 1) List<Post> 타입으로 반환하는 @GetMapping findById 메서드 작성 : postRepository에서 타입의 충돌이 발생 -> +Optional<Post>을 쓰라고 함??

2) JPA repository 클래스를 읽다가.. findById()(처음 시도한 조회메서드)가 없는 것을 발견?

(나뭇잎달려있는건 어디서 오는걸까) -> getById(Long id)로 바꾸자 반환해야 하는 타입이 Post 엔티티로 바뀜 ->

관련 컨트롤러 및 서비스에 반영

+ 테스트 : url에 /api/posts/1 입력 -> HTTP 500 에러

3) Crud Repository에 findById() 메서드를 찾았으나, Optional<T> 타입이다 ~ 쓸 줄 모르는 타입.

** 선택 조회 메서드 구현 해결 + Optional<Post>에 대한 공부를 더 해야한다.


2. 비밀번호 확인 기능 구현 : 고민과 구글링을 통해 비밀번호만 인스턴스로 갖고 있는 비밀번호 확인용 Dto를 만들어 Dto로 들어온 비밀번호

와 레포지토리의 id값으로 조회한 이미 저장된 게시물의 비밀번호를 비교하는 메서드를 서비스에 구현

다음 이슈는 이 거를 어떻게 호출할 지 -> 비교 메서드를 boolean으로 두고 서비스의 업데이트에서 true....

-> 한참 고민하다가 비밀번호용 Dto가 필요없다는 것을 깨닫고(어차피 Dto vs 레포지토리이므로) RequestDto로 변경 후 재시도 : 해결, 삭제 

기능도 동일하게 해결할 수 있을 것으로 기대



4. API 명세서 작성 및 질문에 대한 고민
![1](https://user-images.githubusercontent.com/110814973/206437010-ca0a73ec-71c4-4748-8932-f1128a913d10.png)
![2](https://user-images.githubusercontent.com/110814973/206437020-e8779df6-77ef-4c3b-9f16-3ee2b47a7199.png)

- 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)

+ param : 주소에 포함된 변수를 담는다/ query : 주소 바깥 ? 이후의 변수를 담는다/ body : Json, XML 등 데이터를 받는다

Json 데이터를 받기 위해 body 방식을 사용했다.



- 어떤 상황에 어떤 방식의 request를 써야하나요?

C : POST 

R : GET

U : PUT

D : DELETE



- RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?

HTTP 통신 프로토콜에 따라 데이터 작업이 원활하게 이루어졌으므로 그렇다고 생각한다.



- 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)

Dto클래스에는 Json 형식을 받은 사용자의 요청을 속성화하여 객체로 다른 클래스에서 사용가능하도록 설계


Controller에는 body 방식으로 받은 Json 데이터와 엔티티에서 자동 부여된 id 값을 매개변수로 받고 각각의 메서드와 url을 구분하여 작성
하였으며, 각 기능별로 해당하는 서비스로 id와 객체에 할당한 Json 값을 전달
서비스와의 연결을 위한 불변 객체 선언이 돼있다.


Service에는 각 기능별 메서드가 작성되어 있으며, 레포지토리와의 연결을 위한 불변 객체 선언이 돼있다.


Repository는 JPARepository를 상속받고 추후 발생가능한 다중 상속을 위해 인터페이스로 구현하였다.
선택 게시물 조회 같은 경우 id 값을 매개변수로 한 findById() 메서드를 사용했고, 타입을 Optional<Post>로 사용했지만,
아직 Optional 타입을 정확하게 이해하지 못했다.


엔티티 Post에는 Dto에서 받은 Json 데이터가 각 메서드에 맞게 초기화를 거쳐 데이터베이스와 일치하는 인스턴스가 되어 해당 기능을 수행한다.
