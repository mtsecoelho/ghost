Instalar maven
Instalar java8
Entar no diretorio do projeto
Executar o comando: mvn clean package
Exevutar o comando mvn spring-boot:run

Comandos da api:
Listar dispositivos....: curl -X POST -H "Content-Type: application/json" -d '{"page": 0, "size": 10, "direction": "ASC", "property": "ip"}' http://localhost:8080/api/device/listDevices
Salvar novo dispositivo: curl -X POST -H "Content-Type: application/json" -d '{"ip": "127.0.0.1","port": "5070"}' http://localhost:8080/api/device/saveDevice
Atualizar dispositivo..: curl -X POST -H "Content-Type: application/json" -d '{"deviceId": 1, "ip": "127.0.0.1","port": "5070"}' http://localhost:8080/api/device/saveDevice
Enviar Mensagem........: curl -X POST -H "Content-Type: application/json" -d '{"deviceId": 1, "message": "on"}' http://localhost:8080/api/device/sendMessage