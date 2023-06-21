package Chat.chattingApp.service;


import Chat.chattingApp.entity.Member;
import Chat.chattingApp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Member loginByNameAndEmail(String name, String email) {
        return memberRepository.findByNameAndEmail(name, email);
    }

    public void modifyMember(String name, String email, String newName, String newEmail) {
        Member m = memberRepository.findByNameAndEmail(name, email);
        m.setName(newName);
        m.setEmail(newEmail);
    }

    public void deleteMember(Long userId) {
        memberRepository.deleteById(userId);
    }

}
