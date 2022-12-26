package messenger.messenger.business.friend.presentation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.messenger.business.common.BaseDefaultPageable;

@Data
@NoArgsConstructor
public class MyFriendsReqDto extends BaseDefaultPageable {

    private Long id;

    @Builder
    public MyFriendsReqDto(Long id, Integer page, Integer size) {
        super(page, size);
        this.id = id;
    }



}
