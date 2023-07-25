package Chat.chattingApp.repository;

import Chat.chattingApp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    Optional<Member> findById(Long aLong);

    @Transactional(readOnly = true)
    @Query("select m from Member m where m.name = :name and m.email = :email")
    Member findByNameAndEmail(@Param("name")String name, @Param("email") String email);

}
