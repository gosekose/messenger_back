package messenger.messenger.business.school.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import messenger.messenger.business.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class School extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "school_id")
    private Long id;

    private String schoolName;
    private String schoolAddress;
    private String schoolRegistrationNum;
}
