package messenger.messenger.business.friend.presentation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyResUserDto {

    private Long userId;

    @Builder
    public MyResUserDto(Long userId) {
        this.userId = userId;
    }
}
