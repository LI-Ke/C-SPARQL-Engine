package csparql;

import java.util.Random;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;

public class MemoryMonitoringRDFStreamGenerator extends RdfStream implements Runnable {
	
	private boolean keepRunning = false;
	
	public MemoryMonitoringRDFStreamGenerator(final String iri) {
		super(iri);
	}
	
	public void stop() {
		keepRunning = false;
	}
	
	@Override
	public void run() {
		
		keepRunning = true;
		
		while (keepRunning) {
			try{
	
				RdfQuadruple q;
				Random random = new Random();
				int num = random.nextInt(101);
				//First Observation
				long tempTS = System.currentTimeMillis();
				q = new RdfQuadruple(super.getIRI() + "/pc" + num, "http://myexample.org/hasBrand", super.getIRI() + "/ACER", tempTS);
				this.put(q);
				System.out.println(q.toString());
				int occupation = random.nextInt(101);
				q = new RdfQuadruple(super.getIRI() + "/pc" + num, "http://myexample.org/memoryUsed", occupation + "^^http://www.w3.org/2001/XMLSchema#integer", tempTS);
				this.put(q);
				System.out.println(q.toString());
				
				Thread.sleep(1000);
				
	
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
