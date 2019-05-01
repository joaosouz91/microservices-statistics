package br.com.fiap.joaovictor.microservicesstatistics.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.joaovictor.microservicesstatistics.factory.TransactionFactory;
import br.com.fiap.joaovictor.microservicesstatistics.model.Statistics;
import br.com.fiap.joaovictor.microservicesstatistics.model.Transaction;

@RestController
@RequestMapping("/statistics-service")
public class StatisticsController {
	
	@Autowired
	TransactionFactory transactionFactory;
		
	@GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getStatistics() {
		List<Transaction> lastSixtySecondsTransactions = transactionFactory.getLastSixtySecondsTransactions();
	
		if(lastSixtySecondsTransactions == null || lastSixtySecondsTransactions.size() == 0) return new ResponseEntity<>( HttpStatus.NO_CONTENT );
		
		Statistics st = new Statistics();
		
		double sum = lastSixtySecondsTransactions.stream().mapToDouble(num -> num.getAmount()).sum();
		double avg = lastSixtySecondsTransactions.stream().mapToDouble(num -> num.getAmount()).average().orElse( Double.NaN );
		double max = Collections.max(lastSixtySecondsTransactions, Comparator.comparing(v -> v.getAmount())).getAmount();
		double min = Collections.min(lastSixtySecondsTransactions, Comparator.comparing(v -> v.getAmount())).getAmount();
		long count = lastSixtySecondsTransactions.stream().count();

	    st.setCount(count);
	    st.setSum(sum);
	    st.setAvg(avg);
	    st.setMax(max);
	    st.setMin(min);
	    
		return new ResponseEntity<>(st, HttpStatus.OK);
	}
	
	@PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity saveTransaction(@RequestBody Transaction transactionRequest) throws Exception {
		
		if(transactionRequest.getTimestamp() == null || transactionRequest.getAmount() == null) {
			throw new Exception();
		}

		if(transactionFactory.addTransaction(transactionRequest)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
