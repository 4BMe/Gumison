# :card_index: Inverted Index

### Inverted Index

- 단어가 존재하는 문서와 문서 안에서의 위치 표시
- inverted list : 각 단어가 어느 문서에서 어느 위치에 나타나는지에 대한 list

- ex) in

  - data

    - Doc1 : IMF Financial Economics Crisis
    - Doc2 : IMF Financial Crisis
    - Doc3 : Harry Economics
    - Doc4 : Financial 
    - Doc5 : Harry Potter Crisis

  - 위 data의 inverted index

    - IMF -> Doc1:1, Doc2:1
    - Financial -> Doc1:6, Doc2:6, Doc4:1
    - Economics -> Doc1:16, Doc3:7
    - Crisis -> Doc1:26, Doc2:16, Doc5:14
    - Harry -> Doc3:1, Doc4:11, Doc5:1
    - Potter -> Doc4:17, Doc5:7
    - Film -> Doc4:24

    

## 실습

- 사용하는 함수, 클래스
  - 단어 단위로 자르기 

    ```
    new StringTokenizer(value.toString, " ");
    ```

  - 파일에서 현재 라인의 시작 위치의 byte offset 가져오기

    - hadoop에 있는 LongWritable을 import 하기

    ```java
    ((LongWritable)key).get()
    ```

  - 출력 값 (파일 명:현재 위치)

    ```java
    filename + ":" + p
    ```

  - token 길이 구하기

    - token.length()

  - 입력 파일 이름 알아내기

    - hadoop에 있는 FileSplit import 하기

    ```java
    private String filename;
    protected void setup(Context context) throw IOException, InterruptedException{
        // 현재 사용하는 파일 이름 구하기
        filename = ((FileSplit)context.getInputSplit()).getPath().getName();
    }
    ```

    

- main 함수

  ```java
  private Text index = new Text();
  private Text word = new Text();
  
  private String filename;
  public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {
  
      // value.toString() : get a line
      StringTokenizer itr = new StringTokenizer(value.toString(), " ", true); // " "가 나누는 기준이 되고 token에 포함
      // 현재 위치의 idx 구하기 (시작 지점에서 몇 byte인지)
      long pos = ((LongWritable)key).get();
      while ( itr.hasMoreTokens() ) {
          String token = itr.nextToken();
          word.set(token.trim());
  
          if(!" ".equals(token)){
              index.set(filename + ":" + pos);
              context.write(word,index);
          }				
  
          pos += token.length();
      }
  }
  ```

  

- reduce 함수

  ```java
  private Text list = new Text();
  
  public void reduce(Text key, Iterable<Text> values, Context context) 
      throws IOException, InterruptedException {
  
      StringBuffer sb = new StringBuffer();
      boolean needComma = false; // 맨 앞에는 , 넣지 않기 위한 변수
      for ( Text val : values ) {
          if(!needComma){
              needComma = true;
              sb.append(":").append(val.toString());
          }else{
              sb.append(",\t").append(val.toString());
          }
      }
      list.set(sb.toString());
      context.write(key,list);
  }
  ```

  

- main 함수

  ```java
  public static void main(String[] args) throws Exception {
      Configuration conf = new Configuration();
      String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
      if ( otherArgs.length != 2 ) {
          System.err.println("Usage: <in> <out>");
          System.exit(2);
      }
  
      // 출력 폴더가 이미 존재한다면 삭제
      FileSystem hdfs = FileSystem.get(conf);
      Path output = new Path(otherArgs[1]);
      if(hdfs.exists(output)){
          hdfs.delete(output, true);
      }
  
      Job job = new Job(conf,"inverted index");
      job.setJarByClass(InvertedIndex.class);
  
      // let hadoop know my map and reduce classes
      job.setMapperClass(TokenizerMapper.class);
      job.setReducerClass(IntSumReducer.class);
  
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);
  
      // set number of reduces
      job.setNumReduceTasks(2);
  
      // set input and output directories
      FileInputFormat.addInputPath(job,new Path(otherArgs[0]));
      FileOutputFormat.setOutputPath(job,new Path(otherArgs[1]));
      System.exit(job.waitForCompletion(true) ? 0 : 1 );
  }
  ```

  