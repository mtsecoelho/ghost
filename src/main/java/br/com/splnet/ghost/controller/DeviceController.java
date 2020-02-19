package br.com.splnet.ghost.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.splnet.ghost.model.CommandModel;
import br.com.splnet.ghost.model.Device;
import br.com.splnet.ghost.model.Filter;
import br.com.splnet.ghost.model.ResponseModel;
import br.com.splnet.ghost.repository.DeviceRepository;

@RestController
@RequestMapping("/api/device")
public class DeviceController {
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@RequestMapping(value = "listDevices", method = RequestMethod.POST)
	public ResponseModel listDevices(@RequestBody @Validated Filter filter, BindingResult result) {
		if (result.hasErrors()) return new ResponseModel(HttpStatus.BAD_REQUEST.value(), result.getAllErrors(), null);
		
		Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), filter.getDirection(), filter.getProperty());
		
		return new ResponseModel(HttpStatus.OK.value(), "Lista de Dispositivos", deviceRepository.findAll(pageable));
	}

	@RequestMapping(value = "saveDevice", method = RequestMethod.POST)
	public ResponseModel saveDevice(@RequestBody @Validated Device device, BindingResult result) {
		if (result.hasErrors()) return new ResponseModel(HttpStatus.BAD_REQUEST.value(), result.getAllErrors(), null);
		
		Device oldDevice = deviceRepository.findByIp(device.getIp());
		
		if (oldDevice != null && (device.getDeviceId() == null || oldDevice.getDeviceId() != device.getDeviceId())) {			
			return new ResponseModel(HttpStatus.BAD_REQUEST.value(), "Ip já cadastrado para outro dispositivo", null);
		}
		
		return new ResponseModel(HttpStatus.OK.value(), "Dispositivo armazenado", deviceRepository.save(device));
	}
	
	@RequestMapping(value = "deleteDevice", method = RequestMethod.POST)
	public ResponseModel deleteDevice(@RequestBody Device device) {
		deviceRepository.delete(device);
		
		return new ResponseModel(HttpStatus.OK.value(), "Dispositivo removido", null);
	}
	
	@RequestMapping(value = "sendMessage", method = RequestMethod.POST)
	public ResponseModel sendMessage(@RequestBody @Validated CommandModel commandModel, BindingResult result) {
		if (result.hasErrors()) return new ResponseModel(HttpStatus.BAD_REQUEST.value(), result.getAllErrors(), null);
		
		Socket socket = null;
		Device device = null;
		OutputStream os = null;
		BufferedWriter bf = null;
		
		Optional<Device> optionalDevice = deviceRepository.findById(commandModel.getDeviceId());
		
		if (optionalDevice.isPresent()) device = optionalDevice.get();
		
		if (device == null) return new ResponseModel(HttpStatus.BAD_REQUEST.value(), "Dispositivo não localizado", null);
		
		try {
			socket = new Socket(device.getIp(), device.getPort());
			os = socket.getOutputStream();
			
			bf = new BufferedWriter(new OutputStreamWriter(os));
			
			bf.write("GET /"+ commandModel.getMessage());
			bf.newLine();
			bf.write("Accept: */*");
			bf.newLine();
		} catch (IOException e) {
			return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao enviar mensagem: " + e.getMessage(), null);
		} 
		finally {
			if (socket != null)
				try {
					bf.close();
					os.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return new ResponseModel(HttpStatus.OK.value(), "Mensagem Enviada", null);
	}
}
