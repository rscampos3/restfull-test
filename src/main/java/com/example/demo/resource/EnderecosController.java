package com.example.demo.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

import com.example.demo.EnderecoRepository;
import com.example.demo.model.Endereco;

@RestController
@RequestMapping("/enderecos")
public class EnderecosController {
	
	static final Logger LOGGER = LogManager.getLogger(EnderecosController.class.getName());

	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@GetMapping
	public ResponseEntity<List<Endereco>> getAll() {
		
		return new ResponseEntity<List<Endereco>>(new ArrayList<Endereco>(enderecoRepository.findAll()), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Endereco> findById(@PathVariable Long id){
		Endereco enderecoResponse = enderecoRepository.findById(id).get();
		if(enderecoResponse == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Endereco>(enderecoResponse, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Endereco> save(@RequestBody Endereco endereco){
		Endereco enderecoResponse = enderecoRepository.save(endereco);
		
		return new ResponseEntity<Endereco>(enderecoResponse, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Endereco> update(@RequestBody Endereco endereco) {
		return new ResponseEntity<Endereco>(enderecoRepository.save(endereco), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		
		try {
			enderecoRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
