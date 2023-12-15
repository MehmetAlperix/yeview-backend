package com.kolaykira.webapp.Config.OpenAPIConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/*
This is the class for swagger OpenAPI customization
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name ="Mehmet Alper Çetin",
                        email = "alper.cetin@ug.bilkent.edu.tr"
                ),
                description = "Open API for YEVIEW Backend",
                title = "YEVIEW",
                version = "v1"
        ),

        security = @SecurityRequirement(
        name = "bearerAuth"
    ),
        servers = @Server(url = "/", description = "Default Server URL")
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT token, use KolayKira 12345 for login and copy the given token here for authorization",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenAPIConfig {
}
