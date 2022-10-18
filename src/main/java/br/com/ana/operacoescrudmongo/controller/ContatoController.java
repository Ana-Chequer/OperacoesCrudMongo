package br.com.ana.operacoescrudmongo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ana.operacoescrudmongo.domain.Contato;
import br.com.ana.operacoescrudmongo.exception.ContatoInvalidoException;
import br.com.ana.operacoescrudmongo.exception.ContatoNaoEncontradoException;
import br.com.ana.operacoescrudmongo.repository.ContatoRepository;
import br.com.ana.operacoescrudmongo.service.ContatoService;
import br.com.ana.operacoescrudmongo.service.SequenceGeneratorService;


@RestController
@RequestMapping("v1/contacts")
public class ContatoController {
	
	private static Logger logger = LoggerFactory.getLogger(ContatoController.class);

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private ContatoService contatoService;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;


	@GetMapping
	public List<Contato> listarContatos() {
		logger.info("Passou por aqui na lista de contatos");
		return contatoRepository.findAll();
	}

	@GetMapping("/{contatoId}")
	public ResponseEntity<Contato> buscarContato(@PathVariable Long contatoId) {

		Optional<Contato> contato = contatoRepository.findById(contatoId);

		if (contato.isPresent()) {
			return ResponseEntity.ok(contato.get());

		}
		return ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<?> adicionarContato(@RequestBody Contato contato) {
		logger.info("Contato no adicionar " + contato.getEmailPrincipal());

		try {
			contato.setId(sequenceGeneratorService.generateSequence(Contato.SEQUENCE_NAME));
			contatoService.salvar(contato);
			return ResponseEntity.status(HttpStatus.CREATED).body(contato);

		} catch (ContatoInvalidoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{contatoId}")
	public ResponseEntity<Contato> atualizarContato(@PathVariable Long contatoId, @RequestBody Contato contato) {

		Optional<Contato> contatoCadastrado = contatoRepository.findById(contatoId);

		logger.info("contatoId " + contatoId);
		logger.info("nome proposto " + contato.getNome());
		logger.info("nome original " + contatoCadastrado.get().getNome());

		if (contatoCadastrado.isPresent()) {
			BeanUtils.copyProperties(contato, contatoCadastrado.get(), "id");
			logger.info("nome alterado " + contatoCadastrado.get().getNome());
			Contato contatoAtualizado = contatoService.atualizar(contatoCadastrado.get());
			return ResponseEntity.ok(contatoAtualizado);

		}
		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{contatoId}")
	public ResponseEntity<?> excluirContato(@PathVariable Long contatoId) {

		try {
			contatoService.excluir(contatoId);
			return ResponseEntity.noContent().build();

		} catch (ContatoNaoEncontradoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}

}
