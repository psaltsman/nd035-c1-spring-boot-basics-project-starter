package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final Logger logger = LoggerFactory.getLogger(CredentialService.class);

    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {

        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAllCredentials(User user) {

        return credentialMapper.getAllCredentialsByUser(user);
    }

    public void insertCredential(Credential credential) {

        //Encrypt the password and save the key with the credential record for future use
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        credentialMapper.insertCredential(credential);
    }
    public void updateCredential(Credential credential) {

        String key = credentialMapper.getKeyById(credential.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);

        credential.setKey(key);
        credential.setPassword(encryptedPassword);

        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Credential credential) {

        credentialMapper.deleteCredential(credential);
    }

    public Credential getCredentialById(Integer credentialId) {

        Credential credential = credentialMapper.getCredentialById(credentialId);

        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());

        credential.setPassword(decryptedPassword);
        credential.setKey("");  //Do not return the key to the UI

        return credential;
    }
}
