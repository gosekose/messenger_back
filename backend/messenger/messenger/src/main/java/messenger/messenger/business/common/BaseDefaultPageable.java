package messenger.messenger.business.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseDefaultPageable implements BasePageable{

    Integer page;
    Integer size;

    public BaseDefaultPageable(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public void updatePageable() {
        if (this.page == null) this.page = 0;
        if (this.size == null) this.size = 10;
    }

}
