package br.com.fiap.joaovictor.microservicesstatistics.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.joaovictor.microservicesstatistics.model.Transaction;

@Repository
public class TransactionFactory {

    //private List<Transaction> transactions = new ArrayList<Transaction>();
    private static List<Transaction> transactions = Collections.synchronizedList(new ArrayList<Transaction>());
    
    public boolean addTransaction(Transaction transaction) {
    	
    	Long sysTimestamp = System.currentTimeMillis();
    	
    	long difference = sysTimestamp - transaction.getTimestamp();
		System.out.println("DIFFERENCE: " + difference);
		if(difference > 60000l) {
			return false;
		}
		
        transactions.add(transaction);
        return true;
    }
    
    public List<Transaction> getAllTransactions() {
		return transactions;
	}
    
    public List<Transaction> getLastSixtySecondsTransactions(long requestTimestamp){
		
    	List<Transaction> lastSixtySecondsTransactions = Collections.synchronizedList(new ArrayList<Transaction>());
    	for (Transaction transaction : transactions) {
			long difference = requestTimestamp - transaction.getTimestamp();
			if(difference <= 60000l) {
				lastSixtySecondsTransactions.add(transaction);
			}
		}
    	return lastSixtySecondsTransactions;
    }
    
    public void removeAllTransactions() {
    	transactions.clear();
    }
}
