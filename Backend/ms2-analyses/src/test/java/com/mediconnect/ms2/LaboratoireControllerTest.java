package com.mediconnect.ms2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class LaboratoireControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void crudComplet() throws Exception {
        String json = """
                {"nom":"Laboratoire Pasteur","adresse":"12 av. Bourguiba","ville":"Tunis","telephone":"71 123 456"}""";

        // CREATE -> 201 + Location
        mvc.perform(post("/api/labs").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/labs/1")))
                .andExpect(jsonPath("$.id").value(1));

        // READ
        mvc.perform(get("/api/labs/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Laboratoire Pasteur"));
        mvc.perform(get("/api/labs").param("ville", "tunis")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        mvc.perform(get("/api/labs").param("ville", "Sfax"))
                .andExpect(jsonPath("$.length()").value(0));

        // UPDATE
        mvc.perform(put("/api/labs/1").contentType(MediaType.APPLICATION_JSON)
                        .content(json.replace("Tunis", "Sousse")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ville").value("Sousse"));

        // 400
        mvc.perform(post("/api/labs").contentType(MediaType.APPLICATION_JSON).content("{\"nom\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erreurs.nom").exists());

        // DELETE -> 204, puis 404
        mvc.perform(delete("/api/labs/1")).andExpect(status().isNoContent());
        mvc.perform(get("/api/labs/1")).andExpect(status().isNotFound());

        // Swagger disponible
        mvc.perform(get("/v3/api-docs")).andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/labs']").exists());
    }
}
