# Chapter 1. 성능과 최적화

메소드 디스패치

<aside>
💡

어떤 메소드를 실행할건지 결정하고 실행하는 과정

</aside>

- Static
    - 컴파일 시점 결정
    - 예시
    
    ```java
    
    public class temp {
        public static void main(String[] args) {
            new Temp().run();
        }
        static class Temp{
            
            void run(){
                System.out.println("hi");
            }
        }
    }
    ```
    
    - 무엇이 실행될지 알고 있다.
- Dynamic method dispatch
    - 런타임 시점 결정
    - 예시
    
    ```java
    public class temp {
        public static void main(String[] args) {
            Temp temp = new Temp1();
            temp.run();
        }
        static interface Temp{
    
            public void run();
        }
    
        static class Temp1 implements Temp{
            public void run(){
                System.out.println("temp1");
            }
        }
    
        static class Temp2 implements Temp{
    
            public void run(){
                System.out.println("temp2");
            }
        }
    
    }
    
    ```
    
    - 인터페이스나 추상 클래스에 정의된 추상 메소드를 호출하는 경우, 컴파일러는 Temp 인터페이스의 run을 참조하지만 실제 런타임에서는 new Temp1()으로 인해 Temp1의 run으로 결정된다.

***과거엔 이러한 메소드 호출시 객체 타입에 따라 런타임에 결정하는 가상 디스패치 과정이 매우 비효율적이었으나, 현재는 성능이 좋아졌고 모든 코드를 한 메소드에 다 넣는 것은 어울리지 않는다.***

책에 들어가기에 앞서,

1. JVM을 더 빨리 동작시키는 마법의 스위치는 없다.
2. 자바를 더 빨리 실행하게 만드는 팁은 없다.
3. 비밀 알고리즘 같은 건 존재하지 않는다.

자바는 블루 칼라( 생산직 ) 언어이다. 자바에서는 개발자가 일일이 메모리 관리를 하지 않는 등 서브시스템들을 관리하는 성격이 드러나있다.

### 성능 개선의 개요

1. 원하는 결과를 정의
2. 기존 시스템을 측정
3. 무슨 일을 해야할지 정의
4. 개선 활동 추진
5. 다시 테스트
6. 달성 여부 판단.

### 성능의 지표

1. 처리율_throughput
    1. 시스템이 수행 가능한 작업 비율 ( ex_단위 시간 당 완료한 작업 수 )
    2. 의미 있는 지표가 되려면 하드웨어 스펙, OS, SW 스택등 기준 플랫폼에 대한 정의와 트랜잭션 또한 테스트 마다 동일해야 한다.
    3. 워크로드 또한 일정하게 유지해야 한다.
2. 지연_latency
    1. 종단 시간이라고도 하며 하나의 트랜잭션을 처리하고 그 결과를 반대편에서 바라볼 때 까지 걸리는 소요 시간을 가리킨다.
3. 용량_capacity
    1. 시스템이 보유한 작업 병렬성의 총량, 동시 처리 가능한 작업 단위 수
4. 사용률_utilization
    1. 각 태스크에서 시스템의 리소스를 얼마나 효율적으로 사용하는지에 대한 정의
5. 효율_efficiency
    1. 처리율을 리소스 사용률로 나눈 값 
6. 확장성_scalability
    1. 시스템이 외부 서비스와 결합, 추가 될 때 효율성을 가리키며, 이는 리소스, 혹은 시스템의 확장에 따라 시스템/애플리케이션의 리소스 처리율 변화를 측정하여 가늠할 수 있다.
    2. 현실적으로 시스템을 클러스터를 두배로 늘려도 처리량이 2배로 늘진 않는다 ( 선형 확장은 기대하기 힘들다 )
7. 저하_degradation
    1. 시스템의 부하에 따른 지연, 처리율 측정 값의 변화를 나타냄.
    2. 시스템을 덜 사용하면 측정값이 느슨하게 변한다 만약 시스템이 풀 가동 중이라면 처리율이 더는 늘어나지 않는 즉 지연이 증가하는 양상을 띄고 이런 현상을 부하 증가에 따른 저하라고 함.

### 성능 그래프 읽기

<aside>
💡

앞으로는 visual vm과 git repo로 관리하며 따라할 수 있는 영역은 따라해보자.
![image_prop.png](img%2Fimage_prop.png)
</aside>


1. 성능 엘보
    
    <aside>
    💡
    
    부하가 증가하면서 예기치 않게 저하가 발생하는 것.
    
    </aside>
    
    ![image.png](img%2Fimage.png)
    
2. 준 선형적 확장
    
    <aside>
    💡
    
    클러스터 장비를 추가함에 따라 거의 선형적으로 처리율이 확장되는 케이스 ( 운이 좋음 )
    이러한 경우는 세션 고정등이 필요 없는 무상태 프로토콜만 가능하다.
    
    </aside>
    
    ![image_1.png](img%2Fimage_1.png)
    
3. 부하가 높을 때 지연 발생과 특징
    
    <aside>
    💡
    
    시스템 리소스가 누수될 때 성능이 악화되다가 특정 지점( **변곡점** )에 이르게 되면 성능이 급락함.
    ![image_2.png](img%2Fimage_2.png)
    </aside>

    

---