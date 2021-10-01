package com.ssafy.gumison.api.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.gumison.api.request.VideoRequest;
import com.ssafy.gumison.db.entity.SolutionVideo;
import com.ssafy.gumison.db.repository.SolutionVideoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoServiceImpl implements VideoService {

  private final SolutionVideoRepository solutionVideoRepository;

  @Override
  public SolutionVideo[] uploadVideo(VideoRequest videoRequest) {
    log.info("[uploadVideo] - VideoService : {}", videoRequest);
//    SolutionVideo solutionVideoRet = solutionVideoRepository.save();

    String windowsPath = "C:\\SolutionVideo\\";
    File videoFolder = new File(windowsPath);
    if (!videoFolder.exists()) {
      try {
        videoFolder.mkdirs();
      } catch (Exception e) {
        log.error("[uploadVideo] - VideoService : Failed to make directory");
        e.printStackTrace();
      }
    }

    LocalDateTime now = LocalDateTime.now();
    for (int i = 0; i < videoRequest.getVideos().size(); i++) {
      MultipartFile video = videoRequest.getVideos().get(i);
      String originalFileName = video.getOriginalFilename();
      String extensionName = originalFileName.substring(originalFileName.lastIndexOf('.'), originalFileName.length());
      String fileName = videoRequest.getUserId() + "-" + now.toString().replace(':', '-') + "-" + i + extensionName;
      log.info("[uploadVideo] - VideoService, fileName : {}", fileName);
      File dest = new File(windowsPath + fileName);
      try {
        videoRequest.getVideos().get(i).transferTo(dest);
      } catch (IllegalStateException | IOException e) {
        log.error("[uploadVideo] - VideoService : Failed to upload videos");
        e.printStackTrace();
      }
      
      SolutionVideo solutionVideo = SolutionVideo.builder().date(now).uri(windowsPath + fileName).uploadId(fileName).build();

    }
    return null;
  }

}
