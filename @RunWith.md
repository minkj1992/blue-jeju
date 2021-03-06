# `@RunWith`
> JUnit 테스트시 사용하는 해당 어노테이션의 역할


`@RunWith(SpringJUnit4ClassRunner.class)`

    @RunWith는 JUnit 프레임워크의 테스트 실행 방법을 확장할 때사용하는 애노테이션이다. SpringJUnit4ClassRunner라는 JUnit용테스트 컨텍스트 프레임워크 확장 클래스를 지정해주면 JUnit이테스트를 진행하는 중에 테스트가 사용할 애플리케이션컨텍스트를 만들과 관리하는 작업을 진행해준다.

    스프링의 JUnit 확장기능은 테스트가 실행되기 전에 딱 한 번만 애플리케이션 컨텍스트를 만들어두고, 테스트 오브젝트가 만들어질 때마다 특별한 방법을 이용해 애플리케이션 컨텍스트 자신을 테스트 오브젝트의 특정 필드에 주입해주는 것이다. <토비의 스프링 3>



@RunWith에 Runner클래스를 설정하면 JUnit에 내장된 Runner대신 그 클래스를 실행한다. 여기서는 스프링 테스트를 위해서 SpringJUnit4ClassRunner라는 Runner 클래스를 설정해 준 것이다.

한 클래스내에 여러개의 테스트가 있더라도 어플리케이션 컨텍스트를 초기 한번만 로딩하여 사용하기 때문에, 여러개의 테스트가 있더라도 처음 테스트만 조금 느리고 그 뒤의 테스트들은 빠르다.

`Junit4 VS Junit5`

    - Junit4 사용시 @SpringBootTest 기능은 반드시 JUnit의 SpringJUnit4ClassRunner 클래스를 상속 받는 @RunWith(SpringRynnver.class)와 함께 사용해야 한다.

    - Junit5 사용시에는 해당 어노테이션은 명시할 필요없다.
