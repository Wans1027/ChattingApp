package Chat.chattingApp.controller;

import Chat.chattingApp.entity.Member;
import Chat.chattingApp.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> loginMember(@RequestBody @Valid MemberRegDTO request) {


        Member member = memberService.loginByNameAndEmail(request.name, request.email);
        if(member == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        return ResponseEntity.ok(member.getId());

    }


    @Data
    private static class MemberRegDTO {
        @NotEmpty
        private String name;
        @Email
        private String email;
    }
}
