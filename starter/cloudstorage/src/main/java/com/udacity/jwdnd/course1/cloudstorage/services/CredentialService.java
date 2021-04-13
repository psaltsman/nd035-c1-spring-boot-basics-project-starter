package com.udacity.jwdnd.course1.cloudstorage.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    private Logger logger = LoggerFactory.getLogger(CredentialService.class);

    private EncryptionService encryptionService;

    public CredentialService(EncryptionService encryptionService) {

        this.encryptionService = encryptionService;
    }
}
