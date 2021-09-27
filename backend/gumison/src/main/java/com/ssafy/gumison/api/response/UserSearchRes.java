package com.ssafy.gumison.api.response;

import java.util.ArrayList;
import java.util.List;
import com.ssafy.gumison.common.dto.UserSearchDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchRes {

  List<UserSearchDto> users = new ArrayList<UserSearchDto>();

}
