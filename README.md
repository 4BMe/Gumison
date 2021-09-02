# :pencil: 빅데이터（분산）

## 목차

1. [병렬 분산 알고리즘 사용 이유](#:floppy_disk: 왜 병렬 분산 알고리즘을?)
2. [MapReduce](MapReduce.md)
3. [Hadoop](Hadoop.md)
4. [Hadoop 설치](Hadoop_Install.md)
5. [Partitioner Class](Partitioner_Class.md)
6. [Inverted Index](Inverted_Index.md)



## :floppy_disk: 왜 병렬 분산 알고리즘을?

- Scale-out
  - 아주 많은 값싼 서버들을 이용함
- Scale-up
  - 적은 수의 값 비싼 서버들을 이용함

- 데이터 중심(data-intensive) 어플리케이션 분야에선는 Scale-out을 선호
- 고가의 서버들은 가격에 관점에서는 선형으로 성능이 증가하지 않음
  - 두 배의 성능의 프로세서 한 개를 가진 컴퓨터의 가격이 일반적인 프로세서 한 개를 가진 컴퓨터 가격의 두 배보다 훨씬 더 비쌈

