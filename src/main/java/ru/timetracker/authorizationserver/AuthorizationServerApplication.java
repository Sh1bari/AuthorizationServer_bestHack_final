package ru.timetracker.authorizationserver;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	//@PostConstruct
	public void generateAndSaveKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			// Сохранение закрытого ключа в файл
			PrivateKey privateKey = keyPair.getPrivate();
			FileOutputStream privateKeyFile = new FileOutputStream("private_key.pem");
			privateKeyFile.write(Base64.getEncoder().encode(privateKey.getEncoded()));
			privateKeyFile.close();

			// Сохранение открытого ключа в файл
			PublicKey publicKey = keyPair.getPublic();
			FileOutputStream publicKeyFile = new FileOutputStream("public_key.pem");
			publicKeyFile.write(Base64.getEncoder().encode(publicKey.getEncoded()));
			publicKeyFile.close();

			System.out.println("Сгенерирована и сохранена пара ключей в файлах private_key.pem и public_key.pem");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
