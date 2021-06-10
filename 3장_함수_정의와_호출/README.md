# 3장. 함수 정의와 호출
## 3.1 코틀린에서 컬렉션 만들기
```
val set = hashSetOf(1, 7, 53)
val list = arrayListOf(1, 7, 53)
val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
```
위 코드처럼 `{컬렉션명}Of()` 형태의 정적 함수로 컬렉션을 생성할 수 있다.  
여기서 쓰인 to 는 어떤 언어적인 키워드가 아니라 일반 함수이다.  
이렇게 생성된 컬렉션 객체가 어떤 클래스에 속하는지 확인하기 위해서는  
자바의 `getClass()`에 해당하는 `javaClass`를 이용한다.
```kotlin
println(set.javaClass)
println(list.javaClass)
println(map.javaClass)
```
출력
> class java.util.HashSet  
> class java.util.ArrayList  
> class java.util.HashMap

코틀린은 표준 자바 컬렉션을 사용하여 자바 코드와의 상호작용이 쉽다.  
아래와 같은 여러 확장함수를 제공하여 자바보다 많은 기능을 쓸 수 있다.
```kotlin
val strings = listOf("first", "second", "fourteenth")
println(strings.last())

val numbers = setOf(1, 14, 2)
println(numbers.max())
```
출력
> fourteenth  
> 14

## 3.2 함수를 호출하기 쉽게 만들기
이름 붙인 인자를 이용하여 함수 호출 부분의 가독성을 개선할 수 있다.
```kotlin
fun <T> joinToStringKt(
    collection: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String
): String {
    ...    
}
```

```java
public <T> String joinToStringJava(
        Collection<T> collection,
        String separator,
        String prefix,
        String postfix
) {
    ...
}
```
위는 동일한 기능을하는 코드를 자바와 코틀린으로 작성해 놓은 것이다.  
```java
joinToStringJava(collection, ",", "[", "]");
```
자바에서는 위와 같이 메소드 시그니처의 순서에 맞게 인자를 전달하여 호출해야 한다.  
그러나 코틀린에서는 전달하는 인자에 이름을 붙여 가독성과 함수 호출에 편의성을 높였다.
```kotlin
joinToStringKt(collection = list, postfix = "]", prefix = "[", separator = ",")
```
위 처럼 이름 붙인 인자를 전달하면 순서에 상관없이 호출 가능하다.  

자바코드에서 하위 호완성을 유지하거나 API 사용자에게 편의를 더하기 위해  
인자 중 일부를 생략한 오버로드 함수를 여러개 생성하는데 일부 클래스에서  
오버로딩한 메소드가 너무 많아지는 문제가 있다.  
코틀린에서는 디폴트 파라미터 값을 선언하여 이러한 오버로드를 상당 수 해결 가능하다.
```java
public <T> String joinToStringJava(Collection<T> collection) {
    return joinToStringJava(collection, ",", "[", "]");
}

public <T> String joinToStringJava(Collection<T> collection, String separator) {
    return joinToStringJava(collection, separator, "[", "]");
}

public <T> String joinToStringJava(Collection<T> collection, String separator, String prefix) {
    return joinToStringJava(collection, separator, prefix, "]");
}

public <T> String joinToStringJava(Collection<T> collection, String separator, String prefix, String postfix) {
    ...
}
```
예를 위해 극단적으로 생성한 예제 코드이지만 자바 개발자라면 흔하게 보았을 풍경!  
자! 이제 코틀린을 적용해보자.
```kotlin
fun <T> joinToStringKt(
    collection: Collection<T>,
    separator: String = ",",
    prefix: String = "[",
    postfix: String = "]"
): String {
    ...
}
```
이렇게 코틀린으로 디폴트 파라미터 값을 설정하면  
함수를 호출할 때 모든 인자를 쓸 수도 있고, 일부를 생략할 수도 있다.  
생략하게 되면 기본으로 지정한 값을 사용하여 함수를 실행한다.  

> 이름 붙인 인자를 사용하는 경우에는 인자 목록의 중간에 있는 인자를 생략하고  
> 지정하고 싶은 인자를 이름을 붙여서 순서와 관계 없이 지정할 수 있다.
```kotlin
joinToStringKt(list, postfix = ";", prefix = "#")
```
출력  
```kotlin
#1,2,3;
```

