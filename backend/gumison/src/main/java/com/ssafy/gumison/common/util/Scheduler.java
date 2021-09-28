package com.ssafy.gumison.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

  private final RankUtility rankUtility;

  @Scheduled(cron = "0/10 * * * * *")
  public void loadDataIntoRedisByScheduler() {
    rankUtility.loadAllUserExpIntoRankZSet();
  }
}
