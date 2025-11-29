package ma.projet.Voiture;

import ma.projet.Voiture.entities.Client;
import ma.projet.Voiture.entities.Voiture;
import ma.projet.Voiture.repositories.VoitureRepository;
import ma.projet.Voiture.services.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class VoitureApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoitureApplication.class, args);
    }

    @Bean
    CommandLineRunner initialiserBaseH2(VoitureRepository voitureRepository, ClientService clientService){
        return args -> {
            Client c1 = clientService.clientById(2L);
            Client c2 = clientService.clientById(1L);
            System.out.println("**************************");
            System.out.println("Id est :" + c2.getId());
            System.out.println("Nom est :" + c2.getNom());
            System.out.println("**************************");
            System.out.println("**************************");
            System.out.println("Id est :" + c1.getId());
            System.out.println("Nom est :" + c1.getNom());
            System.out.println("Age est :" + c1.getAge());
            System.out.println("**************************");
            voitureRepository.save(new Voiture(null, "Ferrari", "C 26 7771", "488 GTB", 1L, c2));
            voitureRepository.save(new Voiture(null, "Lamborghini", "C 26 7770", "Huracan", 1L, c2));
            voitureRepository.save(new Voiture(null, "McLaren", "C 26 7777", "720S", 2L, c1));
        };
    }

}