**디폴트 값과 자바**
> 자바에는 디폴트 파라미터 값이라는 개념이 없어서 코틀린 함수를 자바에서 호출할 경우  
> 그 코틀린 함수가 디폴트 파라미터 값을 제공하더라도 모든 인자를 명시해야 한다.  
> 자바에서 코틀린 함수를 자주 호출해야 한다면 @JvmOverloads 애노테이션을 함수에 추가하자.  
> @JvmOverloads를 함수에 추가하면 코틀린 컴파일러가 자동으로 맨 마지막 파라미터로부터 파라미터를  
> 하나씩 생략한 오버로딩한 자바 메소드를 추가해준다.
> ```kotlin
> @JvmOverloads
> fun <T> joinToStringKt(
> collection: Collection<T>,
> separator: String = ",",
> prefix: String = "[",
> postfix: String = "]"
> ): String {
> ...
> }
> ```
> 위처럼 @JvmOverloads를 붙이면 자바에서 오버로딩한 함수가 아래와 같이 만들어 진다.
> ```java
> joinToStringKt(list);
> joinToStringKt(list, separator);
> joinToStringKt(list, separator, prefix);
> joinToStringKt(list, separator, prefix, postfix);
> ```
### 3.2.3 정적인 유틸리티 클래스 없애기: 최상위 함수와 프로퍼티
객체지향 언어인 자바에서 모든 코드를 클래스의 메소드로 작성하면서 응집성을 유지해 주어야 한다는 사실은 알고 있지만,  
실전에서는 어느 한 클래스에 포함시키기 어려운 코드가 많이 생긴다. 여러가지 이유로 다양한 정적 메소드를 모아두는 역할만 하며,  
특별한 상태나 인스턴스 메소드가 없는 클래스가 생겨나게 된다. JDK의 Collections 클래스가 전형적인 예이다.  
이러한 정적 메소드만 모아두는 클래스는 보통 클래스 파일 뒤에 Util이 붙어 있다.  
  
코틀린에서는 이런 무의미한 클래스가 필요 없다. 함수를 직접 소스파일의 최상위 수준, 즉 모든 클래스의 밖에 위치시키면 된다.  
그런 함수들은 여전히 그 파일의 맨 앞에 정의된 패키지의 멤버 함수이므로 다른 패키지에서 그 함수를 사용하고 싶을때는 그 함수가 정의된 패키지를 임포트 해야 한다.
최상위 수준에 선언된 함수는 클래스 내부에 위치해 있지 않기 때문에 코틀린 컴파일러가 생성하는 `클래스파일.함수` 이름으로 임포트 시킬 수 있다.

**파일에 대응하는 클래스의 이름 변경하기**
> 코틀린 컴파일러가 생성하는 클래스의 이름은 기본적으로 코틀린 소스파일의 이름과 대응하는데  
> 이름을 바꾸고 싶다면 코틀린 파일의 패키지 이름 선언 이전에 @JvmName 애노테이션을 추가한다.
> ```kotlin
> @file:JvmName("StringFunctions")
> 
> package strings
> 
> fun joinToStringKt(...): String {...}
> ```
> 자바에서는 아래와 같이 호출한다.
> ```java
> import strings.StringFunction;
> StringFunction.joinToString(list, ",", "[", "]");
> ```

#### 최상위 프로퍼티
함수와 마찬가지로 프로퍼티도 파일의 최상위 수준에 놓을 수 있다.  
어떤 데이터를 클래스 밖에 위치시켜야 하는 경우는 흔하지는 않지만, 그래도 가끔 유용할 때가 있다.  
최상위 프로퍼티의 값은 정적 필드에 저장되고 이런 최상위 프로퍼티는 접근자 메소드를 통해 자바 코드에 노출된다.  
val 의 경우 게터, var의 경우 게터와 세터가 생긴다. 겉으로는 상수처럼 보이는데, 게터를 사용해야 한다면 자연스럽지 못하다.  
상수로 사용하려면 프로퍼티를 `public static final`로 컴파일 해야 하는데, `const` 변경자를 추가하면 프로퍼티를  
`public static final` 필드로 컴파일하게 만들 수 있다(단, 원시 타입과 String 타입의 프로퍼티만 const로 지정할 수 있다).

