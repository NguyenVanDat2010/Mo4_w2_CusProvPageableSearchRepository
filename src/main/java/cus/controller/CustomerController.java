package cus.controller;

import cus.model.Customer;
import cus.model.Province;
import cus.service.customer.ICustomerService;
import cus.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces() {
        return provinceService.findAll();
    }

    /**Hiển thị không phân trang*/
//    @GetMapping("/customers")
//    public ModelAndView showCustomerList(){
//        ModelAndView modelAndView = new ModelAndView("customer/listcus");
//        Iterable<Customer> customers = customerService.findAll();
//        modelAndView.addObject("customers",customers);
//        return modelAndView;
//    }

    /**Phân trang và tìm kiếm max 20 bản ghi*/
//    @GetMapping("/customers")
//    public ModelAndView listCustomers(@RequestParam("s") Optional<String> s, Pageable pageable){
//        Page<Customer> customers;
//        if(s.isPresent()){
//            customers = customerService.findAllByFirstNameContaining(s.get(), pageable);
//        } else {
//            customers = customerService.findAll(pageable);
//        }
//        ModelAndView modelAndView = new ModelAndView("/customer/list");
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }

    /**Phân trang và tìm kiếm max 3 bản ghi tùy chọn*/
    @GetMapping("/customers")
    public ModelAndView showCustomerList(@RequestParam("s") Optional<String> keyword, @RequestParam("page") Optional<Integer> page) {
        Page<Customer> customers;
        ModelAndView modelAndView = new ModelAndView("customer/listcus");
        int pageNumber = 0;
        if (page.isPresent() && page.get() > 1) {
            pageNumber = page.get() - 1;
        }
        PageRequest pageSplitter = new PageRequest(pageNumber, 3);
        if (keyword.isPresent()) {
            customers = customerService.findAllByFirstNameContaining(keyword.get(), pageSplitter);
            modelAndView.addObject("keyword", keyword.get());
        } else {
            customers = customerService.findAll(pageSplitter);
        }
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/customers/create")
    public ModelAndView showCustomerCreate() {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/customers/create")
    public ModelAndView createCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("success", "New customer created successfully!");
        return modelAndView;
    }

    @GetMapping("/customers/edit/{id}")
    public ModelAndView showCustomerEdit(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer != null) {
            modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer);

        } else {
            modelAndView = new ModelAndView("/error-404");
        }
        return modelAndView;
    }

    @PostMapping("/customers/edit")
    public ModelAndView editCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        redirectAttributes.addFlashAttribute("success", "Customer was updated successfully!");
        return modelAndView;
    }

    @GetMapping("/customers/delete/{id}")
    public ModelAndView deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.remove(id);
        redirectAttributes.addFlashAttribute("success", "Customer was deleted successfully!");
        return new ModelAndView("redirect:/customers");
    }
}
