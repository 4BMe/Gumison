package com.ssafy.gumison.api.request;

import java.time.LocalDate;

public class ContributionRequest {
  private Long levelTierId;
  private String newTier;
  private String oldTier;
  private Long userId;

  public ContributionRequest(Long levelTierId, String newTier, String oldTier, Long userId) {
    this.levelTierId = levelTierId;
    this.newTier = newTier;
    this.oldTier = oldTier;
    this.userId = userId;
  }

  public ContributionRequest() {

  }

  public Long getLevelTierId() {
    return levelTierId;
  }

  public void setLevelTierId(Long levelTierId) {
    this.levelTierId = levelTierId;
  }

  public String getNewTier() {
    return newTier;
  }

  public void setNewTier(String newTier) {
    this.newTier = newTier;
  }

  public String getOldTier() {
    return oldTier;
  }

  public void setOldTier(String oldTier) {
    this.oldTier = oldTier;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "ContributionRequest [levelTierId=" + levelTierId + ", newTier=" + newTier + ", oldTier=" + oldTier
        + ", userId=" + userId + "]";
  }

}
