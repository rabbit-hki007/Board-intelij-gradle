package com.study.domain.post;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다. (글작성 수정 삭제 공통모듈)
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }

     // 신규 게시글 생성 (showMessageAndRedirect 미사용 했을때)
//    @PostMapping("/post/save.do")
//    public String savePost(final PostRequest params) {
//        postService.savePost(params);
//        return "redirect:/post/list.do";
//    }


    // 게시글 작성 화면 페이지
    @GetMapping("/post/write.do")
    public String openPostWrite(@RequestParam(value = "id", required = false) final Long id, Model model) {
        // @RequestParam(value = "id", required = false) final Long id
        // 글쓰기 일때는 id 값이 없지만
        // 수정일때는 id 값이 넘어온다
        if (id != null) {
            PostResponse post = postService.findPostById(id);
            model.addAttribute("post", post);
        }
        return "post/write";
    }


    // 게시글 상세 페이지
    @GetMapping("/post/view.do")
    public String openPostView(@RequestParam final Long id, Model model) {
        PostResponse post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "post/view";
    }


    // 신규 게시글 생성 (showMessageAndRedirect 사용 했을때)
    @PostMapping("/post/save.do")
    public String savePost(final PostRequest params, Model model) {
        postService.savePost(params);
        MessageDto message = new MessageDto("게시글 생성이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);} // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.

      // 기존 게시글 수정(showMessageAndRedirect 미사용 했을때)
//    @PostMapping("/post/update.do")
//    public String updatePost(final PostRequest params) {
//        postService.updatePost(params);
//        return "redirect:/post/list.do";
//    }

    // 기존 게시글 수정
    @PostMapping("/post/update.do")
    public String updatePost(final PostRequest params, final SearchDto queryParams, Model model) {
        postService.updatePost(params);
        // MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        // 27강에서 아래로 수정 되었음 final SearchDto queryParams을 javascript를 통해서 넘겨 받았음
        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list.do", RequestMethod.GET, queryParamsToMap(queryParams));
        return showMessageAndRedirect(message, model); // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    }

      // 게시글 삭제 (showMessageAndRedirect 미사용 했을때)
//    @PostMapping("/post/delete.do")
//    public String deletePost(@RequestParam final Long id) {
//        postService.deletePost(id);
//        return "redirect:/post/list.do";
//    }

    // 게시글 삭제
//    @PostMapping("/post/delete.do")
//    public String deletePost(@RequestParam final Long id, Model model) {
//        postService.deletePost(id);
//        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
//        return showMessageAndRedirect(message, model); // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
//    }

    // 게시글 삭제 27강 에서 수정된 부분
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam final Long id, final SearchDto queryParams, Model model) {
        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, queryParamsToMap(queryParams));
        return showMessageAndRedirect(message, model);
    }

    @GetMapping({"","/"})
    public String index() {
        return "redirect:post/list.do";
    }


    // 게시글 리스트 페이지 3단계
    @GetMapping("/post/list.do")
    public String openPostList(@ModelAttribute("params") final SearchDto params, Model model) {
        PagingResponse<PostResponse> response = postService.findAllPost(params);
        model.addAttribute("response", response);
        return "post/list";
    }

     //게시글 리스트 페이지 2단계
      // 리스트 2단계 수정
//    @GetMapping("/post/list.do")
//    public String openPostList(@ModelAttribute("params") final SearchDto params, Model model) {
//        List<PostResponse> posts = postService.findAllPost(params);
//        model.addAttribute("posts", posts);
//        return "post/list";
//    }


    // 게시글 리스트 페이지 1단계
    // 페이징 처리 전에 것임 (그냥 모두다 가져오기)
//    @GetMapping("/post/list.do")
//    public String openPostList(Model model) {
//        List<PostResponse> posts = postService.findAllPost();
//        model.addAttribute("posts", posts);
//        return "post/list";
//    }

    // 쿼리 스트링 파라미터를 Map에 담아 반환
    private Map<String, Object> queryParamsToMap(final SearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }

}
