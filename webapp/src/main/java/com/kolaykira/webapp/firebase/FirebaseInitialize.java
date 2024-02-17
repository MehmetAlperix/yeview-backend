package com.kolaykira.webapp.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;



@Service
public class FirebaseInitialize {

    @PostConstruct
    public void initialize() {
        String json = "{" +
                "\"type\": \"service_account\"," +
                "\"project_id\": \"yeview-b01f7\"," +
                "\"private_key_id\": \"c674a719d68db321b7458c6db4ec8b02df4b5282\"," +
                "\"private_key\": \"-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQClHFD1KeWeM5eg\n" +
                "fIAHms4j54I+jY+rYvVjkrlzIeEzf9cDHboUZUIHtJg1O6DJLCx2Q+OiaETUplDM\n" +
                "MiRrkq9BnLYRKZhN04ej3IW4PncwpwHezlWi7J5IZVQRCeSXIHKjmAK5PXpkSVg9\n" +
                "k4V/dS2Miv1/+6o2miDmksOrZ+DBsosQ7EQ8rKaoAwM+2j/ijZrSpj/ayMXl9JWy\n" +
                "yRPMp4gVwRGpxtElrldPGf9f5lNufQOqzoDthtlQ+QfiT1k2fObqGMnvdBUQWNon\n" +
                "cFA8TpYE4txgRXDz6Pwyh3lEfbJnTqPnEHJsKFqrYKMPBqm3YSlCAzAMdZjglkcO\n" +
                "YfPS5RexAgMBAAECggEAAoDsKleFC5HRNAYHQ/ZBN3Koey3W1py0x1p/dOM34LnI\n" +
                "xxuRiYMije7bWv1JOhuxHeb4IeuOVaIi+gGtozLxdN99NEPq8w0o0p+CqJFL6eEy\n" +
                "alA7JLINUWA19t8tR+xeSKRCsE/iFfi1vZNJ1C2w799gqirlARhztMNXc7cqxY0b\n" +
                "CG6ARSUWf0qpkYslgzFawc8+jar93noHvG5pxYSzd6UGBagdBOCD1Gqu65oM2e9y\n" +
                "/sO0lYecZHqm+81MqB/FU2QVJpchClR1q3M0sMKwYvbJoViHEumhAs+Sjju0pQ1r\n" +
                "IdMbibgbvziNU1UUoYTrdDNBavYzvN3z5d+XE9g7sQKBgQDXIRlRO6ID0USzG2i3\n" +
                "I/NhiEw+FcgoP/GxTkGhLJiHljW5wLnHsHmYZq+XXqhV6HCUFup2iOY5NyHgPUTQ\n" +
                "cnduDF1jVXQmUAxSvkCFhBLEkdM4P4RIFZOsqSdPfBg+kFh/qPriPuGp3303XoZT\n" +
                "fPLVx+H8eXfCGfvOw81GyZnFSQKBgQDEeonM55MiABz007jgsgOI6gFLqW0wXZWc\n" +
                "VhCCKfOR9Xe42qd+SVbTZ8zrcPz0tEbNa+jYa6lW+xvhx53l2NeQ6TgjQaG+Esxu\n" +
                "tB5ucIUy+tdUPAVA+G2srZzdYxaXQk3f6ciztRA2/aiscZqv2dBrBHoL6SGYapZo\n" +
                "nzVv72OHKQKBgQCzhEs8Hib0SIyraRPYv4c4vCPjKwn7jGCDNGpPtIcTnaT3fHuK\n" +
                "xxiws/+SsMqH5Y8MBJNilpqG5BrApF/oEZzXmDnVyllCH0opQdlVkQCYR5eaOGaK\n" +
                "QHcafYzwJv2UN5+h58BzS0XEG1rB+X7d7EY3XrSVmkb1EfmucHW7vD81AQKBgCEA\n" +
                "dCT2wL/1ROfaqDMVs4JLy+vpiW2WSor36c3zPfZGRNXH5FpR90OLK+BgGh0AKuRW\n" +
                "O8YeT1NM2mISJXN1+L9ebQ9+9qv2fGAb4QCr5bOqboC3lCpVkBZxEugcvK6rdGkn\n" +
                "245T22VhNfPBITpoW5meJuHZRstTG/B2zYL/srgBAoGAdbL7DETD4G6NJFfvgt/l\n" +
                "pUm6P6MZ0eYvn6eDFZuUYKX2F1HgmMgZXJ/wD4VOXSV/Hs6ALNZIXDR9ZYIqeyrA\n7ohKr1HHi3t2TYDqM2Dq5uf7wt/xBwOSCuJs7ywZ97f0/puzBWvsfs+ijmZpFVsS\\n" +
                "CQrX0VVCKm8nvDHSjHizOYU=\\n-----END PRIVATE KEY-----\\n\"," +
                "\"client_email\": \"firebase-adminsdk-n870v@yeview-b01f7.iam.gserviceaccount.com\"," +
                "\"client_id\": \"108022680920245459248\"," +
                "\"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\"," +
                "\"token_uri\": \"https://oauth2.googleapis.com/token\"," +
                "\"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\"," +
                "\"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-n870v%40yeview-b01f7.iam.gserviceaccount.com\"," +
                "\"universe_domain\": \"googleapis.com\"" +
                "}";

        JsonObject jsonObject;
        // Parse the JSON string into a JsonObject
        jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();

        byte[] jsonBytes = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
        InputStream inputStream = new ByteArrayInputStream(jsonBytes);
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

            /*
    {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("./serviceAccountKey.json");

            FirebaseOptions options = null;
            try {
                options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FirebaseApp.initializeApp(options);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
 }
    */



