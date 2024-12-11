package com.smartcityapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.smartcityapp.model.EmergencyService;

public interface EmergencyServiceRepository extends CrudRepository<EmergencyService, Long> {
}
