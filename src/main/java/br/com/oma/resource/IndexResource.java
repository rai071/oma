package br.com.oma.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.oma.primary.domain.UsuarioPrimary;
import br.com.oma.primary.repository.UsuarioPrimaryRepository;
import br.com.oma.secondary.domain.UsuarioSecondary;
import br.com.oma.secondary.repository.UsuarioSecondaryRepository;

@Controller
@RequestMapping("/login")
public class IndexResource {
	
	@Autowired
	UsuarioSecondaryRepository secondaryRepo;
	
	@Autowired
	UsuarioPrimaryRepository primaryRepo;

	@RequestMapping(value="/secondary", method= RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioSecondary objDTO) {
		UsuarioSecondary obj = secondaryRepo.save(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/secondary", method = RequestMethod.GET)
	private ResponseEntity<List<UsuarioSecondary>> findAll() {
		List<UsuarioSecondary> list = secondaryRepo.findAll();
		return  ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/secondary/{id}", method= RequestMethod.GET)
	public ResponseEntity<List<UsuarioSecondary>> findOne(@PathVariable Integer id) {
		List<UsuarioSecondary> obj = secondaryRepo.busca(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/primary", method= RequestMethod.POST)
	public ResponseEntity<Void> insertSecondary(@Valid @RequestBody UsuarioPrimary objDTO) {
		UsuarioPrimary obj = primaryRepo.save(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/primary", method = RequestMethod.GET)
	private ResponseEntity<List<UsuarioPrimary>> findAllsecondary() {
		List<UsuarioPrimary> list = primaryRepo.findAll();
		return  ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/primary/{id}", method= RequestMethod.GET)
	public ResponseEntity<Optional<UsuarioPrimary>> findOneSecondary(@PathVariable Integer id) {
		Optional<UsuarioPrimary> obj = primaryRepo.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
