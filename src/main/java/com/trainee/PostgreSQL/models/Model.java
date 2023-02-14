package com.trainee.PostgreSQL.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table( name = "model" )
public class Model implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column( nullable = false )
    @NotEmpty( message = "Campo 'instalacion' no puede estar vacio.")
    private String instalacion;
    @Column( nullable = false )
    @NotNull( message = " Campo 'pago' no debe estar vacio.")
    private int pago;
    @NotNull( message = "Campo 'fecha' no debe estar vacio.")
    @Column( nullable = false, unique = true )
    @Temporal(TemporalType.DATE )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    @Column( unique = true)
    private String comprobante;

    @PrePersist
    public void ModificarFecha() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, 1);
        fecha = calendar.getTime();
    }

    public Model() {}

    public Model(String instalacion, int pago, Date fecha, String comprobante) {
        this.instalacion = instalacion;
        this.pago = pago;
        this.fecha = fecha;
        this.comprobante = comprobante;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(String instalacion) {
        this.instalacion = instalacion;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
