package com.tanvirchoudhury.robotichoover.model.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class Patches extends BaseDomainObject{

    @OneToMany(mappedBy = "patches", cascade = PERSIST)
    private List<Coordinates> coordinates;
}
