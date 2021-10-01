package com.ssafy.gumison.api.service;

import com.ssafy.gumison.api.request.VideoRequest;
import com.ssafy.gumison.db.entity.SolutionVideo;

public interface VideoService {
  public SolutionVideo[] uploadVideo(VideoRequest videoRequest);
}
