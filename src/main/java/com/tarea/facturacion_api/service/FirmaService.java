package com.tarea.facturacion_api.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;

@Service
public class FirmaService {

    private static final String RUTA_FIRMA = "firma.p12";
    private static final String CLAVE_FIRMA = "Titocool12"; // Tu clave real

    private KeyStore keyStore;
    private String alias;

    public FirmaService() {
        cargarKeyStore();
    }

    private void cargarKeyStore() {
        try {
            this.keyStore = KeyStore.getInstance("PKCS12");
            InputStream firmaStream = new ClassPathResource(RUTA_FIRMA).getInputStream();
            this.keyStore.load(firmaStream, CLAVE_FIRMA.toCharArray());

            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                this.alias = aliases.nextElement();
                if (keyStore.isKeyEntry(this.alias)) break;
            }
        } catch (Exception e) {
            System.err.println("Error cargando firma: " + e.getMessage());
        }
    }

    // --- MÉTODOS AVANZADOS (Fase 2) ---
    public X509Certificate getCertificado() throws Exception {
        if (keyStore == null) cargarKeyStore();
        return (X509Certificate) keyStore.getCertificate(alias);
    }

    public PrivateKey getPrivateKey() throws Exception {
        if (keyStore == null) cargarKeyStore();
        return (PrivateKey) keyStore.getKey(alias, CLAVE_FIRMA.toCharArray());
    }

    public String firmar(byte[] datos) throws Exception {
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(getPrivateKey());
        signature.update(datos);
        return Base64.getEncoder().encodeToString(signature.sign());
    }
    
    public String digest(byte[] datos) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(datos);
        return Base64.getEncoder().encodeToString(digest);
    }

    // --- MÉTODO PUENTE (Fase 3 - EL QUE FALTABA) ---
    // Este método permite firmar un String directamente, solucionando el error de compilación.
    public String firmarDatos(String datos) {
        try {
            return firmar(datos.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR_FIRMA";
        }
    }
}