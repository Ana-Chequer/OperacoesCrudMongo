package br.com.ana.operacoescrudmongo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.ana.operacoescrudmongo.domain.Contato;
import br.com.ana.operacoescrudmongo.exception.ContatoInvalidoException;
import br.com.ana.operacoescrudmongo.exception.ContatoNaoEncontradoException;
import br.com.ana.operacoescrudmongo.repository.ContatoRepository;

@Service
public class ContatoService {

	private static Logger logger = LoggerFactory.getLogger(ContatoService.class);

	@Autowired
	private ContatoRepository contatoRepository;

	public Contato salvar(Contato contato) throws ContatoInvalidoException {

		if (!contatoValido(contato)) {
			logger.info("Contato inválido.");
			throw new ContatoInvalidoException("Dados do contato inválidos! Fornecer nome e e-mail, por favor!");

		}
		logger.info("Contato salvo");
		return contatoRepository.save(contato);
	}

	private boolean contatoValido(Contato contato) {
		boolean resultado = true;
		if ((contato.getNome().equals("")) || (contato.getEmailPrincipal().equals(""))
				|| (contato.getEmailPrincipal().equals(null)) || contato.getNome().equals(null)) {
			resultado = false;
		}
		return resultado;
	}

	public Contato atualizar(Contato contato) {
		return contatoRepository.save(contato);
	}

	public void excluir(Long contatoId) {

		try {
			logger.info("O id do contato é: " + contatoId);
			contatoRepository.deleteById(contatoId);

		} catch (EmptyResultDataAccessException e) {
			throw new ContatoNaoEncontradoException(String.format("Contato de código %d não encontrado.", contatoId));
		}
	}

}
