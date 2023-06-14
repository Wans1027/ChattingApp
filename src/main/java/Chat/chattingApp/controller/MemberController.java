package Chat.chattingApp.controller;

import Chat.chattingApp.entity.Member;
import Chat.chattingApp.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    //회원가입
    @PostMapping("/register")
    public void regMember(@RequestBody @Valid MemberRegDTO request){
        Member member = new Member(request.name, request.email);
        memberService.saveMember(member);
    }

    @PostMapping("/login")
    public void loginMember(@RequestBody @Valid MemberRegDTO request) {

    }


    @Data
    private static class MemberRegDTO {
        @NotEmpty
        private String name;
        @Email
        private String email;
    }
}
