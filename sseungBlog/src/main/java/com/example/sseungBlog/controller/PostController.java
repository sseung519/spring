package com.example.sseungBlog.controller;


import com.example.sseungBlog.dto.Comm;
import com.example.sseungBlog.dto.Post;
import com.example.sseungBlog.service.PostService;
import com.example.sseungBlog.util.PagingUtil;
import com.example.sseungBlog.util.PhotoUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PagingUtil pagingUtil;

    @Autowired
    PhotoUtil photoUtil;

    @GetMapping(value = "/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping(value = "/view")
    public String view(HttpServletRequest request, Model model, HttpSession session) {
        {
            try {
                Object memberId = session.getAttribute("member_id");
                int postId = Integer.parseInt(request.getParameter("postId"));
                String pageNum = request.getParameter("pageNum");
                String searchKey = request.getParameter("searchKey");
                String searchValue = request.getParameter("searchValue");

                if (searchValue != null) searchValue = URLDecoder.decode(searchValue, "UTF-8");
                //1. 조회수 늘리기
                postService.updateHitCount(postId);

                //2. 게시물 데이터 가져오기
                Post post = postService.getReadPost(postId);
                Map map = new HashMap<>();
                map.put("postId", postId);
                map.put("memberId", memberId);
                List<Comm> commList = postService.getCommList(map);
                model.addAttribute("commList", commList);

                //가져온 게시물이 없다면
                if (post == null) return "redirect:/list?pageNum=" + pageNum;

                String param = "pageNum=" + pageNum;

                //검색어가 있다면
                if (searchValue != null && !searchValue.equals("")) {
                    param += "&searchKey=" + searchKey;
                    param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
                }

                model.addAttribute("post", post);
                model.addAttribute("params", param);
                model.addAttribute("pageNum", pageNum);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return "post/view";
    }

    @RequestMapping(value = "/list", method = {
            RequestMethod.GET, RequestMethod.POST})
    public String list(HttpSession session, HttpServletRequest request, Model model) {
        try {

            int memberId = (int) session.getAttribute("member_id");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");
            String pageNum = request.getParameter("pageNum");

            pagingUtil.setCurrentPage(1);
            if (pageNum != null) {
                //현재 페이지의 값을 바꿔준다.
                pagingUtil.setCurrentPage(Integer.parseInt(pageNum));
            }
            if (searchValue == null) {
                //검색어가 없다면
                searchKey = "subject"; //검색 키워드의 디폴트는 subject
                searchValue = ""; //검색어의 디폴트는 빈문자열
            } else {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }


            Map map = new HashMap();
            map.put("memberId", memberId);
            map.put("searchKey", searchKey);
            map.put("searchValue", searchValue);

            // 1. 전체 게시물의 갯수를 가져온다(페이징 처리시 필요)
            int dataCount = postService.getDataCount(map);

            //2. 페이징 처리를 한다(준비단계)
            // numPerPage : 페이지당 보여줄 게시물 목록의 갯수
            pagingUtil.resetPaging(dataCount, 5);
            map.put("start", pagingUtil.getStart());
            map.put("end", pagingUtil.getEnd());

            //3.페이징 처리할 리스트를 가지고 온다.
            List<Post> lists = postService.getPostList(map);
            //4. 검색어에 대한 쿼리스트링을 만든다.
            String param = "";
            String listUrl = "/list";
            String articleUrl = "/view?pageNum=" + pagingUtil.getCurrentPage();
            if (searchValue != null && !searchValue.equals("")) {
                param = "searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }
            //검색어가 있다면
            if (!param.equals("")) {
                //listUrl의 값: /list?searchKey=subject&searchValue=네번째
                listUrl += "?" + param;
                //articleUrl의 값: /view?pageNum=1&searchKey=subject&searchValue=네번째
                articleUrl += "&" + param;
            }

            //5. 페이징 버튼을 만들어준다.
            String pageIndexList = pagingUtil.pageIndexList(listUrl);

            model.addAttribute("lists", lists); //DB에서 가져온 전체 게시물리스트
            model.addAttribute("articleUrl", articleUrl); //상세페이지로 이동하기 위한 url
            model.addAttribute("pageIndexList", pageIndexList); //페이징 버튼
            model.addAttribute("dataCount", dataCount); // 게시물의 전체 갯수
            model.addAttribute("searchKey", searchKey); //검색키워드
            model.addAttribute("searchValue", searchValue); //검색어


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "post/list";
    }

    @RequestMapping(value = "/notice", method = { RequestMethod.GET, RequestMethod.POST})
    public String notice(HttpSession session, HttpServletRequest request, Model model) {
        try {
            int memberId = (int) session.getAttribute("member_id");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");
            String pageNum = request.getParameter("pageNum");

            pagingUtil.setCurrentPage(1);
            if (pageNum != null) {
                // 현재 페이지의 값을 바꿔준다.
                pagingUtil.setCurrentPage(Integer.parseInt(pageNum));
            }
            if (searchValue == null) {
                // 검색어가 없다면
                searchKey = "subject"; // 검색 키워드의 디폴트는 subject
                searchValue = ""; // 검색어의 디폴트는 빈문자열
            } else {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }

            Map<String, Object> map = new HashMap<>();
            map.put("memberId", memberId);
            map.put("searchKey", searchKey);
            map.put("searchValue", searchValue);
            //공지사항만 가져오기 위해서 저장된 map에 공지사항인 001코드만 가져오기
            map.put("categoryCode", "001");

            // 1. 전체 공지사항 게시물의 갯수를 가져온다(페이징 처리시 필요)
            int dataCount = postService.getNoticeDataCount(map);

            // 2. 페이징 처리를 한다(준비단계)
            // numPerPage : 페이지당 보여줄 게시물 목록의 갯수
            pagingUtil.resetPaging(dataCount, 5);
            map.put("start", pagingUtil.getStart());
            map.put("end", pagingUtil.getEnd());

            // 3. 페이징 처리할 공지사항 리스트를 가지고 온다.
            List<Post> noticeList = postService.getNoticeList(map);

            // 4. 검색어에 대한 쿼리스트링을 만든다.
            String param = "";
            String listUrl = "/notice";
            String articleUrl = "/view?pageNum=" + pagingUtil.getCurrentPage();
            if (searchValue != null && !searchValue.equals("")) {
                param = "searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }
            // 검색어가 있다면
            if (!param.equals("")) {
                // listUrl의 값: /notice?searchKey=subject&searchValue=네번째
                listUrl += "?" + param;
                // articleUrl의 값: /view?pageNum=1&searchKey=subject&searchValue=네번째
                articleUrl += "&" + param;
            }

            // 5. 페이징 버튼을 만들어준다.
            String pageIndexList = pagingUtil.pageIndexList(listUrl);

            model.addAttribute("lists", noticeList); // DB에서 가져온 공지사항 리스트
            model.addAttribute("articleUrl", articleUrl); // 상세페이지로 이동하기 위한 url
            model.addAttribute("pageIndexList", pageIndexList); // 페이징 버튼
            model.addAttribute("dataCount", dataCount); // 공지사항의 전체 갯수
            model.addAttribute("searchKey", searchKey); // 검색키워드
            model.addAttribute("searchValue", searchValue); // 검색어
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "post/notice";
    }


    @PostMapping(value = "/insert")
    public String insertPost(Post post, HttpSession session) {
        //1. 세션에서 사용자 member_id 가져오기
        Object memberId = (int) session.getAttribute("member_id");

        try {
            if (memberId == null) {
                return "redirect:/login";
            } else {
                post.setMemberId((int) memberId); //insert 하기전 memberId 값 넣어줌
                postService.insertPost(post); //2. 포스트에 insert 해주는 서비스 호출
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/list";
    }

    @GetMapping(value = "/write")
    public String write() {
        return "post/write";
    }

    @GetMapping(value = "/rewrite")
    public String rewrite(HttpServletRequest request, Model model) {

        try {
            int postId = Integer.parseInt(request.getParameter("postId"));
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchkey");
            String searchValue = request.getParameter("searchValue");

            //게시물 데이터 가져오기
            Post post = postService.getReadPost(postId);

            if (post == null) return "redirect:/list?pageNum=" + pageNum;

            String param = "pageNum" + pageNum;

            if (searchValue != null && !searchValue.equals("")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
                //검색어가 있다면
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
            }

            model.addAttribute("post", post);
            model.addAttribute("params", param);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("searchKey", searchKey);
            model.addAttribute("searchValue", searchValue);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "post/rewrite";
    }

    @PostMapping(value = "/update")
    public String update(Post post, HttpSession session, HttpServletRequest request) {
        String pageNum = request.getParameter("pageNum");
        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");
        String param = "postId="+post.getPostId()+"&pageNum="+pageNum;

        try {
            if(searchValue != null && !searchValue.equals("")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
                //검색어가 있다면
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
            }

            Object memberId = session.getAttribute("member_id");

            if (memberId == null) {
                return "redirect:/login"; //세션 만료 시 로그인 페이지로 이동
            } else {
                postService.updatePost(post); //포스트 update 서비스 호출
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/view?" + param;
    }

    @DeleteMapping(value = "/delete/{postId}")
    public @ResponseBody ResponseEntity deletePost(@PathVariable("postId") int postId, HttpSession session) {
        try {
            postService.deletePost(postId);

            Object memberId = session.getAttribute("member_id");

            if(memberId == null) {
                return new ResponseEntity<String>("삭제권한이 없습니다.", HttpStatus.UNAUTHORIZED);
            } else {
                postService.deletePost(postId);
            }



        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("삭제실패. 관리자에게 문의하세요.", HttpStatus.BAD_REQUEST);
        }

        //ResponseEntity<첫번째 매개변수의 타입>(result결과, response상태코드)
        //HttpStatus.OK일떄는 ajax의 success함수로 결과가 출력된다.
        return new ResponseEntity<Integer>(postId, HttpStatus.OK);
    }



    //서버에 이미지 업로드 reqeust
    @PostMapping(value = "/postImgUpload")
    public String postImgUpload(MultipartHttpServletRequest request, Model model) {
        String uploadPath = photoUtil.ckUpload(request);

        model.addAttribute("uploaded", true);
        model.addAttribute("url", uploadPath);

        return "jsonView"; //model에 있는 값들이 json 객체 형식으로 forward 된다.
    }

    @PostMapping(value= "/insertComm")
    public String insertComm(Comm comm, HttpSession session, HttpServletRequest request) {
        String param = "";
        try {
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");
            Object memberId = session.getAttribute("member_id");
            String postId = request.getParameter("postId");
            param = "?postId=" + postId + "&pageNum=" + pageNum;

            if(searchValue != null && !searchValue.equals("")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
                //검색어가 있다면
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
            }

            if(memberId == null) {
                return "redirect:/login"; // 세션 만료시 로그인 페이지로 이동
            } else {
                comm.setPostId(Integer.parseInt(postId));
                comm.setMemberId((Integer) memberId);
                postService.insertComm(comm); // 포스트 update 서비스 호출

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/view" + param;
    }
}
