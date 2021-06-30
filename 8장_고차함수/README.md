## 고차함수란?

다른 함수를 인자로 받거나 함수를 반환하는 함수

코틀린에서는 람다나 함수 참조를 사용해 함수를 값으로 표현할 수 있기때문에,

코틀린에서 고차 함수란 람다나 함수 참조를 인자로 넘길 수 있거나 반환할 수 있는 함수이다.

- 고차함수 정의

  ### 함수 타입

  코틀린에서 **함수 타입을 정의**하려면?

  코틀린의 함수 타입 문법

  그냥 함수를 정의한다면 함수의 파라미터 목록뒤에 오는 Unit 반환 타입 지정자를 생략해도 되지만,
  함수 타입을 선언할 때는 반환 타입을 반드시 명시해야 한다!! (Unit 생략 X)

    ```kotlin
    // 함수 타입을 변수에 할당
    val sum = { x: Int, y: Int → x + y }
    // 코틀린 타입추론으로 인해 람다의 변수 타입을 지정하지 않아도 된다.
    val sum: (Int,Int) -> Int = { x,y -> x + y }
    // 반환 타입이 없는 함수 타입을 지정하려면 반환 타입에 Unit을 넣어주어야 한다.
    val action: () -> Unit = { println(42) }
    // 반환 타입이 널이 될 수 있는 타입을 반환하는 함수 타입
    var canReturnNull: (Int,Int) -> Int? ={x,y->null}
    // 함수 타입 자체가 널이 될 수 있는 타입
    var funOrNull: ((Int,Int) -> Int)? = null
    ```

  ### 인자로 받은 함수 호출

    ```kotlin
    fun twoAndThree(operation: (Int, Int) -> Int) {
      val result = operation(2, 3)
      println("result = $result")
    }

    >>> twoAndThree { a, b -> a + b }
    result = 5
    >>> twoAndThree { a, b -> a * b }
    result = 6
    ```

    ```kotlin
    fun String.filter(predicate: (Char) -> Boolean): String {
      val sb = StringBuilder()
      for (index in 0 until length) {
        val element = get(index)
        if (predicate(element)) sb.append(element) // "predicate" 파라미터로 전달받은 함수를 호출
      }
    	return sb.toString()
    }

    >>> println("ab1c".filter { it in 'a'..'z' }) // 람다를 "predicate" 파라미터로 전달
    ```

  ### 자바에서 코틀린 함수 타입 사용

    ```kotlin
    /* 코틀린 선언 */
    fun processTheAnswer(f: (Int, Int, Int) -> String {
      println(f(10, 10, 10))
    }

    /* 자바 - 람다로 표현 */
    >>> processTheAnswer((number1, number2, number3) -> String.valueOf(number1 + number2 + number3));
    30

    /* 자바 - 익명 함수로 표현 */
    >>> processTheAnswer(
    ...   new Function3<Integer, Integer, Integer, String>() {
    ...     @Override
    ...     public String invoke(Integer number1, Integer number2, Integer number3) {
    ...       return String.valueOf(number1 + number2 + number3);
    ...     }
    ...   }
    ... )
    ```

    ```kotlin
    public inline fun <T> Iterable<T>.forEach(action: (T) -> Unit): Unit {
        for (element in this) action(element)
    }
    ```

    ```java
    List<String> strings = new ArrayList<>();
    strings.add("42");
    CollectionsKt.forEach(strings, s -> {
      System.out.println(s);
      return Unit.INSTANCE;
    });
    ```

  자바에서는 아무래도 코틀린에 비해 깔끔하지 않다.

    ```kotlin
    val strings = listOf("42")
    strings.forEach { print(it) }
    ```

  자바에서는 아무래도 코틀린에 비해 깔끔하지 않다.

  ### 디폴트 값을 지정한 함수 타입 파라미터나 널이 될 수 있는 함수 타입 파라미터

  파라미터를 합수 타입으로 선언할 때도 마찬가지로 디폴트 값을 정할 수 있다.

  **사용이유:**

  메서드를 호출할 때마다 매번 람다를 넘기게 만들면 기본 동작으로도 충분한 대부분의 경우

  함수 호출을 오히려 더 불편하게 만든다는 문제가 있지만, 디폴트 값을 지정하면 이런 문제를 해결 할 수 있음.

    ```kotlin
    fun <T> Collection<T>.joinToString(
      separator: String = ", ",
      prefix: String = "",
      postfix: String = "",
      transform: (T) -> String = { it.toString() }
    ): String {
      val result = StringBuilder(prefix)
      for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(transform(element))
      }
      result.append(postfix)
      return result.toString()
    }

    >>> val letters = listOf("Alpha", "Beta")
    >>> println(letters.joinToString()) <---- 디폴트 변환 함수를 사용
    Alpha, Beta
    >>> println(letters.joinToString { it.toLowerCase() }) <---- 람다를 인자로 전달
    alpha, beta
    >>> println(letters.joinToString(separator = "! ", transform = { it.toUpperCase() }) <---- 이름 붙은 인자 구분을 사용해 람파를 포함하는 여러 인자를 전달
    ALPHA! BETA
    ```

  함수타입은 다른 타입과 마찬가지로 널이 될 수 있는 함수 타입을 사용할 수도 있다.

  하지만 널이 될 수 있는 함수 타입으로 함수를 받으면 코틀린은 NPE 발생 가능성 때문에 컴파일이 안될 것이다.

  해결법으로는 함수 구현부에 null 여부를 명시적으로 검사하거나 ?. 안전한 호출을 이용한다.

    ```kotlin
    fun foo(callback: (() -> Unit)? {
      if (callback != null) {
        callback()
      }
    }
    ```

    ```kotlin
    fun foo(callback: (() -> Unit)? {
      callback?.invoke()
    }
    ```

  함수 타입은 컴파일 시  FunctionN 인터페이스를 구현한 형태로 변경되고 함수가 실행 될 때 FunctionN 의 invoke를 실행하기 때문에
  이 사실을 이용하면 널이 될 수 있는 함수 타입을도 일반 메소드의 실행 처럼 안전 호출 구문으로 callback?.invoke() 처럼 호출할 수 있다.

    - 코틀린 FunctionN 인터페이스

        ```kotlin
        import kotlin.jvm.internal.FunctionBase

        @SinceKotlin("1.3")
        interface FunctionN<out R> : Function<R>, FunctionBase<R> {
            operator fun invoke(vararg args: Any?): R
            override val arity: Int
        }
        ```

    - 코틀린 함수 타입 컴파일 소스

      코틀린 바이트코드를 이용해서 코틀린 함수 타입이 들어있는 컴파일된 코틀린 코드를 살펴보면

        ```kotlin
        // 함수 타입이 반환 타입인 함수 1
        fun plusAndReturnResult(): (Int, Int) -> Int {
          return {x , y -> x + y}
        }
        ```

        ```kotlin
        @NotNull
        public static final Function2 plusAndReturnResult() {
          return (Function2)null.INSTANCE;
        }
        ```

      반환 타입이 Function2 로 컴파일 된 것을 알 수 있다.

        ```kotlin
        public interface Function2<in P1, in P2, out R> : Function<R> {
          /** Invokes the function with the specified arguments. */
          public operator fun invoke(p1: P1, p2: P2): R
        }
        ```

      이렇듯 코틀린 함수 타입은 코틀린 컴파일러에 의해서 FunctionN 인터페이스 타입으로 호출된다.

  ### 함수를 함수에서 반환

  함수 내부에서 프로그램의 상태나 조건에 따라 로직이 달라질 수 있을때 사용하기 유용하다.

    ```kotlin
    fun getShippingCostCalculator(
      delivery: Delivery): (Order) -> Double {
      if (delivery == Delivery.EXPEDITED) {
        return { order -> 6 + 2.1 * order.itemCount }
      }
      return { order -> 1.2 * order.itemCount }
    }

    >>> val calculator = getShippingCostCalculator(Delivery.EXPEDITED)
    >>> val expeditedShippingCost = calculator(Order(10))
    >>> println(expeditedShippingCost)
    27
    ```

    ```kotlin
    data class Person(
      val firstName: String,
      val lastName: String,
      val phoneNumber: String?
    )
    class ContractListFilter {
      var prefix: String = ""
      var onlyWithPhoneNumber: Boolean = false

    	fun getPredicate(): (Person) -> Boolean {
        val startWithPrefix = { p: Person -> p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix) }
        if (!onlyWithPhoneNumber) {
    			return startWithPrefix
    		}
        return { startWithPrefix(it) && it.phoneNumber != null }
      }
    }
    ```

  ### 람다를 활용한 중복 제거

  함수 타입과 람다 식은 재활용하기 좋은 코드를 만들 때 쓸 수 있는 훌륭한 도구이다.

  람다를 활용하면 간결하고 쉽게 중복 코드를 제거할 수 있다.

  웹사이트 방문 기록을 분석하는 프로그램을 예로 살펴보면

    - 사이트 방문 타입 정의

        ```kotlin
        data class SiteVisit(
          val path: String,
          val duration: Double,
          val os: OS
        )
        enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }
        ```

    - 사이트 방문 데이터를 필터를 사용해 조회

        ```kotlin
        val averageWindowsDuration = log
          .filter( it.os == OS.WINDOWS }
          .map(SiteVisit::duration)
          .average()
        ```

        ```kotlin
        val averageWindowsDuration = log
          .filter( it.os == OS.MAC }
          .map(SiteVisit::duration)
          .average()
        ```

      **검색 조건을 하드코딩하게 되면 중복이 발생**

        ```kotlin
        fun List<SiteVisit>.averageDurationFor(os: OS) =
          filter { it.os == os }
          .map(SiteVisit::duration)
          .average()

        >>> println(log.averageDurationFor(OS.WINDOWS))
        >>> println(log.averageDurationFor(OS.MAC))
        ```

      가독성이 좋아졌지만, *"IOS 사용자의 /signup 페이지 평균 방문 시간은?"*와 같은
      조금 더 복잡한 질의에 대한 요구 사항은 람다를 활용하는 편이 낫다.

        ```kotlin
        fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
          filter(predicate)
          .map(SiteVisit::duration)
          .average()

        >>> println(log.averageDurationFor { it.os == OS.IOS && it.path == "/sighup" })
        ```

  객체지향 디자인 패턴의 전략 패턴을 람다 식으로 표현하여 단순화 할 수 있다.

  람다 식이 없다면 인터페이스를 선언하고 구현 클래스를 통해 전략을 정의해야 한다.
  함수 타입을 언어가 지원하면 일반 함수 타입을 사용해 전략을 표현할 수 있고,
  경우에 따라 다른 람다 식을 넘김으로써 여러 전략을 전달할 수 있다.

    - 전략 패턴 단순화

        ```java
        public interface MoveStrategy {
          boolean movable(int speed);
        }

        public class SimpleMoveStrategy implements MoveStrategy {
          @Override
          public boolean movable(int speed) {
            return speed >= 100;
          }
        }

        public class Car {
          private int speed;

          public void run(MoveStrategy moveStrategy) {
            if (moveStrategy.movable(speed)) {
              go();
            }
          }
        }

        public void static main(String... args) {
          Car car = new Car(50);
          car.run(new SimpleMoveStrategy());
        	car.run(spped -> false);
        }
        ```

