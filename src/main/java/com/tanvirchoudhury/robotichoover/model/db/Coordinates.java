package com.tanvirchoudhury.robotichoover.model.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "coordinates")
public class Coordinates extends BaseDomainObject {

    private int x;

    private int y;

    @ManyToOne
    @JoinColumn(name = "patch_id")
    private Patches patches;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(int x, int y, Patches patches) {
        this.x = x;
        this.y = y;
        this.patches = patches;
    }
}
