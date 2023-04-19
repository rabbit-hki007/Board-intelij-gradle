package com.study;

import com.study.common.dto.SearchDto;
import com.study.common.paging.PagingResponse;
import com.study.domain.post.PostResponse;
import com.study.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestApiTestController {

    private PostService postService;

    @GetMapping("/members") // 테스트용 코드 작성용
    @ResponseBody // public @ResponseBody List<Map<String, Object>> findAllMember()와 같이 리턴 타입 앞에도 선언 가능
    public List<Map<String, Object>> findAllMember() {

        List<Map<String, Object>> members = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            Map<String, Object> member = new HashMap<>();
            member.put("id", i);
            member.put("name", i + "번 개발자");
            member.put("age", 10 + i);
            members.add(member);
        }

        return members;
    }

     @GetMapping("/posts")
    public @ResponseBody PagingResponse<PostResponse> findAllPost() {
        return postService.findAllPost(new SearchDto());
    }

}