- 인라인 함수: 람다의 부가 비용 없애기
    - inline 변경자를 함수에 붙이면 컴파일러는 그 함수를 호출하는 모든 문장을 함수 본문에 해당하는 바이트 코드로 바꿔치기 해준다.

      코틀린이 보통 람다를 익명 클래스로 컴파일 하지만 그렇다고 람다 식을 사용할 때마다
      새로운 클래스를 만들지 않느다. 람다가 변수를 포획하면 람다가 생성되는 시점마다
      새로운 무명 클래스 객체가 생긴다. 이럴 경우 무명 클래스 생성에 따른 부가 비용이 들게 되고
      일반 함수를 사용한 구현보다 덜 효율적이게 된다. inline 변경자를 어떤 함수에 붙이면
      컴파일러는 그 함수를 호출하는 모든 문장을 함수 본문에 해당하는 바이트코드로 바꿔치기 해주어
      자바의 일반 명령문만큼 효율적인 코드를 생성할 수 있게 도와준다.

  ### 인라이닝이 작동하는 방식

    ```kotlin
    --- 인라인 함수 정의
    inline fun<T> synchronized(lock: Lock, action: () -> T):T {
      lock.lock()
      try {
        return action()
      }
      finally {
        lock.unlock()
      }
    }

    ---- 인라인 함수 사용
    fun foo(l: Lock) {
      println("Before sync")
      synchronized(l) {
        println("Action")
      }
      println("After sync")
    }

    <컴파일 된 바이트 코드>
    fun __foo__(l: Lock) {
      println("Before sync")
      l.lock()
      try {
        println("Action")
      }
      finally {
        l.unlock()
      }
      println("After sync")
    }
    ```

  synchronized 함수를 inline으로 선언 했기 때문에 함수의 모든 문장을 바이트 코드로 변환되며,

  심지어 함수 타입으로 전달받은 람다 코드의 본문도 인라이닝 되어 바이트 코드로 함께 변환된다.

  인라인 함수를 호출하면서 람다를 넘기는 대신 합수 타입의 변수를 넘기게 되면

  람다 본문은 인라이닝 되지 않고 inline 변경자가 붙은 함수의 본문만 인라이닝 된다.

    ```java
    class LockOwner(val lock: Lock) {
      fun runUnderLock(body: () -> Unit) {
        synchronized(lock, body) <-- 람다 대신 함수 타입인 변수를 인자로 넘김
      }
    }

    <컴파일 된 바이트 코드>
    class lockOwner(val lock: Lock) {
      fun __runUnderLock__(body: () -> Unit) {
        lock.lock()
        try {
          body() <-- 함수 타입의 변수를 넘겨 synchronized를 호출하는 부분에서 람다를 알 수 없으므로 인라이닝 되지 않음
        }
      }
    }
    ```

  ### 인라인 함수의 한계

  함수가 인라이닝 될 때 인자로 전달된 람다 식의 본문이 직접 펼쳐져서 들어가기 때문에 함수가 파라미터로 전달받은 람다를 본문에 사용하는 방식에 있어서 한정적이 된다.

  위의 *인라인 함수 정의하기 2* 에서 본것 처럼 람다를 다른 변수에 저장하고 나중에에 사용하려고 하면 람다를 인라이닝 할 수 없게 된다.

  인라인 함수의 본문에서 람다 식을 바로 호출하거나 람다 식을 인자로 전달받아 바로 호출하는 경우에는 그 람다를 인라이닝 할 수 있음
  그런 경우가 아니라면 컴파일러가 인라이닝을 금지 시킴

  둘 이상의 람다를 인자로 받는 함수에서 일부 람다만 인라이닝 하고 싶을 때

  → 인라이닝하면 안되는 람다를 파라미터로 받을때 noinline 변경자를 파라미터 이름 앞에 붙여서 인라이닝을 금지할 수 있다.

    ```kotlin
    inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {
      //...
    }
    ```

  ### 컬렉션 연산 인라이닝

  코틀린의 filter와 map은 인라인 함수이고, 따라서 filter 함수의 바이트 코드는 그 함수에 전달된 람다 본문의 바이트코드와 함께
  filter를 호출한 위치에 들어간다. 그 결과 filter 함수를 사용하는 코드는 결국 컴파일 코드에서 for loop 를 쓰는 함수와 거의 같게 된다.

    ```kotlin
    list.filter { it.age > 30 }
      .map(Person::name)
    ```

  위 코드는 람다 식과 멤버 참조를 사용하고 있으며, filter 와 map은 인라인 함수이다.
  따라서 두 함수의 본문은 인라이닝되며, 추가 객체나 클래스 생성은 없다.

  하지만, filter를 먼저 수행하여 결과를 저장하는 중간 리스트를 만들고,
  그 리스트를 map 함수에서 사용한다.

  이럴경우 처리량이 많아질 때 중간 리스트를 사용하는 부가 비용이 커지기 때문에
  asSequence를 통해서 리스트 대신 시퀀스로 처리하는게 낫다(자바의 스트림과 동일)
  그러나 시퀀스 연산에서는 람다가 인라이닝되지 않기 때문에 크기가 작은 컬렉션은 오히려
  일반 컬렉션 연산이 더 성능이 나을 수 있다.

  ***크기가 큰 컬렉션만 시퀀스로 성능 향상 시킬 수 있다.***

  ### 함수를 인라인으로 선언해야 하는 경우

  일반 함수 호출의 경우 JIT 컴파일러 컴파일 시점에 JVM이 강력하게 인라이닝을 시켜
  개발자가 작성한 코드의 성능을 최적화 해준다.
  그러나 코틀린 인라인 함수는 바이트 코드에서 각 함수 호출 지점을
  함수 본문으로 대치하기 때문에 코드 중복이 생긴다,

    - 함수를 직접 호출하면 스택 트레이스가 더 깔끔해진다.

      JVM 이 인라이닝하게 되면 최적화 과정에서 코드 중복을 최소화 하기 때문에
      함수를 여러 참조가 바뀔 수 있다. 그래서 스택 트레이스가 복잡해진다.

  일반함수는 인라인 함수로 선언하여 얻을 수 있는 장점이 크지 않다.

  반면 람다를 인자로 받는 함수를 인라이닝하면 이익이 더 많아진다.

    1. 인라이닝으로 추가 객체나 클래스의 생성이 사라지고 함수의 호출비용을 줄일 수 있다. → 바이트 코드가 직접 내부에 삽입 되므로
    2. JVM이 함수 호출과 람다를 인라이닝 해 줄 정도로 똑똑하지 못하다? (책 내용, 잘 이해는 안됨)
    3. 인라이닝을 사용하면 일반 람다에서는 사용할 수 없는 몇 가지 기능을 사용할 수 있다. (넌 로컬 반환 등..)

  **주의할 점**
  inline 함수는 함수의 본문에 해당하는 바이트코드를 모든 호출 지점에 복사해 넣기 때문에
  인라이닝 함수 코드의 크기가 크다면 바이트코드가 전체적으로 아주 커질 수 있다.

  ### 자원 관리를 위해 인라인된 람다 사용

    1. Lock 인터페이스의 확장함수 withLock

        ```kotlin
        public inline fun <T> Lock.withLock(action: () -> T): T {
          lock()
          try {
            return action()
          } finally {
            unlock()
          }
        }
        ```

    2. 자바의 try-with-resource와 같은 use 함수

        ```kotlin
        public inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
            try {
                return block(this)
            } catch (e: Throwable) {
                throw e
            } finally {
                close()
            }
        }
        ```

        ```kotlin
        fun readFirstLineFromFile(path: String): String {
          BufferedReader(FileReader(path)).use {
            return br.readLine()  <-- 인라인 함수에서 return은 넌로컬 return
          }
        }
        ```

       넌 로컬 return 문은 람다가 아니라 람다를 호출한 함수를 끝내면서 값을 반환한다.

