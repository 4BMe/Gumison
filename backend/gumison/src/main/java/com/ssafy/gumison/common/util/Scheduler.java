package com.ssafy.gumison.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

  private final RankProvider rankProvider;

  /*
    cron 단위로 스케줄러 실행
   */
  @Scheduled(cron = "0/10 * * * * *")
  public void loadDataIntoRedisByScheduler() {
    rankProvider.loadAllUserExpIntoRankZSet();
  }
}
