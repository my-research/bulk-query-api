# 중앙 집중화된 설정 정보 저장소

> **중앙 집중화된 설정 정보 관리**를 위해서 시스템 외부에서 관리하도록 돕는 Spring Cloud Config 를 이용합니다.

- 사용 기술
  - Spring Cloud Config
  - Git, Git Hosting (Github)
  - Spring Boot
- [Spring Cloud Config Docs](https://cloud.spring.io/spring-cloud-config/reference/html/)
- [블로그 정리](https://wonit.tistory.com/502?category=854728)
- [실습 소스코드](https://github.com/my-research/centralized-configuration-server)

# Client

- 요청

```
http://localhost:8888/{application}/{profile}/{label}
```

```java
public class Application {

  private final Console menuConsole = new MenuConsole();
  private final GameFinder gameFinder = new GameFinder();

  public static void main(String[] args) {
    int gameNumber = menuConsole.queryGameNumber();

    GameFinder finder = new GameFinder();
    Game game = finder.findBy(inputGameNumber);

    while(true) {
      game.play();
      int retry = menuConsole.queryRetry();
    }

  }
}

public class BaseballGame {

  BaseballConsole console = new BaseballConsole();

  public void play() {
    while (playLoop()) {}
  }

  private boolean playLoop() {
    int number = console.queryNumber();
  }
}

public class Baseball {

  List<Integer> myNumbers = new ArrayList();

  public Result compare(UserShot userShot) {
    List<Integer> numbers = userShot.getNumbers();

    for (int i = 0; i < myNumbers; i++) {
      numbers.get(i);
    }
  }
}

public class UserShot {
  List<Integer> myNumbers = new ArrayList();

  public
}
```

# 일 잘하는 방법

- 시간, 프로세스의 흐름에 의해서 설명하기보다 핵심을 먼저 이야기하고 뒤에서 받쳐주는 수식어를 통해서 핵심의 신뢰를 불어넣는다
  - 일을 할 때는 목적과 효과에 대해서 계속해서 인지하자