- 고차 함수 안에서 흐름 제어

  ### 람다 안의 return문: 람다를 둘러싼 함수로부터 반환

  람다 안에서 return을 사용하면 람다만 반환되는 게 아니라 그 람다를 호출하는 함수가 실행을 끝내고 반환되는데,
  자신을 둘러싸고 있는 블록보다 더 바깥에 있는 다른 블록을 반환하게 만드는 return문을 넌로컬 return이라 부른다.

    - 이유

      자바 메소드 안에 for 루프나 synchronized 블록 안에서 return 키워드가 동작하는 방식과 동일

  코틀린에서 이렇게 return이 바깥쪽 함수를 반환 시킬 수 있는 대는 람다를 인자로 받는 함수가 인라인 함수일 경우 뿐이다.

  ### 람다로부터 반환: 레이블을 사용한 return

  람다 안에서 로컬 return은 for 루프의 break와 비슷한 역할을 함
  로컬 return은 함다의 실행을 끝내고 람다를 호출했던 코드의 실행을 계속 이어간다.
  로컬 return과 넌로컬 return을 구분하기 위해 레이블을 사용해야 한다.

    ```kotlin
    fun lookForAlice(people: List<Person>) {
      people.forEach label@ {
        if (it.name == "Alice") return@label
      }
      println("Alice might be somewhere") <-- 항상 이 줄이 출력된다.
    }
    ```

  ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b367f80f-059d-49cc-bd7a-952e5f38e5a3/_2021-07-01__2.14.26.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b367f80f-059d-49cc-bd7a-952e5f38e5a3/_2021-07-01__2.14.26.png)

  이름은 변경해도 되지만 return 다음에 공백은 안됨!

  람다를 인자로 받는 인라인 함수의 이름을 return 뒤에 레이블로 사용해도 된다

    ```kotlin
    fun lookForAlice(people: List<Person>) {
      people.forEach {
        if (it.name == "Alice") return@forEach
      }
      println("Alice might be somewhre")
    }
    ```

  ### 무명 함수: 기본적으로 로컬 return

    ```kotlin
    fun lookForAlice(people: List<Person>) {
      people.forEach(fun (person) {  <-- 람다 식 대신 무명함수를 사용.
        if (it.name == "Alice") return  <-- "return"은 가장 가까운 함수를 가리키는데 이 위치에서 가장 가까운 함수는 무명 함수다.
        println("${person.name} is noe Alice")
      })
    }
    ```

  자바의 익명함수와 마찬가지로 함수 이름, 파라미터 타입을 생략할 수 있다.

    ```kotlin
    people.filter(fun (person): Boolean {
      return person.age < 30
    }
    ```

    ```kotlin
    people.filter(fun (person) = person.get < 30)
    ```

  무명 함수 안에서 레이블이 붙지 않은 return 식은 무명 함수 자체를 반환시킨다.

  return 은 단순히 가장 안쪽의 함수를 반환시킨다는 규칙을 가지고 있다.

- 요약
    - 함수 타입을 사용해 함수에 대한 참조를 담는 변수나 파라미터나 반환 값을 만들 수 있다.
    - 고차 함수는 다른 함수를 인자로 받거나 함수를 반환한다.
    - 인라인 함수를 컴파일 할 때 컴파일러는 그 함수의 본문과 그 함수에게 전달된 람다의 본문을 컴파일한 바이트코드를 모든 함수 호출 지점에 삽입해 준다.
    - 이렇게 만들어지는 바이트코드는 람다를 활용한 인라인 함수 코드를 풀어서 직접 쓴 경우와 비교할 때 아무 부가 비용이 들지 않는다.
    - 고차 함수를 사용하면 컴포넌트를 이루는 각 부분의 코드를 더 잘 재사용할 수 있다.
    - 인라인 함수에서는 람다 안에 있는 return문이 바깥쪽 함수를 반환시키는 넌 로컬 return을 사용할 수 있다.
    - 무명 함수는 람다 식을 대신할 수 있으며 return 식을 처리하는 규칙이 일반 람다 식과는 다르다.
    - 본문 여러 곳에서 return 해야 하는 코드 블록을 만들어야 한다면 람다 대신 무명 함수를 쓸 수 있다.
