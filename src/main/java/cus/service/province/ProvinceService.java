package cus.service.province;

import cus.model.Province;
import cus.repository.IProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProvinceService implements IProvinceService{
    @Autowired
    private IProvinceRepository provinceRepository;

    @Override
    public Iterable<Province> findAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Province findById(Long id) {
        return provinceRepository.findOne(id);
    }

    @Override
    public void save(Province model) {
        provinceRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        provinceRepository.delete(id);
    }
}
