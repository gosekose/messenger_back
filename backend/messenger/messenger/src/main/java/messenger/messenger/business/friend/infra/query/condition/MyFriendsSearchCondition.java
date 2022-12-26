package messenger.messenger.business.friend.infra.query.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFriendsSearchCondition {

    private Long id;
    private String name;

}
