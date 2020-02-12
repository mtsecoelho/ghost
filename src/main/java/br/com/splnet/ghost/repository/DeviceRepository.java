package br.com.splnet.ghost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.splnet.ghost.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

	Device findByIp(String ip);
	
}
