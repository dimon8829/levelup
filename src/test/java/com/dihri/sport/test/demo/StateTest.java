package com.dihri.sport.test.demo;

import com.dihri.sport.test.demo.service.StateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StateTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StateService stateService;

    @Test
    public void integrationTest() throws Exception {
        //Attempt pass zero userId
        mockMvc.perform(get("/api/state/"+0))
                .andExpect(status().isBadRequest());
        //pass normal userId
        int userId = ThreadLocalRandom.current().nextInt(1,  Integer.MAX_VALUE);
        mockMvc.perform(get("/api/state/"+userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exp").value(0))
                .andExpect(jsonPath("$.level").value(1));
        //pass exp without change level
        String content = "{\"exp\":90}";
        mockMvc.perform(post("/api/state/"+userId).content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exp").value(90))
                .andExpect(jsonPath("$.level").value(1));
        //pass exp with change level
        content = "{\"exp\":10}";
        mockMvc.perform(post("/api/state/"+userId).content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exp").value(0))
                .andExpect(jsonPath("$.level").value(2));
        //pass exp with change level
        content = "{\"exp\":950}";
        mockMvc.perform(post("/api/state/"+userId).content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exp").value(40))
                .andExpect(jsonPath("$.level").value(11));
        //check user
        mockMvc.perform(get("/api/state/"+userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exp").value(40))
                .andExpect(jsonPath("$.level").value(11));
        //pass another userId
        int userId2 = ThreadLocalRandom.current().nextInt(1,  Integer.MAX_VALUE);
        content = "{\"exp\":110}";
        mockMvc.perform(post("/api/state/"+userId2).content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exp").value(10))
                .andExpect(jsonPath("$.level").value(2));


    }

    @Test
    public void multithreadingTest() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //executor.su
        int count = 800;
        CountDownLatch cdl = new CountDownLatch(count);
        int userId = ThreadLocalRandom.current().nextInt(1,  Integer.MAX_VALUE);
        for(int i=0;i<count;i++) {
            Future future = executor.submit(() -> {
                stateService.updateState(userId,1);
                cdl.countDown();
                return 6;
            });

        }
        cdl.await();
        //check user
        mockMvc.perform(get("/api/state/"+userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exp").value(0))
                .andExpect(jsonPath("$.level").value(9));


    }
}


