# 1. MVC 테스트 하기
> Controller를 테스트하고자 할때

@SpringBootTest + @AutoConfigureMockMvc는 통합테스트를 할 때, @WebMvcTest는 MVC쪽만 슬라이스(slice) 테스트를 할 때 사용합니다.


<!-- TOC -->

- [1. MVC 테스트 하기](#1-mvc-%ed%85%8c%ec%8a%a4%ed%8a%b8-%ed%95%98%ea%b8%b0)
  - [1.1. `MockMVC` 직접 주입](#11-mockmvc-%ec%a7%81%ec%a0%91-%ec%a3%bc%ec%9e%85)
  - [1.2. `@WebMvcTest`](#12-webmvctest)
  - [1.3. `@SpringBootTest` + `@AutoConfigureMockMvc`](#13-springboottest--autoconfiguremockmvc)

<!-- /TOC -->

## 1.1. `MockMVC` 직접 주입
> 빌더 패턴

```java
private MockMvc mockMvc;

@Before
public void before() {
  	mockMvc = MockMvcBuilders.standaloneSetup(MyController.class)
          	    .alwaysExpect(MockMvcResultMatchers.status().isOk())
           	    .build();
}
```


## 1.2. `@WebMvcTest`
일반적으로 사용하는 MVC테스트용 어노테이션이다.
해당 어노테이션을 명시하고 그림과 같이 MockMvc를 @Autowired하면 해당 객체를 통해 MVC테스트가 가능하다. 

`@WebMvcTest`어노테이션 사용시 `@SpringBootTest`을 같이 사용할 수 없다. MVC 기능만 사용할 거라면 `@WebMvcTest`를 사용하면 된다.

`@WebMvcTest` 사용시 다른 설정들은 자동으로 올리지 않기 때문에 @Repository나 @Resource, @Service, @Component등은 사용할 수 없다. 자동설정하는 영역은 @Controller, @ControllerAdvice, @JsonComponent 등이다.

```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleSpringBootTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SampleService sampleService;

    @Test
    public void hello() throws Exception {
        when(sampleService.getName()).thenReturn("Minwook");

        mockMvc.perform(get("/hello"))
                .andExpect(content().string("Hello World!"));
    }
}

```


## 1.3. `@SpringBootTest` + `@AutoConfigureMockMvc`
@AutoConfigureMockMvc를 통해 MockMvc를 Builder없이 주입 받을 수 있다. 스프링부트의 매력인 Auto Config의 장점이다. 이 애너테이션을 상속받아 controller 테스트들을 진행한다. 

위 설정은 MVC테스트 외 모든 설정을 같이 올린다. AOP도 되고 JPA Repository도 사용가능하다. 실제적으로 동작하는 MVC테스트를 하려면 위 어노테이션을 사용해야 한다. `@AutoConfigureMockMvc`은 `@SpringBootTest`와 같이 사용 가능하다.


```java
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"))
                .andDo(print());
    }
}
```