# :part_alternation_mark: Partitioner Class

- Map 함수의 출력인 (key, value) 쌍이 key에 의해서 어느 Reducer(머신)로 보내질 것인지에 관한 결정을 정의하는 Class
- Hadoop의 기본 타입은 Hash 함수가 Default로 제공되고 있어서 key에 대한 해시값에 따라 어느 Reducer(머신)로 보낼지 결정
  - Hadoop의 기본 타입
    - Text
    - IntWritable
    - LongWritable
    - FloatWritable
    - DoubleWritable

- 기본적으로 Partitoner Class는 나머지를 구해서 해당 머신으로 보낸다.





## 실습

- key는 Text 타입일 때, key의 첫 글자가 아스키코드 순서로 a보다 앞에 오는 경우 Reducer1로, 나머지는 Reducer2로 보내려면 아래와 같이 Partitioner Class 수정

  ```java
  public static class MyPartitioner extends Partitioner<Text, IntWritable> {
  
      @Override
      public int getPartition(Text key, IntWritable value, int numPartitions) {
          char firstChar = key.toString().charAt(0);
          if (firstChar < 'a')
              return 0;
          else
              return 1;
      }
  }
  ```

  - 주의 : 이해를 위한 코드일 뿐, 실전에서는 이렇게 코딩하면 안된다.

- MyPartitioner.class를 사용하기 위해 Main 함수에 다음 코드 추가

  ```java
  job.setPartitionerClass(MyPartitioner.class)
  ```

  