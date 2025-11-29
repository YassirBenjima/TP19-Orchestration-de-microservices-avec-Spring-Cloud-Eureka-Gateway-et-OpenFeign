package ma.projet.Voiture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.projet.Voiture.entities.Voiture;
import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    @Query("SELECT v FROM Voiture v WHERE v.id_client = :id_client")
    List<Voiture> findByIdClient(@Param("id_client") Long id_client);
}

