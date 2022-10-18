package br.com.ana.operacoescrudmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import br.com.ana.operacoescrudmongo.domain.Contato;

@Component
public interface ContatoRepository extends MongoRepository<Contato, Long> {
	
}
	
	
	
