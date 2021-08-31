# :world_map: MapReduce

## 왜 MapReduce를 사용하는가

- 데이터 중심 프로세싱(Data-intensive processing)
  - 한 대의 컴퓨터의 능력으로 처리가 어려움
  - 근본적으로 수 십대, 수 백대 혹은 수 천대의 컴퓨터를 하나로 묶어서 처리해야 함
  - MapReduce 프레임워크가 이런 것을 가능하게 해준다.
- MapReduce는 빅데이터를 이용한 효율적인 계산이 가능한 첫 번째 프로그래밍 모델
  - 기존에 존재하는 여러 가지 다른 병렬 컴퓨팅 방법에서는 프로그래머가 낮은 레벨의 시스템 세부 내용까지 아주 잘 알고 많은 시간을 쏟아야만 함
- 빅 데이터를 이요하는 응용 분야에서 최근에 주목 받고 있음
- 값싼 컴퓨터들을 모아서 클러스터를 만들고 여기에서 빅 데이터를 처리하기 위한 스케일러블(scalable) 병렬 소프트웨어의 구현을 쉽게 할 수 있도록 도와주는 간단한 프로그래밍 모델
  - scalable하다 : 사용자 수가 급증하거나 데이터가 급증해도 프로그램이 멈추거나 성능이 크게 떨어지는 일이 없다
- 구글의 MapReduce 또는 오픈소스인 Hadoop은 MapReduce 프레임워크의 우수한 구현의 형태임
- 드라이버에 해당하는 메인 함수가 map 함수와 reduce 함수를 호출해서 처리





## MapRedue Programming Model

- 함수형 프로그래밍(Functional prigramming) 언어의 형태
- 유저는 아래 3가지 함수를 구현해서 제공해야함
  - Main 함수
  - Map 함수 : (key1, val1) -> [(key2, val2)]
  - Reduce 함수 : (key2, [val2]) -> [(key3, val3)]
  - [] : list 형태





## MapReduce Framework

- MapReduce 프레임워크에서는 각각의 레코드(record) 또는 튜플(tuple)은 key-value 쌍으로 표현
- MapRedue 프레임워크는 main 함수를 한 개의 마스터 머신(master machine)에서 수행
  - 마스터 머신은 맵 함수를 수행하기 전에 전처리를 하거나 Reduce 함수의 결과를 후처리 하는데 사용될 수 있음
- 컴퓨팅은 map과 reduce라는 유저가 정의한 함수 한 쌍으로 이루어진 MapReduce phase를 한 번 수행하거나 여러번 반복해서 수행할 수 있음.
- 한 번의 MapReduce phase는 Map 함수를 먼저 호출하고 그 다음에 Reduce 함수를 호출하는데 때에 따라 Reduce 함수를 호출하지 않기도 하고 Map 함수가 끝난 후에 컴바인(combine) 함수를 중간에 수행할 수 있다.

- 드라이버에 해당하는 main 프로그램에서 MapReduce phase를 수행



#### MapReduce Phase (with Word Counting algorithm)

- Map phase

  - 제일 먼저 수행

  - 데이터의 여러 파티션(partition)에 병렬 분산으로 호출되어 수행

  - 각 머신마다 수행되는 Mapper는 Map 함수를 호출

    - 입력 데이터의 한 줄마다 Map 함수를 호출

  - Map 함수는 (key, value)쌍의 형태로 결과를 출력하고 여러 머신에 나누어 보내며 같은 key를 가진 (key, value) 쌍은 같은 머신으로 보내짐 

  - ex) word counting 알고리즘

    <table>
        <tr>
            <th>Input</th>
            <td>Set of data</td>
            <td>Bus, Car, bus,  car, train, car, bus, car, train, bus, TRAIN,BUS, buS, caR, CAR, car, BUS, TRAIN</td>
        </tr>
        <tr>
            <th>Output</th>
            <td>Convert into another set of data<br>(Key,Value)</td>
            <td>(Bus,1), (Car,1), (bus,1), (car,1), (train,1),<br>(car,1), (bus,1), (car,1), (train,1), (bus,1),<br>(TRAIN,1),(BUS,1), (buS,1), (caR,1), (CAR,1),<br>(car,1), (BUS,1), (TRAIN,1)</td>
        </tr>
    </table>

