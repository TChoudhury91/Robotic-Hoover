package com.tanvirchoudhury.robotichoover.model.db;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "unclean_environment")
public class UncleanEnvironment extends BaseDomainObject{

    @OneToOne(cascade = PERSIST)
    @JoinColumn(name = "room_size_coordinates_id")
    private Coordinates roomSize;

    @OneToOne(cascade = PERSIST)
    @JoinColumn(name = "coords_coordinates_id")
    private Coordinates coords;

    @OneToOne(cascade = PERSIST)
    @JoinColumn(name = "patches_id")
    private Patches patches;

    @Column
    private String instructions;
}
