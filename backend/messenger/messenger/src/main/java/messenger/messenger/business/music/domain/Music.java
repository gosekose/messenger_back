package messenger.messenger.business.music.domain;

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
public class Music extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "music_id")
    private Long id;

    private String musicName;
    private String singer;
    private String composer;


}
