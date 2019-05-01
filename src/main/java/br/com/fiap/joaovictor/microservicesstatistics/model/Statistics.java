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
	
	private Long count;
	private Double sum;
    private Double avg;
    private Double max;
    private Double min;
    
}
