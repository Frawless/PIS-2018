package cz.vut.fit.pis.bakery.bakery.repository;

import cz.vut.fit.pis.bakery.bakery.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Address findAddressByCityAndPscAndStreetNameAndStreetNumber(String city, String psc, String streetName, int streetNumber);
}
