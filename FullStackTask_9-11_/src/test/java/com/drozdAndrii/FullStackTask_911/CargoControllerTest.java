package com.drozdAndrii.FullStackTask_911;

import com.drozdAndrii.FullStackTask_911.controllers.CargoController;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
@Sql(value = {"create-vessel-before.sql","create-cargo-before.sql",})
public class CargoControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CargoController controller;


    @Test
    public void testController() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getAllCargoTest()throws Exception {
        this.mockMvc.perform(get("/cargo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( content().json("[{\"id\":1,\"cargoName\":\"BMW x5\",\"sendNumber\":123456,\"trackerDTO\":{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010}},{\"id\":2,\"cargoName\":\"Tesla Model 3\",\"sendNumber\":143446,\"trackerDTO\":{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010}},{\"id\":3,\"cargoName\":\"Maybach S 560\",\"sendNumber\":222456,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}},{\"id\":4,\"cargoName\":\"Porsche Cayman S 987\",\"sendNumber\":223426,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}},{\"id\":5,\"cargoName\":\"Mercedes-Benz GLC\",\"sendNumber\":324456,\"trackerDTO\":{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011}},{\"id\":6,\"cargoName\":\"Lamborghini Urus\",\"sendNumber\":329426,\"trackerDTO\":{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011}}]"));
    }

    @Test
    public void getCargoTest()throws Exception {
        this.mockMvc.perform(get("/cargo/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( content().json("{\"id\":4,\"cargoName\":\"Porsche Cayman S 987\",\"sendNumber\":223426,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}}"));
    }

    @Test
    public void getCargoByCargoNameAndSendNumberTest()throws Exception {
        this.mockMvc.perform(get("/cargo/BMW x5/sendNumber/123456/get"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( content().json("{\"id\":1,\"cargoName\":\"BMW x5\",\"sendNumber\":123456,\"trackerDTO\":{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010}}"));
    }

    @Test
    public void createCargoTest() throws Exception{
        JSONObject requestParams = new JSONObject();
        requestParams.put("cargoName", "Autobus");
        requestParams.put("sendNumber", "453323");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/cargo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(requestParams));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( content().json("{\"id\":7,\"cargoName\":\"Autobus\",\"sendNumber\":453323,\"trackerDTO\":null}"));

    }

    @Test
    public void deleteCargoTest() throws Exception{
        this.mockMvc.perform(delete("/cargo/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( content().json("{\"id\":5,\"cargoName\":\"Mercedes-Benz GLC\",\"sendNumber\":324456,\"trackerDTO\":{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011}}"));
    }

    @Test
    public void editCargoTest() throws Exception{
        JSONObject cargo = new JSONObject();
        cargo.put("cargoName", "Deo Matis");
        cargo.put("sendNumber", "45323");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.put("/cargo/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(cargo));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( content().json("{\"id\":3,\"cargoName\":\"Deo Matis\",\"sendNumber\":45323,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}}"));

    }

//    @Test
//    public void cargoCreateEmptyCargoNameTest() throws Exception{
//        JSONObject cargo = new JSONObject();
//        cargo.put("cargoName", "");
//        cargo.put("sendNumber", "45323");
//
//        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/cargo")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(String.valueOf(cargo));
//
//        this.mockMvc.perform(mockRequest)
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect( content().json("{\"message\":\"cargoName-Cargo name should be between 2 and 100 characters;cargoName-Cargo name should not be empty;\"}"));
//    }

    @Test
    public void cargoCreateCargoNameLessTwoCharacterTest()throws Exception{
        JSONObject cargo = new JSONObject();
        cargo.put("cargoName", "A");
        cargo.put("sendNumber", "45323");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/cargo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(cargo));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect( content().json("{\"message\":\"cargoName-Cargo name should be between 2 and 100 characters;\"}"));
    }

    @Test
    public void cargoCreateCargoSendNumberMinusTest()throws Exception{
        JSONObject cargo = new JSONObject();
        cargo.put("cargoName", "Bus");
        cargo.put("sendNumber", "-45323");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/cargo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(cargo));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect( content().json("{\"message\":\"sendNumber-Send number should be uniq and greater than 0;\"}"));
    }

    @Test
    public void cargoCreateCargoSendNumberGreaterThanMaxTest()throws Exception{
        JSONObject cargo = new JSONObject();
        cargo.put("cargoName", "Bus");
        cargo.put("sendNumber", "2147483646");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/cargo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(cargo));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect( content().json("{\"message\":\"sendNumber-Send number should be uniq and less than 2147483645;\"}"));
    }

    @Test
    public void cargoCreateCargoSendNumberAlreadyExistTest()throws Exception{
        JSONObject cargo = new JSONObject();
        cargo.put("cargoName", "Bus");
        cargo.put("sendNumber", "324456");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/cargo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(cargo));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect( content().json("{\"message\":\"Cargo with Send Number: 324456 already exist\"}"));
    }

    @Test
    public void editCreateCargoSendNumberGreaterThanMaxTest()throws Exception{
        JSONObject cargo = new JSONObject();
        cargo.put("cargoName", "Bus");
        cargo.put("sendNumber", "2147483646");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.put("/cargo/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(cargo));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect( content().json("{\"message\":\"sendNumber-Send number should be uniq and less than 2147483645;\"}"));
    }

    @Test
    public void editCreateCargoNameLessTwoCharacterTest()throws Exception{
        JSONObject cargo = new JSONObject();
        cargo.put("cargoName", "A");
        cargo.put("sendNumber", "45323");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.put("/cargo/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(cargo));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect( content().json("{\"message\":\"cargoName-Cargo name should be between 2 and 100 characters;\"}"));
    }

    @Test
    public void editCreateCargoSendNumberMinusTest()throws Exception{
        JSONObject cargo = new JSONObject();
        cargo.put("cargoName", "Bus");
        cargo.put("sendNumber", "-45323");

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.put("/cargo/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(cargo));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect( content().json("{\"message\":\"sendNumber-Send number should be uniq and greater than 0;\"}"));
    }
}
