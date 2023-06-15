package Chat.chattingApp.controller;

import Chat.chattingApp.entity.Member;
import Chat.chattingApp.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Member loginMember(@RequestBody @Valid MemberRegDTO request) {
        /*try {
            Long memberId = memberService.loginByNameAndEmail(request.name, request.email);
            log.info("여기까지 오나???");
            return ResponseEntity.ok(memberId); //로그인에 성공하면 200 과 유저 id를 반환

        } catch (Exception e) {
            log.info("로그인실패!!");
            return ResponseEntity.status(400).build(); //로그인에 실패하면 400 반환
        }*/

        Member member = memberService.loginByNameAndEmail(request.name, request.email);
        log.info("여기까지 오나???");
        return member;
        //return ResponseEntity.ok(memberId); //로그인에 성공하면 200 과 유저 id를 반환
    }


    @Data
    private static class MemberRegDTO {
        @NotEmpty
        private String name;
        @Email
        private String email;
    }
}
