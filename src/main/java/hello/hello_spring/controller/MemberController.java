package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;

import hello.hello_spring.service.MemberService;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
