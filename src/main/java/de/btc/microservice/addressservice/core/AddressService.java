package de.btc.microservice.addressservice.core;

import de.btc.microservice.addressservice.model.Address;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AddressService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<Address> loadAllAddress() {
        return em.createNamedQuery(Address.ADDRESS_FIND_ALL, Address.class).getResultList();
    }

    @Transactional
    public Address findAddressById(Long id) {
        return em.find(Address.class, id);
    }


    @Transactional
    public List<Address> findAddressByQueryParam(Long addressId, String streetName, String houseNumber, String zip, String city) {
        return em.createNamedQuery(Address.ADDRESS_FIND_BY_QUERY_PARAM, Address.class)
                .setParameter("addressId", addressId)
                .setParameter("streetName", streetName)
                .setParameter("houseNumber", houseNumber)
                .setParameter("zip", zip)
                .setParameter("city", city)
                .getResultList();
    }

    @Transactional
    public Address updateAddress(Long id, Long addressId, String streetName, String houseNumber, String zip, String city) {
        Address persistedAddress = em.find(Address.class, id);
        if (addressId != null) {
            persistedAddress.setAddressId(addressId);
        }
        if (streetName != null) {
            persistedAddress.setStreetName(streetName);
        }
        if (houseNumber != null) {
            persistedAddress.setHouseNumber(houseNumber);
        }
        if (zip != null) {
            persistedAddress.setZip(zip);
        }
        if (city != null) {
            persistedAddress.setCity(city);
        }
        return persistedAddress;
    }

    @Transactional
    public Address addAddress(Address address) {
        em.persist(address);
        return address;
    }

    @Transactional
    public void deleteAddress(Long id) {
        Address address = em.find(Address.class, id);
        if (address != null) {
            em.remove(address);
        }
    }
}
