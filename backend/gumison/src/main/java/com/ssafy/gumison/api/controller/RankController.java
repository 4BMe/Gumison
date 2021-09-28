package com.ssafy.gumison.api.controller;


import com.ssafy.gumison.api.response.UserHistoryRes;
import com.ssafy.gumison.common.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/rank")
@RestController
@RequiredArgsConstructor
public class RankController {
//	@ApiOperation(value = "유저 랭크", notes = "해당 사용자 랭크 출력", response = ApiResponseDto.class)
//
//	@GetMapping("/detail/{nickname}")
//	public ApiResponseDto<> userRank(@PathVariable("nickname") String nickname) {
//
//
//	}
}
