package malohaja.speak.global.domain.skill;

import malohaja.speak.global.exception.NoMatchingSkillException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SkillTypeTest {

    @DisplayName("이름으로 SkillType을 생성한다.")
    @Test
    void fromString(){

        SkillType skillType = SkillType.fromString("JAVA");

        assertThat(skillType.name()).isEqualTo("JAVA");
    }

    @DisplayName("유효하지 않은 이름으로 SkillType을 생성하면 예외가 발생한다.")
    @Test
    void invalidSkillName(){

        assertThatThrownBy(()->SkillType.fromString("그냥있을수가없는스킬이름임"))
                .isInstanceOf(NoMatchingSkillException.class);
    }

    @DisplayName("스킬 이름 list로 SkillType set 을 생성한다.")
    @Test
    void convertList(){
        // given
        List<String> skills = List.of("JAVA", "SPRING");

        // when
        Set<SkillType> skillTypes = SkillType.convertList(skills);

        // then
        assertThat(skillTypes).hasSize(2)
                .containsExactlyInAnyOrder(SkillType.SPRING, SkillType.JAVA);
    }

    @DisplayName("빈 list로 빈 SkillType set 을 생성한다.")
    @Test
    void convertEmptyList(){
        // given
        List<String> emptySkills = List.of();

        // when
        Set<SkillType> skillTypes = SkillType.convertList(emptySkills);

        // then
        assertThat(skillTypes).isEmpty();
    }
}