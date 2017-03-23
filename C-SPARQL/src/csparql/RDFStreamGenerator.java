package csparql;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;

public class RDFStreamGenerator extends RdfStream implements Runnable {

	private int c = 1;
	private int ct = 1;
	private boolean keepRunning = false;

	public RDFStreamGenerator(final String iri) {
		super(iri);
	}

	public void stop() {
		keepRunning = false;
	}

	@Override
	public void run() {

		keepRunning = true;
		
		while (keepRunning) {
			// generate quadruples
			final RdfQuadruple q = new RdfQuadruple(super.getIRI()+"/U" + this.c,
					"http://myexample.org/follows", "http://myexample.org/U" + this.c, System.currentTimeMillis());
			this.put(q);
			System.out.println(q.toString());
			ct++;

			double n = Math.random()*5;

			for (int i=0;i<n;i++) {
				final RdfQuadruple q1 = new RdfQuadruple(super.getIRI()+"/U" + this.c+i,
						"http://myexample.org/follows", "http://myexample.org/U" + this.c, System.currentTimeMillis());
				this.put(q1);
				System.out.println(q1.toString());
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ct++;
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.c++;
		}
	}

}
