
package ar.com.ada.api.aladas.entities;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "pasajero")
public class Pasajero extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pasajero_id")
    private Integer pasajeroId;

    @OneToMany(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public void agregarReserva(Reserva reserva) { //agregarReseva se encarga de la relacion bidereccional
        this.reservas.add(reserva); //en la linea 20 y 21 se genera la realcion bidreccional
        reserva.setPasajero(this); // this es todo el pasajero
    }

}