## 3.3 메소드를 다른 클래스에 추가: 확장 함수와 확장 프로퍼티
#### 확장함수 
확장 함수는 어떤 클래스의 맴버 메소드인 것처럼 호출할 수 있지만 그 클래스의 밖에 선언된 함수  
확장 함수를 만들려면 추가하려는 함수 이름 앞에 그 함수가 확장할 클래스의 이름을 덧붙이기만 하면 된다.  
클래스의 이름을 **수신 객체 타입**이라 부르며, 확장 함수가 호출되는 대상이 되는 값(객체)을 **수신 객체**라고 부른다.  
호출하는 구문은 일반 클래스 멤버를 호출하는 구문과 똑같다.  

직접 작성한 코드가 아니고 코드를 소유한것도 아니지만 원하는 메소드를 클래스에 추가할 수 있다.  
일반 메소드의 본문에서 this를 사용할 때와 마찬가지로 확장 함수 본문에도 this를 쓸 수 있고,  
일반 메소드에서와 마찬가지로 확장 함수 본문에서도 this를 생략할 수 도 있다.  
확장함수 내부에서는 일반적인 인스턴스 메소드의 내부에서와 마찬가지로 수신 객체의 메소드나 프로퍼티를 바로 사용할 수 있지만,  
클래스 안에서 정의한 메소드와 달리 확장 함수 안에서는 클래스의 내부에서만 사용할 수 있는 private 멤버나 protected 멤버를 사용할 수 없다.  
즉, 확장 함수는 캡슐화를 깰 수 없다.  
  
#### 임포트와 확장 함수
확장 함수를 정의하자마자 어디서든 그 함수를 쓸 수 있다면 한 클래스에 같은 이름의 확장 함수가 둘 이상 있어서 이름이 
충돌하는 경우가  자주 생길 수 있다. 코틀린에서는 클래스를 임포트할 때와 동일한 구문을 사용해 개발함수를 임포트할 수 있다.
`as` 키워드를 사용하면 임포트한 클래스나 함수를 다른 이름으로 불러 한파일 안에서 다른 여러 패키지에 속해있는 이름이 같은
함수를 가져와 사용하는 경우에 충돌을 막을 수 있다.  
  
#### 자바에서 확장 함수 호출
내부적으로 확장 함수는 수신 객체를 첫 번째 인자로 받는 정적 메소드이다. 그래서 자바에서 사용하기도 편하다. 단지 정적
메소드를 호출하면서 첫 번째 인자로 수신 객체를 넘기기만 하면 된다. 다른 최상위 함수와 마찬가지로 작동한다.  

#### 확장 함수로 유틸리티 함수 정의
위에서 최상위 함수로 만든 joinToSTring() 함수를 Collection의 확장함수로 정의하기
```kotlin
fun <T> Collection<T>.joinToStringKt(
    separator: String,
    prefix: String,
    postfix: String
): String {
    ...
    구현부에서 기존에 인자로 받아 사용하면 collection 객체를
    this로 접근하여 사용한다.
}
```
확장 함수는 단지 정적 메소드 호출에 대한 문법적인 편의일 뿐이다.  
그래서 클래스가 아닌 더 구체적인 타입을 수신 객체 타입으로 지정할 수도 있다.  
그래서 문자열의 컬렉션에 대해서만 호출할 수 있는 join 함수를 정의하고 싶다면 다음과 같이 하면 된다.
```kotlin
fun Collection<String>.join(
    separator: String,
    prefix: String,
    postfix: String
) = joinToString(separator, prefix, postfix)

listOf("one", "two", "eight").join(" ")
```
출력
> one two eight

***확장 함수가 정적 메소드와 같은 특징을 가지므로, 확장 함수를 하위 클래스에서 오버라이드 할 수는 없다***

> 어떤 클래스를 확장한 함수와 그 클래스의 멤버 함수의 이름과 시그니처가 같다면 확장 함수가 아니라 멤버 함수가 호출된다.  
> 클래스의 API를 변경할 경우 항상 이를 염두에 둬야 한다.

#### 확장 프로퍼티
확장 프로퍼티를 사용하면 기존 클래스 객체에 대한 프로퍼티 형식의 구분으로 사용할 수 있는 API를 추가할수 있다.
프로퍼티라는 이름으로 불리기는 하지만 상태를 저장할 적절한 방법이 없기 때문에(기존 클래스의 인스턴스 객체에
필드를 추가할 방법은 없다) 실제로 확장 프로퍼티는 아무 상태도 가질 수 없다. 하지만 프로퍼티 문법으로 더 짧게 코드를
작성할 수 있어서 편한 경우가 있다.

