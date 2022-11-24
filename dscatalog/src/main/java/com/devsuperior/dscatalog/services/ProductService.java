package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundExceptions;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Transactional() // Annotation para garantir o ACID da transação com o banco de dados.
	public Page<ProductDTO> findAllProduct(PageRequest pageRequest){
		Page<Product> list = productRepository.findAll(pageRequest); // O repository não aceita DTO, somente a entidade, 
															// portanto é necessário
															// converter o Product em ProductDTO. 	
		return list.map(x -> new ProductDTO(x));
		
//		List<ProductDTO> listDto = new ArrayList<>();
//		for (Product cat : list) {
//			listDto.add(new ProductDTO(cat));
//		}
//		return listDto;
	}
	
	@Transactional()
	public ProductDTO findProductById(Long id) {
		Optional<Product> obj = productRepository.findById(id); 
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundExceptions("Resource not found."));
		return new ProductDTO(entity, entity.getCategories());
	}
	@Transactional()
	public ProductDTO insertProduct(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}
	@Transactional()
	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		try {
			Product item = productRepository.getReferenceById(id);
			//item.setName(dto.getName());
			return new ProductDTO(item);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundExceptions("Product not found." + id);
		}
	}

	public void deleteProduct(Long id) {
		try {
			productRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundExceptions("Product not found" + id);
		}catch(DataIntegrityViolationException e) {
			
		}
		
	}
}
