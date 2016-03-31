package de.btc.microservice.addressservice.ui;

import de.btc.microservice.addressservice.core.AddressService;
import de.btc.microservice.addressservice.model.Address;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class AddressModel {

    @Inject
    AddressService addressService;

    public List<Address> getAllAddress() {
        return addressService.loadAllAddress();
    }

}
