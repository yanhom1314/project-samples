package com.greatbit.xgn.console.web;

import com.greatbit.xgn.console.domain.Customer;
import com.greatbit.xgn.console.domain.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer greetings(@PathVariable("id") Long id) {
        System.out.println("1");
        if (repository.exists(id)) {
            return repository.findOne(id);
        } else return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Customer> list() {
        System.out.println("2");
        final List<Customer> list = new ArrayList<Customer>();
        Iterator<Customer> iterable = repository.findAll().iterator();

        while (iterable.hasNext()) {
            list.add(iterable.next());
        }
        return list;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Customer customer) {
        System.out.println("3");
        repository.save(customer);
        System.out.println(repository.count());
        System.out.println(repository.findByLastName("Bilbo"));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody Customer customer) {
        System.out.println("4");
        repository.save(customer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateById(@PathVariable("id") Long id, @RequestBody Customer customer) {
        System.out.println("5");
        if (repository.exists(id)) {
            Customer ct = repository.findOne(id);
            ct.setFirstName(customer.getFirstName());
            ct.setLastName(customer.getLastName());
            repository.save(ct);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        System.out.println("6");
        repository.delete(id);
    }
}
