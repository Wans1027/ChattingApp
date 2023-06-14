package Chat.chattingApp.service;


import Chat.chattingApp.entity.Member;
import Chat.chattingApp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Long loginByNameAndEmail(String name, String email) throws Exception {
        Member m = memberRepository.findByNameAndEmail(name, email);
        if(m == null) throw new Exception();
        else{
            return m.getId();
        }
    }

}
