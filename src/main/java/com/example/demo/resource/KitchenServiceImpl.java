package com.example.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KitchenServiceImpl {

	@Autowired
	KitchenRepo kRepo;
	
	public Kitchen findKitchen(Long id) {
		return kRepo.findById(id).get();
	}
	
	public void saveKitchen(Kitchen k) {
		 kRepo.save(k);
	}
	
	public void deleteKitchen(Long id) {
		kRepo.deleteById(id);
	}
}
