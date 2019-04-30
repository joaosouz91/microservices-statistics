package br.com.fiap.joaovictor.microservicesstatistics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
	
	private long count;
	private double sum;
    private double avg;
    private double max;
    private double min;
    
}
