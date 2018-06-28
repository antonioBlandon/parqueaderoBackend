package co.com.ceiba.parqueadero.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parqueadero.model.Contact;

@RestController
public class ContactsApi {
     
    @RequestMapping(value="/product", method=RequestMethod.GET)
    public Contact getById(){
        return new Contact(1L, "John", "Doe", "+57 311 222 3344", "john@sinbugs.com");
    }
}