- Shuffling phase

  - 모든 머신에서 Map phase가 다 끝나면 시작

  - Map phase에서 각각의 머신으로 보내진 (key, value) 쌍을 key를 이용해서 정렬

  - 각각의 key마다 같은 key를 가진 (key, value) 쌍을 모아서 (value-list)를 만든 다음 (key, value-list) 형태로 key에 따라서 여러 머신에 분산해서 보낸다.

  - ex) word counting 알고리즘

    <table>
        <tr>
            <th>Input<br>(output of Map function)</th>
            <td>Set of Tuples</td>
            <td>(Bus,1), (Car,1), (bus,1), (car,1), (train,1),<br>(car,1), (bus,1), (car,1), (train,1), (bus,1),<br>(TRAIN,1),(BUS,1), (buS,1), (caR,1), (CAR,1),<br>(car,1), (BUS,1), (TRAIN,1)</td>
        </tr>
        <tr>
            <th>Output</th>
            <td>Converts into smaller list of tuples</td>
            <td>(BUS, [1,1,1,1,1,1,1]),<br>(CAR, [1,1,1,1,1,1,1]),<br>(TRAIN, [1,1,1,1])</td>
        </tr>
    </table>

- Reduce phase

  - 모든 머신에서 Shuffling phase가 다 끝나면 시작

  - 각의 머신에서는 Suffling phase에서 해당 머신으로 보내진 각각의  (key, value-list) 쌍 마다 Reduce 함수가 호출

    - 하나의 Reduce 함수가 끝나면 다음 (key, value-list)쌍에 Reduce 함수가 호출

  - 출력이 있다면 (key, value)쌍의 형태로 출력

  - ex) word counting 알고리즘

    <table>
        <tr>
            <th>Input</th>
            <td>Set of Tuples</td>
            <td>(BUS, [1,1,1,1,1,1,1]),<br>(CAR, [1,1,1,1,1,1,1]),<br>(TRAIN, [1,1,1,1])</td>
        </tr>
        <tr>
            <th>Output</th>
            <td>Converts into smaller set of tuples</td>
            <td>(BUS, 7),<br>(CAR, 7),<br>(TRAIN, 4)</td>
        </tr>
    </table>



#### MapReduce 함수

- Map 함수
  - org.apache.hadoop.mapreduce라는 피키지에 있는 Mapper 클래스를 상속받아서 map 메소드 수정
  - 입력 텍스트 파일에서 라인 단위로 호출되고 입력은 (key, value)의 형태
  - key는 입력 텍스트 파일에서 맨 앞 문자를 기준으로 Map 함수가 호출된 해당 라인의 첫 번째 문자까지의 오프셋
  - value는 텍스트 해당 라인 전체
- Rudece 함수
  - org.apache.hadoop.mapreduce라는 피키지에 있는 Reducer 클래스를 상속받아서 reduce 메소드 수정
  - suffling phase의 출력을 입력으로 받는데 (key, value-list)의 형태
  - value-list는 Map 함수의 출력에서 key를 갖는 (key, value) 쌍들의 value들의 리스트
- Combine 함수
  - reduce 함수와 유사한 함수인데 각 머신에서 map phase에서 map 함수의 출력 크기를 줄여서 suffling phase와 reduce phase의 비용과 네트워크 트래픽(Network Traffic)을 줄여주는데 사용
  - 즉, map phase에서의 결과를 reduce 함수의 결과처럼 만들어서 출력 
    - Suffling phase 전에 수행



## Mapper 와 Reducer

- 각 머신에서 독립적으로 수행
- Mapper는 Map 함수를, Reducer는 Reduce 함수를 각각 수행
- setup()
  - 첫 Map 함수나 Reduce 함수가 호출되기 전에 맨 먼저 수행
  - 모든 Map 함수들에게 Braodcast해서 전달해야 할 파라미터들 정보를 Main 함수에서 받아오는데 사용
  - 모든 Map 함수들이 공유하는 자료구조를 초기화 하는데 사용
- cleanup()
  - 마지막 Map 함수나 Reduce 함수가 끝나고 나면 수행
  - 모든 Map 함수들이 공유하는 자료구조의 결과를 출력하는데 사용
- 한개의 MapReduce job을 수행할 때에 Map phase만 수행하고 중단할 수도 있음.



## 입출력에 사용한 Default 클래스

- MapReduce의 입출력에 사용하는 타입들은 Suffling phase에서 정렬하는데 필요한 비교 함수 등 여러 함수가 이미 정의되어 있음
- MapReduce의 여러 함수에서 입출력에 사용할 수 있는 클래스와 해당되는 자바 타입
  - Text - String
  - IntWritable - int
  - LongWritable - long
  - FloatWritable - float
  - DoubleWritable - double
