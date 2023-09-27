package malohaja.speak.member.repository;

import malohaja.speak.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /*
    select * from member m
    where m.provider_name = ?
    and m.provider_id = ?
     */
    Optional<Member> findByProviderNameAndProviderId(String providerName, String providerId);
}
