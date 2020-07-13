package cus.controller;

import cus.model.Customer;
import cus.model.Province;
import cus.service.customer.ICustomerService;
import cus.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/provinces")
public class ProvinceController {
    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ModelAndView showProvinces(){
        ModelAndView modelAndView = new ModelAndView("province/list");
        modelAndView.addObject("provinces",provinceService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showProvinceCreate(){
        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province",new Province());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province",new Province());
        modelAndView.addObject("success","New province created successfully!");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showProvinceEdit(@PathVariable Long id){
        Province province = provinceService.findById(id);
        ModelAndView modelAndView;
        if(province != null) {
            modelAndView = new ModelAndView("/province/edit");
            modelAndView.addObject("province", province);

        }else {
            modelAndView = new ModelAndView("/error-404");
        }
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editProvinces(@ModelAttribute("province") Province province, RedirectAttributes redirectAttributes){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("redirect:/provinces");
        redirectAttributes.addFlashAttribute("success","Province was updated successfully!");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProvinces(@PathVariable Long id,RedirectAttributes redirectAttributes){
        provinceService.remove(id);
        redirectAttributes.addFlashAttribute("success","Province was deleted successfully!");
        return new ModelAndView("redirect:/provinces");
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewProvinces(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("province/view");
        Province province = provinceService.findById(id);
        if(province == null){
            return new ModelAndView("/error-404");
        }
        Iterable<Customer> customers = customerService.findAllByProvince(province);
        modelAndView.addObject("province", province);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}
