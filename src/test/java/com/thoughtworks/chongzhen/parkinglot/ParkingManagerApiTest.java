package com.thoughtworks.chongzhen.parkinglot;

import com.thoughtworks.chongzhen.parkinglot.controller.ParkingManagerController;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingBoyService;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ParkingManagerController.class)
public class ParkingManagerApiTest {
    @MockBean
    private ParkingBoyService parkingBoyService;

    @MockBean
    private ParkingManagerService parkingManagerService;

    @Autowired
    private MockMvc mockMvc;

    //TODO: changed
//    @Test
//    public void should_delete_parking_boy() throws Exception {
//        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().build();
//        when(parkingManagerService.deleteParkingBoy(1, "zuowen")).thenReturn(null);
//
//        mockMvc.perform(delete("/parkingManagers/parkingBoys/{id}", 1))
//                .andExpect(status().is(200))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.smart").value(true))
//                .andExpect(jsonPath("$.name").value("chongzhen"))
//                .andExpect(jsonPath("$.previousVisitedLot").value(0));
//    }


}
