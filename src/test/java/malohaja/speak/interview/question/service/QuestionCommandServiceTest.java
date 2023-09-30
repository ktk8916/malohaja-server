package malohaja.speak.interview.question.service;

import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.global.jwt.JwtService;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.interview.question.domain.request.QuestionCreateRequest;
import malohaja.speak.interview.question.domain.response.QuestionResponse;
import malohaja.speak.interview.question.repository.QuestionRepository;
import malohaja.speak.member.domain.entity.Member;
import malohaja.speak.member.domain.entity.Role;
import malohaja.speak.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class QuestionCommandServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private QuestionCommandService questionCommandService;

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("요청을 받아 질문을 생성한다.")
    @Test
    void questionCreate() {
        // given
        List<String> skills = List.of("SPRING");
        QuestionCreateRequest request = new QuestionCreateRequest("di가 무엇인가요 ?", skills);

        Member member = Member.builder()
                .nickName("취준생")
                .providerName("kakao")
                .providerId("kakaoId")
                .profileImageUri("image")
                .role(Role.MEMBER)
                .build();

        Member savedMember = memberRepository.save(member);

        String token = jwtService.generateToken(savedMember);
        TokenInfo tokenInfo = jwtService.extractUser(token);

        // when
        QuestionResponse response = questionCommandService.questionCreate(tokenInfo, request);

        // then
        assertThat(response)
                .extracting("id", "skills", "content", "likeCount", "memberId")
                .contains(1L, SkillType.convertList(skills), "di가 무엇인가요 ?", 0, 1L);
    }

}