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

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundExceptions;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional() // Annotation para garantir o ACID da transação com o banco de dados.
	public Page<CategoryDTO> findAllCategory(PageRequest pageRequest){
		Page<Category> list = categoryRepository.findAll(pageRequest); // O repository não aceita DTO, somente a entidade, 
															// portanto é necessário
															// converter o Category em CategoryDTO. 	
		return list.map(x -> new CategoryDTO(x));
		
//		List<CategoryDTO> listDto = new ArrayList<>();
//		for (Category cat : list) {
//			listDto.add(new CategoryDTO(cat));
//		}
//		return listDto;
	}
	
	@Transactional()
	public CategoryDTO findCategoryById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id); 
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundExceptions("Resource not found."));
		return new CategoryDTO(entity);
	}
	@Transactional()
	public CategoryDTO insertCategory(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}
	@Transactional()
	public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
		try {
			Category item = categoryRepository.getReferenceById(id);
			item.setName(dto.getName());
			return new CategoryDTO(item);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundExceptions("Category not found." + id);
		}
	}

	public void deleteCategory(Long id) {
		try {
			categoryRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundExceptions("Category not found" + id);
		}catch(DataIntegrityViolationException e) {
			
		}
		
	}
}
