package cus.service.customer;

import cus.model.Customer;
import cus.model.Province;
import cus.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IGeneralService<Customer> {
    Iterable<Customer> findAllByProvince(Province province);

    Page<Customer> findAllByFirstNameContaining(String firstName, Pageable pageable);

    Page<Customer> findAll(Pageable pageable);
}
