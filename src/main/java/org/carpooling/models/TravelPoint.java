package org.carpooling.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "points", schema = "rose-valley-travel")
public class TravelPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private int id;
    @Column(name = "address")
    private String address;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;

    public TravelPoint() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelPoint travelPoint)) return false;
        return id == travelPoint.id
                && Objects.equals(address, travelPoint.address)
                && Objects.equals(latitude, travelPoint.latitude)
                && Objects.equals(longitude, travelPoint.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, latitude, longitude);
    }
}
