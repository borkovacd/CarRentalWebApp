package borkovacd.carrentalapp.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import borkovacd.carrentalapp.model.VehicleBrand;
import borkovacd.carrentalapp.service.VehicleBrandService;

@WebMvcTest(controllers = VehicleBrandController.class)
class VehicleBrandControllerUnitTest {
   
    @Autowired                           
    private MockMvc mockMvc;  
                                                 
    @MockBean                           
    private VehicleBrandService vehicleBrandService; 
                                               
    private List<VehicleBrand> vehicleBrandList;       
                                            
    @BeforeEach                           
    void setUp() {                               
       this.vehicleBrandList = new ArrayList<>();                                    
       this.vehicleBrandList.add(new VehicleBrand(1L,"Toyota"));              
       this.vehicleBrandList.add(new VehicleBrand(2L, "Audi"));                        
       this.vehicleBrandList.add(new VehicleBrand(3L, "BMW"));                                                       
    }
    
    @Test
    void shouldFetchAllVehicleBrands() throws Exception {
    	
    	given(vehicleBrandService.findAll()).willReturn(vehicleBrandList);
    	
    	this.mockMvc.perform(get("/api/brands"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.size()", is(vehicleBrandList.size())));
    }
}
