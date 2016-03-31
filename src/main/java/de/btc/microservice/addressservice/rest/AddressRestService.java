package de.btc.microservice.addressservice.rest;

import de.btc.microservice.addressservice.core.AddressService;
import de.btc.microservice.addressservice.model.Address;
import de.btc.microservice.addressservice.rest.dto.AddressDto;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

@Path("/address")
public class AddressRestService {

    @Inject
    AddressService addressService;

    @GET
    @Path("/")
    @Produces("application/json")
    public List<AddressDto> getAllAddress() {
        return addressService.loadAllAddress().stream().map(address -> mapAddressToDto(address)).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public AddressDto findAddressById(@PathParam("id") Long id) {
        return mapAddressToDto(addressService.findAddressById(id));
    }


    @GET
    @Path("/query")
    @Produces("application/json")
    public List<AddressDto> findAddressByQueryParam(@QueryParam("addressId") Long addressId,
                                                    @QueryParam("streetName") String streetName,
                                                    @QueryParam("houseNumber") String houseNumber,
                                                    @QueryParam("zip") String zip,
                                                    @QueryParam("city") String city) {
        return addressService.findAddressByQueryParam(addressId, streetName, houseNumber, zip, city).stream().map(address -> mapAddressToDto(address)).collect(Collectors.toList());
    }


    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public AddressDto updateAddress(@PathParam("id") Long id,
                                    @QueryParam("addressId") Long addressId,
                                    @QueryParam("streetName") String streetName,
                                    @QueryParam("houseNumber") String houseNumber,
                                    @QueryParam("zip") String zip,
                                    @QueryParam("city") String city) {
        return mapAddressToDto(addressService.updateAddress(id, addressId, streetName, houseNumber, zip, city));
    }


    @POST
    @Path("/")
    @Produces("application/json")
    public AddressDto addAddress(AddressDto address) {
        return mapAddressToDto(addressService.addAddress(mapDtoToAddress(address)));
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public void deleteAddress(@PathParam("id") Long id) {
        addressService.deleteAddress(id);
    }


    private AddressDto mapAddressToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setAddressId(address.getAddressId());
        addressDto.setCity(address.getCity());
        addressDto.setHouseNumber(address.getHouseNumber());
        addressDto.setStreetName(address.getStreetName());
        addressDto.setZip(address.getZip());
        return addressDto;
    }

    private Address mapDtoToAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setId(addressDto.getId());
        address.setAddressId(addressDto.getAddressId());
        address.setCity(addressDto.getCity());
        address.setHouseNumber(addressDto.getHouseNumber());
        address.setStreetName(addressDto.getStreetName());
        address.setZip(addressDto.getZip());
        return address;
    }
}
