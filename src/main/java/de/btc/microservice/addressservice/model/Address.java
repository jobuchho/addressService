package de.btc.microservice.addressservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
@NamedQueries({
        @NamedQuery(name = Address.ADDRESS_FIND_ALL, query = "SELECT a FROM Address a"),
        @NamedQuery(name = Address.ADDRESS_FIND_BY_QUERY_PARAM,
                query = "SELECT a " +
                        "  FROM Address a " +
                        " where (:addressId is null or a.addressId = :addressId)" +
                        "   and (:streetName is null or a.streetName = :streetName)" +
                        "   and (:houseNumber is null or a.houseNumber = :houseNumber)" +
                        "   and (:zip is null or a.zip = :zip)" +
                        "   and (:city is null or a.city = :city)")
})
@XmlRootElement
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ADDRESS_FIND_ALL = "Address.findAll";
    public static final String ADDRESS_FIND_BY_QUERY_PARAM = "Address.findByQueryParam";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long addressId;

    @Column(length = 40)
    private String streetName;

    @Column(length = 10)
    private String houseNumber;

    @Column(length = 20)
    private String city;

    @Column(length = 10)
    private String zip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
