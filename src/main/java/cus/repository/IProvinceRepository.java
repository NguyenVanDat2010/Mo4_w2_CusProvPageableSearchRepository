package cus.repository;

import cus.model.Province;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IProvinceRepository extends PagingAndSortingRepository<Province,Long> {
}
