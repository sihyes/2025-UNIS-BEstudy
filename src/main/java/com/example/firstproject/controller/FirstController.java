package com.example.firstproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //컨트롤러 선언, 어노테이션 : 메타데이터의 일종

public class FirstController {
    @GetMapping("/hi") //url 요청 접수 localhost:8080/hi로 접근 시 greetings.mustache를 반환하라는 뜻
    public String niceToMeetYou(Model model) {
        model.addAttribute("username","시은");
        return"greetings"; //서버가 알아서 greetings.mustache 파일 찾아서 반환

    }
    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname","시은");
        return "goodbye";

    }
}
