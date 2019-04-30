package br.com.fiap.joaovictor.microservicesstatistics.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.joaovictor.microservicesstatistics.factory.TransactionFactory;
import br.com.fiap.joaovictor.microservicesstatistics.model.Statistics;
import br.com.fiap.joaovictor.microservicesstatistics.model.Transaction;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionFactory transactionFactory;

    private Long sysTimestamp;

    @Before
    public void setUp() throws Exception {
        sysTimestamp = System.currentTimeMillis();
    }

    @Test
    public void transactionCreated() throws Exception {
        Transaction transaction = new Transaction(sysTimestamp - 1l, 300.00);
        System.out.println("transaction timestamp: " + transaction.getTimestamp());
        System.out.println("systimestamp timestamp: " + sysTimestamp);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        
        String jsonStr = mapper.writeValueAsString(transaction);
        System.out.println("SYSOUT: " + jsonStr);
        
        when(transactionFactory.addTransaction(transaction, sysTimestamp)).thenCallRealMethod(); // thenReturn(true);

        mvc.perform(post("/statistics-service/transactions")
                .contentType("application/json").content(jsonStr))
                .andExpect(status().isCreated());
    }

    @Test
    public void transactionNoContent() throws Exception {
        Transaction transaction = new Transaction(sysTimestamp - 60001, 300.00);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String jsonInString = mapper.writeValueAsString(transaction);

        when(this.transactionFactory.addTransaction(transaction, sysTimestamp)).thenCallRealMethod(); //thenThrow(MoreThan60SecException.class);

        mvc.perform(post("/statistics-service/transactions")
                .contentType("application/json").content(jsonInString))
                .andExpect(status().isNoContent());
    }

    @Test
    public void statisticsFoundedIn60Sec() throws Exception {
        Statistics statistics = new Statistics(
        		3l,
        		900.00,
                300.00,
                400.00,
                200.00
        );

        Transaction t1 = new Transaction(sysTimestamp - 1, 300.00);
        Transaction t2 = new Transaction(sysTimestamp - 2, 200.00);
        Transaction t3 = new Transaction(sysTimestamp - 3, 400.00);

        transactionFactory.addTransaction(t1, sysTimestamp);
        transactionFactory.addTransaction(t2, sysTimestamp);
        transactionFactory.addTransaction(t3, sysTimestamp);

        List<Transaction> lastSixtySecondsTransactions = new ArrayList<Transaction>();
        if (sysTimestamp - t1.getTimestamp() <= 60000) {
        	lastSixtySecondsTransactions.add(t1);
        }
        if (sysTimestamp - t2.getTimestamp() <= 60000) {
        	lastSixtySecondsTransactions.add(t2);
        }
        if (sysTimestamp - t3.getTimestamp() <= 60000) {
        	lastSixtySecondsTransactions.add(t3);
        }

        when(this.transactionFactory.getLastSixtySecondsTransactions(sysTimestamp)).thenReturn(lastSixtySecondsTransactions);

        mvc.perform(get("/statistics-service/statistics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(response -> {
                    String json = response.getResponse().getContentAsString();
                    Statistics foundedStatistics = new ObjectMapper().readValue(json, Statistics.class);
                    assertThat(statistics).isEqualToComparingFieldByField(foundedStatistics);
                });
    }

    @Test
    public void statisticsNotFoundedIn60Sec() throws Exception {
        Statistics statistics = new Statistics(
        		0l,
        		0.00,
                0.00,
                0.00,
                0.00                
        );

        Transaction t1 = new Transaction(sysTimestamp - 60001, 300.00);
        Transaction t2 = new Transaction(sysTimestamp - 60002, 200.00);
        Transaction t3 = new Transaction(sysTimestamp - 60003, 400.00);

        this.transactionFactory.addTransaction(t1, sysTimestamp);
        this.transactionFactory.addTransaction(t2, sysTimestamp);
        this.transactionFactory.addTransaction(t3, sysTimestamp);

        List<Transaction> lastSixtySecondsTransactions = new ArrayList<Transaction>();
        if (sysTimestamp - t1.getTimestamp() <= 60000) {
        	lastSixtySecondsTransactions.add(t1);
        }
        if (sysTimestamp - t2.getTimestamp() <= 60000) {
        	lastSixtySecondsTransactions.add(t2);
        }
        if (sysTimestamp - t3.getTimestamp() <= 60000) {
        	lastSixtySecondsTransactions.add(t3);
        }

        when(this.transactionFactory.getLastSixtySecondsTransactions(sysTimestamp)).thenReturn(lastSixtySecondsTransactions);

        mvc.perform(get("/statistics-service/statistics")
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }
}
