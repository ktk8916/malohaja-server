package malohaja.speak.interview.question.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.interview.question.domain.entity.Bookmark;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.entity.QuestionSkill;
import malohaja.speak.interview.question.domain.request.QuestionCreateRequest;
import malohaja.speak.interview.question.domain.request.QuestionUpdateRequest;
import malohaja.speak.interview.question.domain.response.QuestionResponse;
import malohaja.speak.interview.question.exception.BookmarkNotFoundException;
import malohaja.speak.interview.question.exception.InvalidQuestionWriterException;
import malohaja.speak.interview.question.repository.BookmarkRepository;
import malohaja.speak.interview.question.repository.QuestionRepository;
import malohaja.speak.member.domain.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionCommandService {

    private final QuestionRepository questionRepository;
    private final BookmarkRepository bookmarkRepository;

    public QuestionResponse questionCreate(TokenInfo tokenInfo, QuestionCreateRequest request){
        Question question = Question.builder()
                .member(Member.fromId(tokenInfo.getId()))
                .content(request.content())
                .build();

        Question savedQuestion = questionRepository.save(question);

        Set<QuestionSkill> skills = of(question, request.skills());

        savedQuestion.setSkills(skills);

        return QuestionResponse.fromEntity(savedQuestion);
    }

    public QuestionResponse questionUpdate(Long id, TokenInfo tokenInfo, QuestionUpdateRequest request){
        Question question = questionRepository.getByQuestionId(id);

        if(!isValidMember(question, tokenInfo.getId())){
            throw new InvalidQuestionWriterException();
        }

        Set<QuestionSkill> skills = of(question, request.skills());

        question.update(request.content(), skills);

        return QuestionResponse.fromEntity(question);
    }

    public void addBookmark(Long questionId, TokenInfo tokenInfo) {
        Question question = questionRepository.getByQuestionId(questionId);
        Member member = Member.fromId(tokenInfo.getId());

        Bookmark bookmark = Bookmark.createBookmark(question, member);
        bookmarkRepository.save(bookmark);
    }
    public void removeBookmark(Long questionId, TokenInfo tokenInfo) {
        Bookmark bookmark = getBookmarkBy(questionId, tokenInfo);
        bookmarkRepository.delete(bookmark);
    }

    private Bookmark getBookmarkBy(Long questionId, TokenInfo tokenInfo) {
        return bookmarkRepository.findByQuestionIdAndMemberId(questionId, tokenInfo.getId())
                .orElseThrow(BookmarkNotFoundException::new);
    }

    private static Set<QuestionSkill> of(Question question, List<String> skills) {
        return SkillType.convertList(skills).stream()
                .map(skill -> QuestionSkill.builder()
                        .question(question)
                        .skill(skill)
                        .build())
                .collect(Collectors.toSet());
    }

    private boolean isValidMember(Question question, Long memberId){
        return Objects.equals(question.getMember().getId(), memberId);
    }
}
