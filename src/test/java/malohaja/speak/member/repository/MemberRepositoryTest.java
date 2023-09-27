package malohaja.speak.member.repository;

import malohaja.speak.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("oauth2 정보로 member를 조회한다.")
    @Test
    void findByProviderNameAndProviderId(){
        // given
        String providerName = "kakao";
        String providerId = "11223344";

        Member member = Member.builder()
                .providerName(providerName)
                .providerId(providerId)
                .build();

        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findByProviderNameAndProviderId(providerName, providerId).get();

        // then
        Assertions.assertThat(findMember.getProviderName()).isEqualTo(providerName);
        Assertions.assertThat(findMember.getProviderId()).isEqualTo(providerId);
    }
}