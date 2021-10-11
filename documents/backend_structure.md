```
backend/gumison/src
├── main
│   ├── generated
│   │   └── com
│   │       └── ssafy
│   │           └── gumison
│   │               └── db
│   │                   └── entity
│   │                       ├── QClimbing.java
│   │                       ├── QCommonCode.java
│   │                       ├── QHours.java
│   │                       ├── QLevelTier.java
│   │                       ├── QSolution.java
│   │                       ├── QSolutionVideo.java
│   │                       └── QUser.java
│   ├── java
│   │   └── com
│   │       └── ssafy
│   │           └── gumison
│   │               ├── GumisonApplication.java
│   │               ├── api
│   │               │   ├── controller
│   │               │   │   ├── ClimbingController.java
│   │               │   │   ├── ContributionController.java
│   │               │   │   ├── HistoryController.java
│   │               │   │   ├── RankController.java
│   │               │   │   └── UserController.java
│   │               │   ├── request
│   │               │   │   ├── ContributionRequest.java
│   │               │   │   └── SolutionRequest.java
│   │               │   ├── response
│   │               │   │   ├── ClimbingRes.java
│   │               │   │   ├── ClimbingSolutionRes.java
│   │               │   │   ├── HistoryRes.java
│   │               │   │   ├── RankPageCountRes.java
│   │               │   │   ├── SolutionListItem.java
│   │               │   │   ├── SolutionListRes.java
│   │               │   │   ├── SolutionRes.java
│   │               │   │   ├── UserRankListRes.java
│   │               │   │   ├── UserRankRes.java
│   │               │   │   └── UserSearchRes.java
│   │               │   └── service
│   │               │       ├── ClimbingService.java
│   │               │       ├── ClimbingServiceImpl.java
│   │               │       ├── ContributionService.java
│   │               │       ├── ContributionServiceImpl.java
│   │               │       ├── HistoryService.java
│   │               │       ├── HistoryServiceImpl.java
│   │               │       ├── RankService.java
│   │               │       ├── RankServiceImpl.java
│   │               │       ├── UserService.java
│   │               │       └── UserServiceImpl.java
│   │               ├── common
│   │               │   ├── dto
│   │               │   │   ├── ClimbingSearchDto.java
│   │               │   │   ├── ClimbingSolutionDto.java
│   │               │   │   ├── ContributionDto.java
│   │               │   │   ├── HistoryUserDto.java
│   │               │   │   ├── HoursDto.java
│   │               │   │   ├── LevelTierDto.java
│   │               │   │   ├── SolutionDto.java
│   │               │   │   ├── UserExpTierDto.java
│   │               │   │   ├── UserOauthDto.java
│   │               │   │   ├── UserRankDto.java
│   │               │   │   └── UserSearchDto.java
│   │               │   ├── enums
│   │               │   │   ├── AuthTypeName.java
│   │               │   │   ├── CodeTypeName.java
│   │               │   │   ├── HoursDay.java
│   │               │   │   └── RedisKey.java
│   │               │   ├── exception
│   │               │   │   ├── BadRequestException.java
│   │               │   │   ├── OAuth2AuthenticationProcessingException.java
│   │               │   │   ├── ResourceNotFoundException.java
│   │               │   │   └── handler
│   │               │   │       └── ControllerAdvice.java
│   │               │   ├── response
│   │               │   │   ├── ApiResponseCode.java
│   │               │   │   └── ApiResponseDto.java
│   │               │   └── util
│   │               │       ├── CookieUtils.java
│   │               │       ├── HashIdGenerator.java
│   │               │       └── Scheduler.java
│   │               ├── config
│   │               │   ├── AppConfig.java
│   │               │   ├── JpaConfig.java
│   │               │   ├── RedisConfig.java
│   │               │   ├── SecurityConfig.java
│   │               │   ├── SwaggerConfig.java
│   │               │   └── WebMvcConfig.java
│   │               ├── db
│   │               │   ├── entity
│   │               │   │   ├── Climbing.java
│   │               │   │   ├── CommonCode.java
│   │               │   │   ├── Contribution.java
│   │               │   │   ├── Hours.java
│   │               │   │   ├── LevelTier.java
│   │               │   │   ├── Solution.java
│   │               │   │   ├── SolutionVideo.java
│   │               │   │   └── User.java
│   │               │   └── repository
│   │               │       ├── ClimbingRepository.java
│   │               │       ├── CommonCodeRepository.java
│   │               │       ├── ContributionRepository.java
│   │               │       ├── HoursRepository.java
│   │               │       ├── LevelTierRepository.java
│   │               │       ├── SolutionRepository.java
│   │               │       ├── SolutionVideoRepository.java
│   │               │       ├── UserRepository.java
│   │               │       └── UserRepositorySupport.java
│   │               ├── redis
│   │               │   ├── RankProvider.java
│   │               │   ├── RankProviderImpl.java
│   │               │   ├── UserRankSearchKeywordRepository.java
│   │               │   └── UserRankSearchKeywordRepositoryImpl.java
│   │               └── security
│   │                   ├── CurrentUser.java
│   │                   ├── RestAuthenticationEntryPoint.java
│   │                   ├── TokenAuthenticationFilter.java
│   │                   ├── TokenProvider.java
│   │                   ├── UserPrincipal.java
│   │                   └── oauth2
│   │                       ├── CustomOAuth2UserService.java
│   │                       ├── HttpCookieOAuth2AuthorizationRequestRepository.java
│   │                       ├── OAuth2AuthenticationFailureHandler.java
│   │                       ├── OAuth2AuthenticationSuccessHandler.java
│   │                       └── user
│   │                           ├── GoogleOAuth2UserInfo.java
│   │                           ├── OAuth2UserInfo.java
│   │                           └── OAuth2UserInfoFactory.java
│   └── resources
│       ├── application-prod.yml
│       ├── application.yml
│       ├── gumison.ts
│       └── gumisonkey.jks
└── test
    └── java
        └── com
            └── ssafy
                └── gumison
                    ├── RedisConnectionTest.java
                    ├── api
                    │   └── service
                    │       ├── HistoryServiceTest.java
                    │       └── RankServiceTest.java
                    ├── common
                    │   └── util
                    │       └── RankProviderTest.java
                    ├── db
                    │   └── repository
                    │       ├── UserRepositorySupportTest.java
                    │       └── UserRepositoryTest.java
                    └── redis
                        └── UserRankSearchKeywordRepositoryTest.java
```
