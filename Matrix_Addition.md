# :grapes: Matrix Addition

- 행렬 덧셈

  ![image](https://user-images.githubusercontent.com/62821450/130779836-1b59e95a-e17a-4942-bf4a-ef3a532ae2e8.png)

- 입력
  - 형식 : 행렬이름	행번호	열번호	원소값

- 출력
  - 형식 : 행번호	열번호	원소값

- key는 (행, 열), value는 원소값





## 실습

- 사용할 함수

  - 한 라인을 분리해서 배열에 담기 위한 함수

    ```java
    tmpStr.split("\t");
    ```

- map 함수

  ```java
  public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {
  
      String[] arr = value.toString().split("\t"); // [A|B] row_num col_num value
  
      Text coordi = new Text(arr[1] + "\t" + arr[2]);
      IntWritable intWritable new IntWritable(Integer.parseInt(arr[3]));
  
      context.write(coordi, intWritable);
  
  }
  ```

- reduce 함수

  - wordcount와 같음

- main 함수

  - InvertedIndex와 같고 job.setXXX 부분만 바꿔주면 된다.

  ```java
  public static void main(String[] args) throws Exception {
      // ...
      
      Job job = new Job(conf, "matrix addition");
      job.setJarByClass(MatrixAdd.class);		
      job.setMapperClass(MAddMapper.class);	          
      job.setReducerClass(MAddReducer.class);	 
      
      job.setOutputKeyClass(Text.class);	
      job.setOutputValueClass(IntWritable.class);		
      
      // ... 
  }
  ```

  

- 실헹 명령어

  ```
  $ hdfs dfs -put data/matadd-data-2x2.txt matadd_test # hdfs에 input data 넣기
  $ hadoop jar ssafy.jar matadd matadd_test matadd_output ＃ hadoop 실행
  $ hdfs dfs -cat matadd_output/part-r-00000 | more # 결과값 확인
  
  ```

  