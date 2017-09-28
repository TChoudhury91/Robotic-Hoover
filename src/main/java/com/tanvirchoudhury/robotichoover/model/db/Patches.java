package com.tanvirchoudhury.robotichoover.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Patches extends BaseDomainObject{

    @OneToMany(mappedBy = "patches")
    private List<Coordinates> coordinates;
}
