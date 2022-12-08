# projectBlog
1. Usecase 작성
![img](https://user-images.githubusercontent.com/110814973/206435978-5dbd3c82-e741-4b25-831f-014eea9f7ce4.png)

2. API 설계 CRUD
기능	메서드	URL	반환
전체 게시물 조회	GET	/	List<Post>
선택 게시물 조회	GET	/posts/{id}	Long
게시물 작성	Post	/posts	Post
게시물 수정	Put	/posts/[id}	Long
게시물 삭제	Delete	/posts/{id}	Long
3. 프로젝트 시작
** 엔티티는 DB의 테이블( 객체 지향적 언어의 속성을 살려 자바 관점에서 본 객체 )과 속성이 같아야 한다.



22.12.08 트러블 슈팅

Post 생성자를 만들 때 Dto 인자를 주입하며 @RequestBody를 사용하지 않음

(@ResponseBody와 혼동: @RestController) -> 값이 할당되지 않음 : 해결

+@RequestBody로 받을 때는 반드시 받는 객체가 기본 생성자를 가지고 있어야 한다( 인자의 필드가 하나인 경우에, 인자를 가진 생성자 메서드만 있으면 안된다, 둘 다 있는 건 가능 + 하나만 있는 경우는 Setter가 있어도 안된다 )



&& 메모장 프로젝트 실습보다 추가된 점 :

- 프론트엔드에서 받아오는 정보가 추가되었다. -> dto 객체의 속성값이 증가했다.

- 키 값을 가지고 게시물을 선택 조회 및 수정 / 삭제하는 기능



22. 12. 08 트러블 슈팅

1. 게시물 선택 조회 기능을 만들때 지식과 경험 부족으로 인한 컨트롤러, 서비스 구현이 막힘

시도 1) List<Post> 타입으로 반환하는 @GetMapping findById 메서드 작성 : postRepository에서 타입의 충돌이 발생 -> +Optional<Post>을 쓰라고 함??

2) JPA repository 클래스를 읽다가.. findById()(처음 시도한 조회메서드)가 없는 것을 발견?

(나뭇잎달려있는건 어디서 오는걸까) -> getById(Long id)로 바꾸자 반환해야 하는 타입이 Post 엔티티로 바뀜 ->

관련 컨트롤러 및 서비스에 반영

+ 테스트 : url에 /api/posts/1 입력 -> HTTP 500 에러

3) Crud Repository에 findById() 메서드를 찾았으나, Optional<T> 타입이다 ~ 쓸 줄 모르는 타입.

** 선택 조회 메서드 구현 해결 + Optional<Post>에 대한 공부를 더 해야한다.



2. 비밀번호 확인 기능 구현 : 고민과 구글링을 통해 비밀번호만 인스턴스로 갖고 있는 비밀번호 확인용 Dto를 만들어 Dto로 들어온 비밀번호와 레포지토리의 id값으로 조회한 이미 저장된 게시물의 비밀번호를 비교하는 메서드를 서비스에 구현

다음 이슈는 이 거를 어떻게 호출할 지 -> 비교 메서드를 boolean으로 두고 서비스의 업데이트에서 true....

-> 한참 고민하다가 비밀번호용 Dto가 필요없다는 것을 깨닫고(어차피 Dto vs 레포지토리이므로) RequestDto로 변경 후 재시도 : 해결, 삭제 기능도 동일하게 해결할 수 있을 것으로 기대



4. API 명세서 작성 및 질문에 대한 고민
메서드	URL	요청	응답
GET(전체 게시물 조회)	/api/posts	-	
[
    {
        "createdAt": "2022-12-08T20:01:31.935827",
        "modifiedAt": "2022-12-08T20:01:31.935827",
        "id": 2,
        "title": "제목2",
        "username": "글쓴이2",
        "contents": "내용을 또 작성했습니다",
        "password": "1111"
    },
    {
        "createdAt": "2022-12-08T19:59:01.351124",
        "modifiedAt": "2022-12-08T19:59:01.351124",
        "id": 1,
        "title": "제목",
        "username": "글쓴이",
        "contents": "내용을 작성했습니다",
        "password": "1111"
    }
]
GET(선택 게시물 조회)	/api/posts/{id}	-	
{
    "createdAt": "2022-12-08T19:59:01.3511237",
    "modifiedAt": "2022-12-08T19:59:01.3511237",
    "id": 1,
    "title": "제목",
    "username": "글쓴이",
    "contents": "내용을 작성했습니다",
    "password": "1111"
}
POST	/api/posts	
{
    "title":"제목",
    "username":"글쓴이",
    "contents":"내용을 작성했습니다",
    "password":"1111"
}
{
    "createdAt": "2022-12-08T19:59:01.3511237",
    "modifiedAt": "2022-12-08T19:59:01.3511237",
    "id": 1,
    "title": "제목",
    "username": "글쓴이",
    "contents": "내용을 작성했습니다",
    "password": "1111"
}
PUT	/api/posts/{id}	
{
    "title":"제목3",
    "username":"글쓴이3",
    "contents":"내용을 수정했습니다",
    "password":"1111"
}
{
    "createdAt": "2022-12-08T19:59:01.351124",
    "modifiedAt": "2022-12-08T20:02:55.144295",
    "id": 1,
    "title": "제목3",
    "username": "글쓴이3",
    "contents": "내용을 수정했습니다",
    "password": "1111"
}
DELETE	/api/posts/{id}	
{
    "title":"제목3",
    "username":"글쓴이3",
    "contents":"내용을 수정했습니다",
    "password":"1111"
}
true

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



Controller에는 body 방식으로 받은 Json 데이터와 엔티티에서 자동 부여된 id 값을 매개변수로 받고 각각의 메서드와 url을 구분하여 작성하였으며, 각 기능별로 해당하는 서비스로 id와 객체에 할당한 Json 값을 전달

서비스와의 연결을 위한 불변 객체 선언이 돼있다.



Service에는 각 기능별 메서드가 작성되어 있으며, 레포지토리와의 연결을 위한 불변 객체 선언이 돼있다.



Repository는 JPARepository를 상속받고 추후 발생가능한 다중 상속을 위해 인터페이스로 구현하였다.

선택 게시물 조회 같은 경우 id 값을 매개변수로 한 findById() 메서드를 사용했고, 타입을 Optional<Post>로 사용했지만,

아직 Optional 타입을 정확하게 이해하지 못했다.



엔티티 Post에는 Dto에서 받은 Json 데이터가 각 메서드에 맞게 초기화를 거쳐 데이터베이스와 일치하는 인스턴스가 되어 해당 기능을 수한다.
