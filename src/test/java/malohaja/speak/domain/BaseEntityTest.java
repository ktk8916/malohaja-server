package malohaja.speak.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaseEntityTest {

    @DisplayName("entity가 생성되면 삭제여부가 false이다.")
    @Test
    void createBaseEntity(){
        //given
        BaseEntity baseEntity = new BaseEntity() {};

        //then, when
        assertThat(baseEntity.isDeleted()).isFalse();
    }

    @DisplayName("entity를 삭제하면 삭제일이 업데이트 되고, 삭제여부가 true가 된다.")
    @Test
    void baseEntityDelete(){
        //given
        BaseEntity baseEntity = new BaseEntity() {};

        //then
        baseEntity.delete();

        //when
        assertThat(baseEntity.getDeletedAt()).isNotNull();
        assertThat(baseEntity.isDeleted()).isTrue();
    }
}