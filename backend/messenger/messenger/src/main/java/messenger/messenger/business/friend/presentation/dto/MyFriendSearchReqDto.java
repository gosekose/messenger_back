package messenger.messenger.business.friend.presentation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.messenger.business.common.BaseDefaultPageable;

@Data
@NoArgsConstructor
public class MyFriendSearchReqDto extends BaseDefaultPageable {

    private Long id;
    private String name;

    @Builder
    public MyFriendSearchReqDto(Integer page, Integer size, Long id, String name) {
        super(page, size);
        this.id = id;
        this.name = name;
    }
}
