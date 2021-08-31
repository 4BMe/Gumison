# :herb: Hadoop 설치

### Mode 3가지

- Stand alone(독립실행모드) 
  - 기본 실행모드
  - 분산 저장 안함
  - 코딩 가능
- Pseudo-distributed(가상분산모드)
  - 하나의 컴퓨터에 설치
- Fully distributed(완전분산모드)
  - 여러 대의 컴퓨터에 설치



## Stand alone 모드 - 코딩 및 디버깅용

1. consol terminal 실행

   - Applications -> Accessories -> Terminal or Ctrl+Alt+t
   
2. 편의를 위해 패키징된 파일 다운로드

   ```
   $ wget http://kdd.snu.ac.kr/~kddlab/Project.tar.gz
   ```

3. Project.tar.gz 압축 풀기

   ```
   $ tar -zxvf Project.tar.gz
   ```

4. Project 폴더와 하위 폴더 및 파일 소유권 변경 -> 현재 계정과 다른 경우

   ```
   $ sudo chown -R [소유권자]:[그룹식별자] Project
   ```

5. 폴더 이동

   ```
   $ cd Project/
   ```

   

6. hadoop-3.2.2 파일 이동

   ```
   $ sudo mv hadoop-3.2.2 /usr/local/hadoop
   ```

   

7. 패키지 최신으로 업그레이드

   ```
   $ sudo apt update
   ```

   

8. ubuntu에 ssh, openjdk, apache ant 설치

   ```
   $ sudo apt install ssh openjdk-8-jdk ant -y
   ```

   - -y : 설치시 나타나는 모든 물음에 yes를 사용하겠다는 옵션값

   

9. hadoop 설정 파일 실행

   ```
   $ ./set_hadoop_env.sh
   ```

   

10. 설정 파일 적용

    ```
    $ source ~/.bashrc
    ```

    



## Hadoop 실행을 위한 준비

- 모든 명령은 hadoop 계정에서! (나는 ubuntu)

  - Path를 지정하려면 /home/hadoop에서 `source .bashrc` 실행

- Name node format

  - Dis format과 같은 개념

    ```
    $ hadoop namenode -format
    ```

- DFS Deamon start

  ```
  $ start-dfs.sh
  ```

- MapReduce daemon start (standalone 모드에서는 불필요)

  ```
  $ start-mapred.sh
  ```

- 확인

  - 수행 중인 java 프로세스 리스트를 확인
  - `$ jps`
    - SecNameNode
    - ondaryNameNode
    - DataNode
    - TaskTracker (Stand alone에서는 불필요)
    - JobTracker (Stand alone에서는 불필요)

- hadoop 계정의 HDFS상에 아무 디렉토리나 파일이 없음

- hadoop 계정의 HDFS상에서의 맨 위에 /user/hadoop 디렉토리를 생성

  ```
  $ hdfs dfs -mkdir -p /user/hadoop
  ```






## Word Count 예제 코드 실행

#### HDFS 디렉토리에 테스트 데이터 넣기

- Project 폴더로 이동

  ```
  $ cd /home/hadoop/Project
  ```

- 하둡의 HDFS에 wordcount_test 디렉토리 생성

  ```
  $ hdfs dfs -mkdir wordcount_test
  ```

- Linux의 data 디렉토리에 있는 wordcount-data.txt 파일을 하둡의 HDFS의 wordcount_test 디렉토리에 보냄

  ```
  $ hdfs dfs -put data/wordcount-data.txt wordcount_test
  ```

- 반드시 MapReduce 프로그램이 결과를 저장할 디렉토를 삭제한 후 프로그램을 실행 해야함.

  ```
  $ hdfs dfs -rm -r wordcount_test_out
  ```

  - MapReduce 코드에서 자동적으로 삭제하는 방법

    - main 함수에 new Job 전에 추가

    ```java
    FileSystem hdfs = FileSystem.get(conf);
        Path output - new Path(otherArgs[1]);
        if(hdfs.exists(output)){
            hdfs.delete(output, true);
    }
    ```

    



#### 코드 실행과 확인

- Project 폴더로 이동

  ```
  $ cd /home/hadoop/Project
  ```

- 하둡의 HDFS에 wordcount_test 디렉토리 생성

  - Driver.java에 표시한대로 wordcout를 써서 Wordcount MapRedce 코드를 수행

  - Wordcount_test 디렉토리에 들어있는 모든 파일을 맵 함수의 입력으로 사용

  - Hadoop 실행 방법

    ``` 
    $ hadoop jar [jar file] [program name] <input arguments>
    ```

    - ex)

      ```
      $ hadoop jar ssafy.jar wordcount wordcount_test wordcount_test_out
      ```

- 결과 파일 확인

  - reducer를 2개 사용해서 결과 파일이 2개

    ```
    $ hdfs dfs -cat wordcount_test_out/part-r-00000 | more
    $ hdfs dfs -cat wordcount_test_out/part-r-00001 | more
    ```





## 실행 중 겪었던 Error 해결법

#### Error : Permission denied (publickey,password) 2가지 해결법

1. Empty 'ssh key' 생성

   - ssh key 생성

     ```
     $ ssh-keygen -t rsa -P ""
     ```

   - ```
     $ cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys 
     ```

   - 제대로 생성됐는지 확인

     ```
     $ ssh localhost
     ```

     - 질문이 뜨면 yes를 입력
     - 비밀번호를 물어보지 않고 prompt가 뜨면 성공



2. 인증키 없이 바로 접속할 수 있도록 설정
   1.   설정 파일 vi 에디터로 열기

   ```
   $ sudo vi /etc/ssh/sshd_config
   ```

   2. `/PermitRootLogin`을 yes에서 no로 변경 후 `:wq`로 저장 후 종료





#### Could not get lock /var/lib/apt/lists/lock. 해결법

```
$ sudo /var/lib/apt/lists/* -vf
```





#### localhost:9000 failed on connection exception: java.net.ConnectException

- 에러가 나왔던 상황

  ```
  $ hdfs dfs -rm -r wordcount_test_out
  ```

- hadoop이 실행되지 않아서 생긴 에러

  - 먼저 `jps`로 Node를 확인한다

  - 필요한 Node들이 뜨지 않는다면 아래 명령 실행

    ```
    $ start-dfs.sh
    ```

    
