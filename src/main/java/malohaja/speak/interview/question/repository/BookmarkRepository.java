package malohaja.speak.interview.question.repository;

import malohaja.speak.interview.question.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    /*
    select b from Bookmark b
    where b.questionId = ?
    and b.memberId = ?
     */
    Optional<Bookmark> findByQuestionIdAndMemberId(Long questionId, Long memberId);
}
