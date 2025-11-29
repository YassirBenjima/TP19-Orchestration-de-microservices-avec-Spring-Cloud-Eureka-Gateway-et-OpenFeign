package ma.projet.Voiture.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ma.projet.Voiture.entities.Client;

@FeignClient(name="SERVICE-CLIENT")
public interface ClientService{
    @GetMapping(path="/clients/{id}")
    Client clientById(@PathVariable Long id);
}

