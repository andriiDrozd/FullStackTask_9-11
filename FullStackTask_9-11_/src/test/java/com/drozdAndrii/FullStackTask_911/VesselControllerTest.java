package com.drozdAndrii.FullStackTask_911;

import com.drozdAndrii.FullStackTask_911.controllers.VesselController;
import com.drozdAndrii.FullStackTask_911.model.Cargo;
import com.drozdAndrii.FullStackTask_911.services.CargoService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
@Sql(value = {"create-vessel-before.sql", "create-cargo-before.sql",})
public class VesselControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VesselController controller;
    @Autowired
    private CargoService cargoService;

    @Test
    public void testController() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getAllVesselsTest() throws Exception {
        this.mockMvc.perform(get("/vessel"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010,\"cargoDTOS\":[{\"id\":1,\"cargoName\":\"BMW x5\",\"sendNumber\":123456,\"trackerDTO\":{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010}},{\"id\":2,\"cargoName\":\"Tesla Model 3\",\"sendNumber\":143446,\"trackerDTO\":{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010}}]},{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009,\"cargoDTOS\":[{\"id\":3,\"cargoName\":\"Maybach S 560\",\"sendNumber\":222456,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}},{\"id\":4,\"cargoName\":\"Porsche Cayman S 987\",\"sendNumber\":223426,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}}]},{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011,\"cargoDTOS\":[{\"id\":5,\"cargoName\":\"Mercedes-Benz GLC\",\"sendNumber\":324456,\"trackerDTO\":{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011}},{\"id\":6,\"cargoName\":\"Lamborghini Urus\",\"sendNumber\":329426,\"trackerDTO\":{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011}}]}]"));
    }

    @Test
    public void getVesselById() throws Exception {
        this.mockMvc.perform(get("/vessel/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011,\"cargoDTOS\":[{\"id\":5,\"cargoName\":\"Mercedes-Benz GLC\",\"sendNumber\":324456,\"trackerDTO\":{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011}},{\"id\":6,\"cargoName\":\"Lamborghini Urus\",\"sendNumber\":329426,\"trackerDTO\":{\"id\":3,\"vesselName\":\"M/V Warnow Moon\",\"yearOfBuild\":2011}}]}"));
    }

    @Test
    public void deleteVessel() throws Exception {
        this.mockMvc.perform(delete("/vessel/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009,\"cargoDTOS\":[{\"id\":3,\"cargoName\":\"Maybach S 560\",\"sendNumber\":222456,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}},{\"id\":4,\"cargoName\":\"Porsche Cayman S 987\",\"sendNumber\":223426,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}}]}"));
    }

    @Test
    public void editVesselTest() throws Exception {
        JSONObject vessel = new JSONObject();
        vessel.put("vesselName", "TestVesselName");
        vessel.put("yearOfBuild", "2000");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/vessel/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(vessel));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":2,\"vesselName\":\"TestVesselName\",\"yearOfBuild\":2000,\"cargoDTOS\":[{\"id\":3,\"cargoName\":\"Maybach S 560\",\"sendNumber\":222456,\"trackerDTO\":{\"id\":2,\"vesselName\":\"TestVesselName\",\"yearOfBuild\":2000}},{\"id\":4,\"cargoName\":\"Porsche Cayman S 987\",\"sendNumber\":223426,\"trackerDTO\":{\"id\":2,\"vesselName\":\"TestVesselName\",\"yearOfBuild\":2000}}]}"));
    }

    @Test
    public void addCargoToVesselTest() throws Exception {
        cargoService.saveCargo(new Cargo("TestCargo",345644));
        this.mockMvc.perform(post("/vessel/1/cargo/7/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010,\"cargoDTOS\":[{\"id\":1,\"cargoName\":\"BMW x5\",\"sendNumber\":123456,\"trackerDTO\":{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010}},{\"id\":2,\"cargoName\":\"Tesla Model 3\",\"sendNumber\":143446,\"trackerDTO\":{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010}},{\"id\":7,\"cargoName\":\"TestCargo\",\"sendNumber\":345644,\"trackerDTO\":{\"id\":1,\"vesselName\":\"M/V AAL Paris\",\"yearOfBuild\":2010}}]}"));
    }

    @Test
    public void deleteCargoFromVesselTest() throws Exception{
        this.mockMvc.perform(delete("/vessel/2/cargo/4/delete"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009,\"cargoDTOS\":[{\"id\":3,\"cargoName\":\"Maybach S 560\",\"sendNumber\":222456,\"trackerDTO\":{\"id\":2,\"vesselName\":\"M/V Warnow Merkur\",\"yearOfBuild\":2009}}]}"));
    }
//    @Test
//    public void createVesselWithEmptyNameTest() throws Exception {
//        JSONObject vessel = new JSONObject();
//        vessel.put("vesselName", "");
//        vessel.put("yearOfBuild", "2000");
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/vessel")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(String.valueOf(vessel));
//
//        this.mockMvc.perform(mockRequest)
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(content().json("{\"message\":\"vesselName-Vessel name should be between 2 and 100 characters;vesselName-Vessel name should not be empty;\"}"));
//    }

    @Test
    public void createVesselWithNameLengthLessTwoCharacterTest() throws Exception {
        JSONObject vessel = new JSONObject();
        vessel.put("vesselName", "A");
        vessel.put("yearOfBuild", "2000");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/vessel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(vessel));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"vesselName-Vessel name should be between 2 and 100 characters;\"}"));
    }

    @Test
    public void createVesselWithYearOfBuiltLessTest() throws Exception {
        JSONObject vessel = new JSONObject();
        vessel.put("vesselName", "Vessel");
        vessel.put("yearOfBuild", "1799");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/vessel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(vessel));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"yearOfBuild-Year of built should be greater than 1800;\"}"));
    }

    @Test
    public void createVesselWithYearOfBuiltGreaterTest() throws Exception {
        JSONObject vessel = new JSONObject();
        vessel.put("vesselName", "Vessel");
        vessel.put("yearOfBuild", "2024");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/vessel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(vessel));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"yearOfBuild-Year of built should be less than 2024;\"}"));
    }

    @Test
    public void editVesselWithYearOfBuiltGreaterTest() throws Exception {
        JSONObject vessel = new JSONObject();
        vessel.put("vesselName", "Vessel");
        vessel.put("yearOfBuild", "2024");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/vessel/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(vessel));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"yearOfBuild-Year of built should be less than 2024;\"}"));
    }

    @Test
    public void editVesselWithYearOfBuiltLessTest() throws Exception {
        JSONObject vessel = new JSONObject();
        vessel.put("vesselName", "Vessel");
        vessel.put("yearOfBuild", "1800");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/vessel/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(vessel));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"yearOfBuild-Year of built should be greater than 1800;\"}"));
    }

    @Test
    public void editVesselWithNameLengthLessTwoCharacterTest() throws Exception {
        JSONObject vessel = new JSONObject();
        vessel.put("vesselName", "A");
        vessel.put("yearOfBuild", "1987");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/vessel/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(vessel));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"vesselName-Vessel name should be between 2 and 100 characters;\"}"));
    }


}