- 확장프로퍼티 선언하기
```kotlin 
val String.lastChar: Char
  get() = get(length - 1)
```
- 변경 가능한 확장 프로퍼티 선언하기
```kotlin
var StringBuilder.lastChar: Char
  get() = get(length - 1)               // 프로퍼티 게터
  set(value: Char) {
      this.setChatAt(length - 1, value) // 프로퍼티 세터
  }
```
확장 프로퍼티를 사용하는 방법은 멤버 프로퍼티를 사용하는 방법과 같고, 자바에서 사용하고자 한다면 항상 게터나 세터를 명시적으로 호출해야 한다.

## 3.4 컬렉션 처리: 가변 길이 인자, 중위 함수 호출, 라이브러리 지원
- vararg 키워드를 사용하면 호출 시 인자 개수가 달라질 수 있는 함수를 정의할 수 있다.
- 중위(infix) 함수 호출 구문을 사용하면 인자가 하나뿐인 메소드를 간편하게 호출할 수 있다.
- 구조 분해 선언(destructuring declaration)을 사용하면 복합적인 값을 분해해서 여러 변수에 나눠 남을 수 있다.

#### 가변 인자 함수: 인자의 개수가 달라질 수 있는 함수 정의
```kotlin
fun listOf<T>(vararg values: T): List<T> {...}
```
이미 배열에 들어있는 원소를 가변 길이 인자로 넘길 때 자바에서는 배열을 그냥 넘기면 되지만  
코틀린에서는 스프레드 연산자를 사용해서 배열을 명시적으로 풀어서 배열의 각 원소가 인자로 전달되게 해야 한다.  
```kotlin
fun main(args: Array<String>) {
    val list = listOf("args: ", *args)
    print(list)
}
```
배열로 전달된 인자를 스프레드 연산자를 사용했을 때와 사용하지 않았을 때 비교
```kotlin
fun spreadOperator(args: Array<String>) {
    val spreadList = listOf("spread: ", *args)
    println(spreadList)

    val unSpreadList = listOf("unspread: ", args)
    println(unSpreadList)
}
```
출력
> [spread: , 하, 쿠, 나, 마, 타, 타]  
> [unspread: , [Ljava.lang.String;@7291c18f] 

스프레드 연산자를 사용해야만 원하는 결과를 얻을 수 있다.

#### 값의 쌍 다루기: 중위 호출과 구조 분해 선언
- 중위 호출(infix call): 수신 객체와 유일한 메소드 인자 사이에 메소드 이름을 넣는다.
  다음 두 호출은 같다.
  ```kotlin
  1.to("one")
  1 to "one"
  ```
  > 인자가 하나뿐인 일반 메소드나 인자가 하나뿐인 확장 함수에 중위 호출을 사용할 수 있다. 
  함수를 중위 호출에 사용하게 허용하고 싶으면 infix 변경자를 함수 선언 앞에 추가 해야 한다.
- 구조 분해 선언: 복합적인 값을 분해해서 여러 변수에 나눠 남는 기술
    ```kotlin
    val (number, name) = 1 to "one"
    ```

## 3.5 문자열과 정규식 다루기
코틀린 문자열은 자바 문자열과 같다. 코틀린은 다양한 확장 함수를 제공함으로써 표준 자바 문자열을 더 쉽게 다루게 해준다.

#### 문자열 나누기
- toRegex 확장 함수를 이용해 정규식을 명시적으로 만듬
    ```kotlin
    println("12.345-6.A".split("\\.|=".toRegex()))
    >>> [12, 345, 6, A]
    ```
- 여러 구분 문자열을 지정
    ```kotlin
    println("12.345-6.A".split(".", "-"))
    >>> [12, 345, 6, A]
    ```
  
#### 정규식과 3중 따옴표로 묶은 문자열
- String 확장 함수를 사용해 경로 파싱하기
    ```kotlin
    fun parsePath(path: String) {
      val directory = path.substringBeforeLast("/")
      val fullName = path.substringAfterLast("/")
  
      val fileName = fullName.substringBeforeLast(".")
      val extension = fullName.substringAfterLast(".")
  
      println("Dir: $directory, name: $fileName, ext: $extension")
    } 
  
    parsePath("/Users/yole/kotlin-book/chapter.adoc")
    >>> Dir: /Users/yole/kotlin-book, name: chapter, ext: adoc 
    ```
  
- 경로 파싱에 정규식 사용하기
    ```kotlin
    fun parsePath(path: String) {
      val regex = """(.+)/(.+)\.(.+)""".toRegex()
      val matchResult = regex.matchEntire(path)
      if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        println("Dir: $directory, name: $fileName, ext: $extension")
      }
    ```
    3중 따옴표 문자열을 사용해 정규식을 쓰면 역슬래시(\)를 포함한 어떤 문자도 이스케이프할 필요가 없다.

#### 여러 줄 3중 따옴표 문자열
3중 따옴표 문자열을 문자열 이스케이프를 피하기 위해서만 사용하지는 않는다.
3중 문자열 따옴표 문자열에는 줄 바꿈을 표현하는 아무 문자열이나 그대로 들어간다. 
따라서 줄 바꿈이 들어있는 프로그램 텍스트를 쉽게 문자열로 만들 수 있다.  

## 3.6 코드 다듬기: 로컬 함수와 확장
자바에서는 DRY 원칙을 따르기 위해 많은 경우 메소드 추출 리팩토링을 적용해서 긴 메소드를 부분부분
나눠서 각 부분을 재활용 할 수 있게 만든다. 하지만 이렇게 하면 클래스 안에 작은 메소드가 많아지고
각 메소드 사이의 관계를 파악하기 힘들어서 코드를 이해하기 더 어려워질 수도 있다.
리팩토링을 진행해서 추출한 메소들르 별도의 내부 클래스 안에 넣으면 코드를 깔끔하게 조직할 수 있지만,
그에 따른 불필요한 준비 코드가 늘어나게 된다.  
  
코틀린에는 좀 더 깔끔한 해법이 있는데, 코틀린에서는 함수에서 추출한 함수를 원 함수 내부에 중첩시킬 수 있다.  
이렇게 하면 문법적인 부가 비용을 들이지 않고도 깔끔하게 코드를 조직할 수 있다.  
  

```kotlin
class User(val id: Int, val name: String, val address: String)
fun saveUser(user: User) {
  if(user.name.isEmpty()) {
      throw IllegalArgumentException("Can't Save user ${user.id}: empty Name")
  }
  if (user.address.isEmpty()) {
      throw IllegalArgumentException("Can't Save user ${user.id}: empty Name")
  }
  // 저장 로직
    ...
}

saveUser(User(1, "", ""))
```
> java.lang.IllegalArgumentException "Can't Save user 1: empty Name"

위와 같이 흔히 발생하는 코드 중복을 로컬 함수를 통해 제거하기
```kotlin
class User(val id: Int, val name: String, val address: String)
fun saveUser(user: User) {
  fun validate(value: String,
               fieldName: String) {
      if (value.isEmpty()) {
        throw IllegalArgumentException("Can't Save user ${user.id}: empty $fieldName")
      }
  }
  validate(user.name, "Name")
  validate(user.address, "Address")
  
  // 저장 로직
  ...
}

saveUser(User(1, "", ""))
```
로컬 함수는 자신이 속한 바깥 함수의 모든 파라미터와 변수를 사용할 수 있다.  
여기에서 검증 로직을 User 클래스를 확장한 함수로 만들어 개선할 수 도 있다.
```kotlin
class User(val id: Int, val name: String, val address: String)
fun User.validateBeforeSave() {
  fun validate(value: String, fieldName: String) {
    if (value.isEmpty()) {
      throw IllegalArgumentException("Can't Save user ${user.id}: empty $fieldName")
    }
  }
  validate(name, "Name")
  validate(address, "Address")
}

fun saveUser(user: User) { 
  user.validateBeforeSave()
  // 저장 로직
  ...
}
```
코드를 확장 함수로 뽑아내는 기법은 놀랄 만큼 유용하다.  
검증 로직들을 User에 포함시켜 복잡하게 만드는 대신   
User를 간결하게 유지하면 생각해야 할 내용이 줄어들어 더 쉽게 코드를 파악할 수 있다.  
  
반면 한 객체만을 다루면서 객체의 비공개 데이터를 다룰 필요는 없는 함수는 확장 함수로 만들면  
객체.멤버 처럼 수신 객체를 지정하지 않고도 공개된 멤버 프로퍼티나 메소드에 접근할 수 있다.  
  
확장 함수는 로컬 함수로도 정의할 수 있다. 즉 User.validateBeforeSave를 saveUser 내부에  
로컬 함수로 넣을 수 있다. 하지만 중첩된 함수의 깊이가 깊어지면 코드를 읽기가 상당히 어려워진다.  
따라서 일반적으로는 한 단계만 함수를 중첩시키라고 권장한다.
