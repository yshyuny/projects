package com.example.demo.controller;

import java.io.IOException;
import java.lang.*;
import java.util.logging.Logger;

import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import lombok.SneakyThrows;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    //@getmapping => 어떤 url로 들어갈건지 지정 //localhost:8080/board/write 이 주소로 접속을하면 return을 보여주겠다.
    public String boardWriteForm() {
        return "boardwrite"; // 어떤 html 파일로 보내줄 것이냐
    }


    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model,@RequestParam("file") MultipartFile file) throws Exception{

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }


    @GetMapping("/board/list")
    public String boardList(Model model,
                           @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {



        Page<Board> list = null;

        if(searchKeyword == null) {
             list = boardService.boardList(pageable);
        }else {
             list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

 @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String boardView(Model model, @RequestParam(name="id")Integer id) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
 }

 @GetMapping("/board/delete")
    public String boardDelete( @RequestParam(name="id")Integer id) {

        boardService.boardDelete(id);

        return "redirect:/board/list";
 }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model) {


        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable(name = "id") Integer id, Board board, @RequestParam("file") MultipartFile file) throws Exception  { // @RequestParam("file") MultipartFile file로 받아야함 개 시발 챗 gpt가 날 살림

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);


        return "redirect:/board/list";

    }


}
