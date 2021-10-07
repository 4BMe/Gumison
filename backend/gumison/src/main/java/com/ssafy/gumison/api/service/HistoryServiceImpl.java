package com.ssafy.gumison.api.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.gumison.api.request.SolutionRequest;
import com.ssafy.gumison.api.response.HistoryRes;
import com.ssafy.gumison.api.response.SolutionListItem;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.common.dto.HistoryUserDto;
import com.ssafy.gumison.common.exception.ResourceNotFoundException;
import com.ssafy.gumison.db.entity.Climbing;
import com.ssafy.gumison.db.entity.CommonCode;
import com.ssafy.gumison.db.entity.LevelTier;
import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.entity.SolutionVideo;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.ClimbingRepository;
import com.ssafy.gumison.db.repository.CommonCodeRepository;
import com.ssafy.gumison.db.repository.LevelTierRepository;
import com.ssafy.gumison.db.repository.SolutionRepository;
import com.ssafy.gumison.db.repository.SolutionVideoRepository;
import com.ssafy.gumison.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService {

  private final UserRepository userRepository;
  private final SolutionRepository solutionRepository;
  private final CommonCodeRepository commonCodeRepository;
  private final ClimbingRepository climbingRepository;
  private final LevelTierRepository levelTierRepository;
  private final SolutionVideoRepository solutionVideoRepository;

  private final String windowsPath = "C:\\SolutionVideo\\";

  private final int LIST_PER_PAGE = 10;
  private final Long MAX_TIER_CODE = 224L;
  private final Long MIN_TIER_CODE = 201L;

  @Override
  public HistoryRes history(String nickname) {
    User user = userRepository.findByNickname(nickname).orElseThrow(RuntimeException::new);
    List<Solution> solutionList = getSolutionList(user.getId(), 0);
    CommonCode code = commonCodeRepository.findById(user.getTierCode())
        .orElseThrow(RuntimeException::new);
    String tier = codeNameConvert(code.getCode());
    List<SolutionListItem> solutionListItems = solutionListConvert(solutionList);
    Long nextExp = codeExpConvert(code.getCode() + 1L);
    HistoryUserDto userDto = userConvert(user, tier, user.getAccumulateExp(), nextExp);
    int solutionLastPage = allHistorySize(user.getId());
    return HistoryRes.of(userDto, solutionListItems, solutionLastPage);
  }

  @Override
  public SolutionListRes solutionList(String nickname, int pageNumber) {
    User user = userRepository.findByNickname(nickname).orElseThrow(RuntimeException::new);
    List<Solution> solutionList = getSolutionList(user.getId(), pageNumber);
    List<SolutionListItem> solutionListItems = solutionListConvert(solutionList);
    return SolutionListRes.of(solutionListItems);
  }

  @Override
  public SolutionRes solution(String solutionId) {
    Solution solution = solutionRepository.findById(Long.parseLong(solutionId))
        .orElseThrow(RuntimeException::new);
    List<Solution> solutionList = solutionRepository.findByUploadId(solution.getUploadId());
    User user = solution.getUser();
    CommonCode code = commonCodeRepository.findById(user.getTierCode())
        .orElseThrow(RuntimeException::new);
    String tier = codeNameConvert(code.getCode());
    List<Long> solutionIds = new ArrayList<>();
    List<Long> levelTierIds = new ArrayList<>();
    List<String> tierNames = new ArrayList<>();
    List<String> levelNames = new ArrayList<>();
    List<Integer> counts = new ArrayList<>();
    List<SolutionVideo> solutionVideoList = solutionVideoRepository
        .findByUploadId(solution.getUploadId());
    solutionList.forEach(sol -> {
      solutionIds.add(sol.getId());
      levelTierIds.add(sol.getLevelTier().getId());
      tierNames.add(codeNameConvert(sol.getLevelTier().getTierCode()));
      levelNames.add(codeNameConvert(sol.getLevelTier().getLevelCode()));
      counts.add(sol.getCount());
    });

    return SolutionRes.of(user, tier, solution, levelTierIds, tierNames, solutionIds, levelNames,
        counts, solutionVideoList);
  }

  private HistoryUserDto userConvert(User user, String tier, Long exp, Long nextExp) {
    HistoryUserDto userDto = HistoryUserDto.builder().profile(user.getProfile())
        .nickname(user.getNickname()).description(user.getDescription()).tier(tier).exp(exp)
        .nextExp(nextExp).build();
    return userDto;
  }

  private List<SolutionListItem> solutionListConvert(List<Solution> solutionList) {
    List<SolutionListItem> list = new LinkedList<SolutionListItem>();
    solutionList.forEach(solution -> {
      list.add(SolutionListItem.builder().id(solution.getId())
          .tier(codeNameConvert(solution.getLevelTier().getTierCode()))
          .climbingName(solution.getClimbing().getClimbingName())
          .level(codeNameConvert(solution.getLevelTier().getLevelCode())).cnt(solution.getCount())
          .date(solution.getDate()).build());
    });
    return list;
  }

  private int allHistorySize(Long id) {
    int size = 0;
    List<Solution> sol = solutionRepository.findAllByUserId(id);
    size = sol.size() / LIST_PER_PAGE + (sol.size() % LIST_PER_PAGE == 0 ? 0 : 1);
    return size;
  }

  private List<Solution> getSolutionList(Long userId, int pageNumber) {
    List<Solution> solutionList = new LinkedList<Solution>();
    PageRequest page = PageRequest.of(pageNumber, LIST_PER_PAGE,
        Sort.by(Sort.Direction.DESC, "date"));
    solutionList = solutionRepository.findByUserId(userId, page);
    return solutionList;
  }

  private String codeNameConvert(Long code) {
    String name = "";
    name = commonCodeRepository.findName(code);
    return name.toLowerCase();
  }

  private Long codeExpConvert(Long code) {
    Long exp = 0L;
    exp = commonCodeRepository.findTierExp(code);
    return exp;
  }

  private void uploadVideos(Long userId, LocalDateTime now, List<MultipartFile> videos) {
    log.info("[uploadVideos] - HistoryService : {}", videos);

    File videoFolder = new File(windowsPath);
    if (!videoFolder.exists()) {
      try {
        videoFolder.mkdirs();
      } catch (Exception e) {
        log.error("[uploadVideo] - VideoService : Failed to make directory");
        e.printStackTrace();
      }
    }

    List<SolutionVideo> solutionVideos = new ArrayList<>();
    for (int i = 0; i < videos.size(); i++) {
      String originalFileName = videos.get(i).getOriginalFilename();
      String extensionName = originalFileName.substring(originalFileName.lastIndexOf('.'),
          originalFileName.length());
      String fileName = userId + "-" + now.toString().replace(':', '.') + "-" + i + extensionName;
      log.info("[uploadVideo] - VideoService, fileName : {}", fileName);
      File dest = new File(windowsPath + fileName);
      try {
        videos.get(i).transferTo(dest);
      } catch (IllegalStateException | IOException e) {
        log.error("[uploadVideo] - VideoService : Failed to upload videos");
        e.printStackTrace();
      }
      SolutionVideo solutionVideo = SolutionVideo.builder().dateTime(now).uri(fileName)
          .uploadId(userId + "-" + now).build();
      solutionVideos.add(solutionVideo);
    }
    solutionVideoRepository.saveAll(solutionVideos);
  }

  @Override
  public List<Solution> createSolution(SolutionRequest solutionRequest) {
    log.info("[createSolution] - HistoryService : {}", solutionRequest);
    List<Solution> solutions = new ArrayList<>();

    User user = userRepository.findByOauthId(solutionRequest.getOauthId())
        .orElseThrow(RuntimeException::new);
    Climbing climbing = climbingRepository.findById(solutionRequest.getClimbingId())
        .orElseThrow(RuntimeException::new);
    LocalDateTime now = LocalDateTime.now();
    List<Long> levelTierIds = solutionRequest.getLevelTierIds();
    List<Integer> counts = solutionRequest.getCounts();
    for (int i = 0; i < solutionRequest.getLevelTierIds().size(); i++) {
      Long levelTierId = levelTierIds.get(i);
      LevelTier levelTier = levelTierRepository.findById(levelTierId)
          .orElseThrow(RuntimeException::new);

      Solution solution = Solution.builder().user(user).levelTier(levelTier).climbing(climbing)
          .count(counts.get(i)).date(solutionRequest.getDate()).uploadId(user.getId() + "-" + now)
          .build();
      solutions.add(solution);

      increaseUserExpByLevelTierAndCount(user, levelTier, counts.get(i));
    }
    if (solutionRequest.getVideos() != null) {
      uploadVideos(user.getId(), now, solutionRequest.getVideos());
    }
    return solutionRepository.saveAll(solutions);
  }

  @Override
  public List<Solution> updateSolution(SolutionRequest solutionRequest) {
    log.info("[updateSolution] - HistoryService : {}", solutionRequest);
    List<Solution> solutions = new ArrayList<>();

    User user = userRepository.findByOauthId(solutionRequest.getOauthId())
        .orElseThrow(RuntimeException::new);
    Climbing climbing = climbingRepository.findById(solutionRequest.getClimbingId())
        .orElseThrow(RuntimeException::new);
    LocalDateTime now = LocalDateTime.now();
    List<Long> levelTierIds = solutionRequest.getLevelTierIds();
    List<Integer> counts = solutionRequest.getCounts();
    List<Long> solutionIds = solutionRequest.getSolutionIds();
    for (int i = 0; i < solutionRequest.getLevelTierIds().size(); i++) {
      Long levelTierId = levelTierIds.get(i);

      LevelTier levelTier = levelTierRepository.findById(levelTierId)
          .orElseThrow(RuntimeException::new);
      Long solutionId = solutionIds.get(i);
      if (solutionId > 0) {
        Solution oldSolution = solutionRepository.getById(solutionId);
        Solution solution = Solution.builder().id(solutionId).user(user).levelTier(levelTier)
            .climbing(climbing).count(counts.get(i)).date(solutionRequest.getDate())
            .accumulateReport(oldSolution.getAccumulateReport()).uploadId(user.getId() + "-" + now)
            .deleteYN(oldSolution.getDeleteYN()).build();

        decreaseUserExpByLevelTierAndCount(user, oldSolution.getLevelTier(),
            oldSolution.getCount());

        solutions.add(solution);

        increaseUserExpByLevelTierAndCount(user, levelTier, counts.get(i));
      } else {
        Solution solution = Solution.builder().user(user).levelTier(levelTier).climbing(climbing)
            .count(counts.get(i)).date(solutionRequest.getDate()).uploadId(user.getId() + "-" + now)
            .build();

        solutions.add(solution);

        increaseUserExpByLevelTierAndCount(user, levelTier, counts.get(i));
      }
    }

    return solutionRepository.saveAll(solutions);
  }

  private void deleteVideos(String uploadId) {
    List<SolutionVideo> solutionVideos = solutionVideoRepository.findByUploadId(uploadId);

    for (SolutionVideo solutionVideo : solutionVideos) {
      File removeFile = new File(windowsPath + solutionVideo.getUri());
      log.info("[deleteVideos] - pathname : {}", removeFile.getPath());
      if (removeFile.exists()) {
        if (removeFile.delete())
          log.info("[deleteVideos] - success!!");
        else {
          log.info("[deleteVideos] - fail!!");
        }
      } else {
        log.info("[deleteVideos] - no file!!");
      }
    }

    solutionVideoRepository.removeByUploadId(uploadId);
  }

  @Override
  public Integer deleteSolution(String uploadId) {
    log.info("[deleteSolution] - HistoryService : {}", uploadId);
    deleteVideos(uploadId);
    Integer ret = solutionRepository.removeByUploadId(uploadId);
    log.info("ret : {}", ret);
    return ret;
  }

  /**
   * 사용자의 문제 풀이 기록으로 사용자 경험치를 올린다.
   *
   * @param user      사용자 정보
   * @param levelTier 난이도 정보
   * @param count     푼 문제 수
   */
  private void increaseUserExpByLevelTierAndCount(User user, LevelTier levelTier, Integer count) {
    Long nextExp;

    try {
      nextExp = getSolutionExpByTierCode(levelTier.getTierCode()) * count;
    } catch (ResourceNotFoundException e) {
      return;
    }

    user.setAccumulateExp(user.getAccumulateExp() + nextExp);

    increaseUserTierByUserExp(user);
    userRepository.save(user);
  }

  /**
   * 사용자 경험치가 요구 경험치를 초과했을 경우 티어를 올린다.
   *
   * @param user 사용자 정보
   */
  private void increaseUserTierByUserExp(User user) {
    try {
      if (user.getTierCode().equals(MAX_TIER_CODE)) {
        return;
      }
      Long nextLevelRequireExp = getTierExpByTierCode(user.getTierCode() + 1);
      log.info("[solutionCreate] next level require exp - {}", nextLevelRequireExp);
      log.info("[solutionCreate] curr user exp - {}", user.getAccumulateExp());
      for (; user.getAccumulateExp() >= nextLevelRequireExp
          && !user.getTierCode().equals(MAX_TIER_CODE); nextLevelRequireExp = getTierExpByTierCode(
              user.getTierCode() + 1)) {

        log.info("[solutionCreate] increase user tier");
        user.setTierCode(user.getTierCode() + 1);
      }
    } catch (ResourceNotFoundException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * 사용자의 문제 기록으로 사용자 경험치를 낮춘다.
   *
   * @param user      사용자 정보
   * @param levelTier 난이도 정보
   * @param count     푼 문제 수
   */

  private void decreaseUserExpByLevelTierAndCount(User user, LevelTier levelTier, Integer count) {
    Long nextExp;

    try {
      nextExp = getSolutionExpByTierCode(levelTier.getTierCode()) * count;
    } catch (ResourceNotFoundException e) {
      return;
    }

    user.setAccumulateExp(user.getAccumulateExp() - nextExp);

    decreaseUserTierByUserExp(user);
    userRepository.save(user);
  }

  /**
   * 사용자 경험치가 이전 티어의 요구 경험치보다 낮을 경우 티어를 낮춘다.
   *
   * @param user 사용자 정보
   */

  private void decreaseUserTierByUserExp(User user) {
    try {
      if (user.getTierCode().equals(MIN_TIER_CODE)) {
        return;
      }
      Long nextLevelRequireExp = getTierExpByTierCode(user.getTierCode() - 1);
      log.info("[solutionCreate] next level require exp - {}", nextLevelRequireExp);
      log.info("[solutionCreate] curr user exp - {}", user.getAccumulateExp());
      for (; user.getAccumulateExp() < nextLevelRequireExp
          && !user.getTierCode().equals(MIN_TIER_CODE); nextLevelRequireExp = getTierExpByTierCode(
              user.getTierCode() - 1)) {

        log.info("[solutionCreate] decrease user tier");
        user.setTierCode(user.getTierCode() - 1);
      }
    } catch (ResourceNotFoundException e) {
      log.error(e.getMessage());
    }
  }

  private Long getTierExpByTierCode(Long tierCode) {
    return Optional.ofNullable(commonCodeRepository.findTierExp(tierCode))
        .orElseThrow(() -> new ResourceNotFoundException("tier_exp", "tier_code", tierCode));
  }

  private Long getSolutionExpByTierCode(Long tierCode) {
    return Optional.ofNullable(commonCodeRepository.findSolutionExp(tierCode))
        .orElseThrow(() -> new ResourceNotFoundException("solution_exp", "tier_code", tierCode));
  }
}
