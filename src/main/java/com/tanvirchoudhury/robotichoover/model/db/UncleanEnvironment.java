package com.tanvirchoudhury.robotichoover.model.db;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "unclean_environment")
public class UncleanEnvironment extends BaseDomainObject{

    @OneToOne
    @JoinColumn(name = "room_size_coordinates_id")
    private Coordinates roomSize;

    @OneToOne
    @JoinColumn(name = "coords_coordinates_id")
    private Coordinates coords;

    @OneToOne
    @JoinColumn(name = "patches_id")
    private Patches patches;

    @Column
    private String instructions;
}
