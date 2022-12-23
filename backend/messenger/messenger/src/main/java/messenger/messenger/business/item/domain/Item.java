package messenger.messenger.business.item.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;
    private String itemDescriptions;
    private String maker;
    private LocalDateTime expirationDate;
    private LocalDateTime manufacturingDate;

    private int price;
    private int quantity;

}
