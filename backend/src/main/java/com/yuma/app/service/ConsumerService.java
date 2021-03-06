package com.yuma.app.service;

import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yuma.app.document.Consumer;
import com.yuma.app.document.Role;
import com.yuma.app.exception.ResourceNotFoundException;
import com.yuma.app.payload.SignUpRequest;
import com.yuma.app.repository.RoleRepository;
import com.yuma.app.repository.UserRepository;
import com.yuma.app.to.ConsumerTO;

@Slf4j
@Service
public class ConsumerService {

	private UserRepository userRepository;
	private ConversionService conversionService;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public ConsumerService(UserRepository userRepository, ConversionService conversionService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.conversionService = conversionService;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Consumer saveUser(SignUpRequest req) {
		log.info("saving consumer {}", req.getEmail());
		Consumer consumer = conversionService.convert(req, Consumer.class);
		consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
		consumer.setActive(false);

		Optional<Role> userRole = roleRepository.findByRole("ADMIN");
		userRole.ifPresent(x -> consumer.setRoles(new HashSet<>(Collections.singletonList(x))));
		return userRepository.save(consumer);
	}

	public List<ConsumerTO> list() {
		log.info("fetching users list");
		List<Consumer> consumerList = userRepository.findAll();
		return convertUserListToUserTOList(consumerList);
	}

	public Set<String> listUserCompanies() {
		log.info("fetching users list");
		
		Set<String> companies = new HashSet<>();
		
		List<Consumer> consumerList = userRepository.findAll();
		for (Consumer consumer : consumerList) {
			
			if(consumer.getCompany() != null) {
				companies.add(consumer.getCompany());
			}
		}
		return companies;
	}

	public ConsumerTO findUserByEmail(String email) {
		log.info("fetching consumer by email: %s", email);

		Consumer consumer = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Consumer", "email", email));
		return conversionService.convert(consumer, ConsumerTO.class);
	}
	
	public List<ConsumerTO> findUsersByCompany(String company){
		log.info("fetching consumers from company: %s",company);

		List<Consumer> consumerList = userRepository.findByCompanyIgnoreCase(company);
		return convertUserListToUserTOList(consumerList);	
	}
	
	
	public void deleteUserByUserID(String uuid){
		log.info("deleting user by uuid: {}", uuid);

		userRepository.delete(uuid);
	}
	
	public ConsumerTO create(ConsumerTO consumerTO) {
		consumerTO.setUserId(UUID.randomUUID().toString());
		Consumer consumerToCreate = conversionService.convert(consumerTO, Consumer.class);
		Consumer consumer = userRepository.save(consumerToCreate);
		return conversionService.convert(consumer, ConsumerTO.class);
	}
	
	public ConsumerTO updateUser(ConsumerTO consumerTO){
		log.info("fetching consumer from DB to update");
		Consumer consumer = userRepository.findByUserId(consumerTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Consumer", "userId", consumerTO.getUserId()));
		Consumer consumerToUpdate = conversionService.convert(consumerTO, Consumer.class);
		consumer.updateFrom(consumerToUpdate);
		Consumer updatedConsumer = userRepository.save(consumer);
		return conversionService.convert(updatedConsumer, ConsumerTO.class);
		
	}
	
	protected boolean existsByEmail(String email){
		return userRepository.existsByEmail(email);
	}
	
	public boolean existsByCompany(String company){ return userRepository.existsByCompany(company);}
	
	public List<ConsumerTO> activeUsers(){
		log.info("fetching users list");

		List<Consumer> consumerList = userRepository.findByIsActiveIsTrue();
		return convertUserListToUserTOList(consumerList);
	}

	private List<ConsumerTO> convertUserListToUserTOList(List<Consumer> consumerList){
		List<ConsumerTO> consumerTOS = new ArrayList<>();

		for (Consumer consumer : consumerList) {
			consumerTOS.add(conversionService.convert(consumer, ConsumerTO.class));
		}

		return consumerTOS;
	}
	public void deleteAllUsers(){
		userRepository.deleteAll();
	}
}