- 만약 새로운 클래스를 정의해서 입출력에 사용하고 싶다면 필요한 여러 함수도 함께 정의를 같이 해주어야만 한다.



## 코드

#### wordcount.java

- Map

  ```java
  public static class TokenizerMapper extends Mapper<입력 key 타입, 입력 value 타입, 출력 key 타입, 출력 value 타입>{
      
      // 출력값에 사용되는 변수, 상수
      private final static IntWritable one = new IntWritable(1);
      private Text word = new Text();
  
      // map function (Context는 고정된 인자)
      public void map(Object key, Text value, Context context)
          throws IOException, InterruptedException {
  
          // value.toString() : get a line
          StringTokenizer itr = new StringTokenizer(value.toString());
          while ( itr.hasMoreTokens() ) {
              word.set(itr.nextToken());
  
              // emit a key-value pair
              context.write(word,one);
          }
      }
  }
  ```

  

- Reduce

  ```java
  public static class IntSumReducer
      extends Reducer<입력 key 타입, 입력 value 타입, 출력 key 타입, 출력 value 타입> {
  
      // 출력값에 사용되는 변수
      private IntWritable result = new IntWritable();
  
      // key : a disticnt word
      // values :  Iterable type (data list)
      public void reduce(Text key, Iterable<IntWritable> values, Context context) 
          throws IOException, InterruptedException {
  
          int sum = 0;
          for ( IntWritable val : values ) {
              sum += val.get();
          }
          result.set(sum);
          context.write(key,result);
      }
  }
  ```



- main

  ```java
  public static void main(String[] args) throws Exception {
      Configuration conf = new Configuration();
      String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
      if ( otherArgs.length != 2 ) {
          System.err.println("Usage: <in> <out>");
          System.exit(2);
      }
      Job job = new Job(conf,"word count"); // (job 작성, 설명(안써도 된다))
      job.setJarByClass(Wordcount.class); // (job 수행할 class), 대소문자 주의
  
      // hadoop에게 작성한 Map과 Reudce class를 알려준다
      job.setMapperClass(TokenizerMapper.class);
      job.setReducerClass(IntSumReducer.class);
  
      // Mapr과 Reduce의 output의 key와 value 타입 지정
      // Map과 Reduce의 output이 같다면 Map output에 대한 지정 생략
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(IntWritable.class);
  
      // 동시에 수행되는 reducer의 개수 설정
      job.setNumReduceTasks(2);
  
      // set input and output directories
      // input과 output 파일을 저장할 path 설정
      FileInputFormat.addInputPath(job,new Path(otherArgs[0]));
      FileOutputFormat.setOutputPath(job,new Path(otherArgs[1]));
      
      // 실행
      System.exit(job.waitForCompletion(true) ? 0 : 1 );
  }
  ```

  

#### Mapper나 Reducer에 값을 넘겨주기

- main

  ```java
  public static void main(String[] args) throws Exception {
      // ...
      Job job = new Job(conf,"word count");
      // ...
      job.setOutputValueClass(IntWritable.class);
  
      // 넘겨줄 인자 setting
     	Configuration config = job.getConfiguration();
      config.set("name", "Shim"); // name의 값은 "Shim"
      config.setInt("one", 1);
      config.setFloat("point_five", (float)0.5);
      
      //..
      
      FileInputFormat.addInputPath(job,new Path(otherArgs[0]));
      FileOutputFormat.setOutputPath(job,new Path(otherArgs[1]));
      System.exit(job.waitForCompletion(true) ? 0 : 1 );
  }
  ```

  

- Mapper나 Reducer의 setup

  ```java
  public static class IntSumReducer
      extends Reducer<입력 key 타입, 입력 value 타입, 출력 key 타입, 출력 value 타입> {
  	// ...
      
     	private String name;
      private int one;
      private float point;
      
      // setup 함수
      protected void setup(Context context) throws IOException, InterruptedException {
          Configuration config = context.getConfiguration();
          name = config.get("name", "Kim"); // (값 가져오기, default 값)"
          one = config.getInt("one", 1);
          point = config.getFloat("point_five", (float)0.5);
      }
  }
  ```

  



## 새로운 MapReduce Code 컴파일하는 법

- 소스 코드 파일을 Project/src/ 디렉토리에 넣는다

- Project/src 디렉토리에 있는 Driver.java 파일에 아래 라인을 넣는다.

  ```java
  pgd.addClass("program name", class name, "description");
  ```

- 컴파일을 하려면 Project 디렉토리에서 ant라고 타입하고 수행

  ```
  $ ant
  ```

