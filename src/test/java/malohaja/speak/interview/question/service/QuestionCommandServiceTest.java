package malohaja.speak.interview.question.service;

import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.global.jwt.JwtService;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.interview.question.domain.request.QuestionCreateRequest;
import malohaja.speak.interview.question.domain.request.QuestionUpdateRequest;
import malohaja.speak.interview.question.domain.response.QuestionResponse;
import malohaja.speak.interview.question.exception.InvalidQuestionWriterException;
import malohaja.speak.interview.question.repository.QuestionRepository;
import malohaja.speak.member.domain.entity.Member;
import malohaja.speak.member.domain.entity.Role;
import malohaja.speak.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @AfterEach
    void clear(){
        questionRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("요청을 받아 질문을 생성한다.")
    @Test
    void questionCreate() {
        // given
        List<String> skills = List.of("SPRING");
        QuestionCreateRequest request = new QuestionCreateRequest("di가 무엇인가요 ?", skills);

        Member member = Member.builder()
                .nickname("취준생")
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
                .extracting("skills", "content", "likeCount", "memberId")
                .contains(SkillType.convertList(skills), "di가 무엇인가요 ?", 0, savedMember.getId());
    }
    @DisplayName("질문을 수정한다.")
    @Test
    void questionUpdate() {
        // given
        List<String> skills = List.of("SPRING");
        QuestionCreateRequest createRequest = new QuestionCreateRequest("di가 무엇인가요 ?", skills);

        Member member = Member.builder()
                .nickname("취준생")
                .providerName("kakao")
                .providerId("kakaoId")
                .profileImageUri("image")
                .role(Role.MEMBER)
                .build();

        Member savedMember = memberRepository.save(member);

        String token = jwtService.generateToken(savedMember);
        TokenInfo tokenInfo = jwtService.extractUser(token);

        QuestionResponse createdQuestioned = questionCommandService.questionCreate(tokenInfo, createRequest);

        List<String> updateSkills = List.of("JAVA", "SPRING");
        QuestionUpdateRequest updateRequest = new QuestionUpdateRequest("이름이 뭔가요 ?", updateSkills);

        // when
        QuestionResponse response = questionCommandService.questionUpdate(createdQuestioned.id(), tokenInfo, updateRequest);

        // then
        assertThat(response)
                .extracting("skills", "content", "likeCount", "memberId")
                .contains(SkillType.convertList(updateSkills), "이름이 뭔가요 ?", 0, savedMember.getId());
    }
    @DisplayName("질문의 작성자가 아닌 회원이 수정하려는 경우, 예외가 발생한다.")
    @Test
    void invalidMemberQuestionUpdate() {
        // given
        List<String> skills = List.of("SPRING");
        QuestionCreateRequest createRequest = new QuestionCreateRequest("di가 무엇인가요 ?", skills);

        Member member = Member.builder()
                .nickname("취준생")
                .providerName("kakao")
                .providerId("kakaoId")
                .profileImageUri("image")
                .role(Role.MEMBER)
                .build();

        Member savedMember = memberRepository.save(member);

        String token = jwtService.generateToken(savedMember);
        TokenInfo tokenInfo = jwtService.extractUser(token);

        QuestionResponse createdQuestioned = questionCommandService.questionCreate(tokenInfo, createRequest);

        List<String> updateSkills = List.of("JAVA", "SPRING");
        QuestionUpdateRequest updateRequest = new QuestionUpdateRequest("이름이 뭔가요 ?", updateSkills);

        Member anotherMember = Member.builder()
                .nickname("취준생2")
                .providerName("kakao2")
                .providerId("kakaoId2")
                .profileImageUri("image2")
                .role(Role.MEMBER)
                .build();

        String anotherMemberToken = jwtService.generateToken(anotherMember);
        TokenInfo anotherMemberTokenInfo = jwtService.extractUser(anotherMemberToken);

        // when, then
        assertThatThrownBy(()->questionCommandService.questionUpdate(createdQuestioned.id(), anotherMemberTokenInfo, updateRequest))
                .isInstanceOf(InvalidQuestionWriterException.class);
    }

}