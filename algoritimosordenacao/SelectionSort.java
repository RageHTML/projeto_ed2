
package algoritimosordenacao;

public class SelectionSort implements AlgoritimoOrdenacao {

  @Override
	
  public void ordenarVetor(int[] vetor) {
	
    for(int i = 0; i < vetor.length; i++) {
		    	  
	  int indiceMenorElemento = i;
		
	  for(int j = i + 1; j < vetor.length; j++) {
		       
	    if( vetor[j] < vetor[indiceMenorElemento] ) {
		        
		  indiceMenorElemento = j;
		  
	    }
		
	  } 
		
	  if( indiceMenorElemento != i ) {
		    	   
		int menorElemento = vetor[indiceMenorElemento];
		
		vetor[indiceMenorElemento] = vetor[i];
		
		vetor[i] = menorElemento;
		
	  }
		
    }
	
  }

  @Override
	
  public String toString() {
	
    return "SelectionSort";
    
  }

}
