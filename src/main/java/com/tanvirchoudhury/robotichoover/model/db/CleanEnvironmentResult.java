package com.tanvirchoudhury.robotichoover.model.db;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import static javax.persistence.CascadeType.PERSIST;

@Getter
@Entity
@AllArgsConstructor
public class CleanEnvironmentResult extends BaseDomainObject {

    @OneToOne(cascade = PERSIST)
    @JoinColumn(name = "coords_coordinates_id")
    private Coordinates coords;

    private int patchesCleaned;
}