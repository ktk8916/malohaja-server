package malohaja.speak.member.domain.entity;

import malohaja.speak.member.exception.NoMatchingJobException;

import java.util.Arrays;

public enum JobType {

    FRONTEND,
    BACKEND,
    IOS,
    ANDROID,
    DESIGNER,
    PLANNER,
    GAME_CLIENT,
    GAME_SERVER,
    ETC
    ;
    public static JobType fromString(String job){
        return Arrays.stream(JobType.values())
                .filter(jobType -> jobType.name().equalsIgnoreCase(job))
                .findFirst()
                .orElseThrow(NoMatchingJobException::new);
    }
}
