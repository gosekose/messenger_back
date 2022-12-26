package messenger.messenger.business.friend.infra.query.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyFriendsDto {

    private Long id;
    private String friendName;

    @Builder
    @QueryProjection
    public MyFriendsDto(Long id, String friendName) {
        this.id = id;
        this.friendName = friendName;
    }
}
