package csparql;

import java.text.ParseException;

import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.engine.CsparqlEngine;
import eu.larkc.csparql.engine.CsparqlEngineImpl;
import eu.larkc.csparql.engine.CsparqlQueryResultProxy;

public class SampleCSparql {
	/**
	 * A simple example to simulate the RDF Stream Process
	 */

	public static void main(String[] args) {
		
		// examples of streams and queries
		// define some immutable values to represent different queries
		final int WHO_FOLLOWS_WHO = 0;
		final int HOW_MANY_USERS_FOLLOW_THE_SAME_USER = 1;
		final int MULTI_SOCIAL_NETWORK = 2;
		final int WHO_HAS_THE_MOST_FOLLOWERS = 3;
		final int WHICH_MACHINE_OVERUSED = 4;
		
		// put here the example you want to run
		//int key = WHO_FOLLOWS_WHO;
		//int key = HOW_MANY_USERS_FOLLOW_THE_SAME_USER;
		//int key = MULTI_SOCIAL_NETWORK;
		//int key = WHO_HAS_THE_MOST_FOLLOWERS;
		int key = WHICH_MACHINE_OVERUSED;


		// initializations
		String query = null;
				
		// instantiation of stream generator
		RdfStream rdfStreamGenerator = null;
		RdfStream rdfStreamGenerator2 = null;

		switch(key){
		case WHO_FOLLOWS_WHO:
			query = "REGISTER QUERY WhoFollowsWho AS "
					+ "PREFIX ex: <http://myexample.org/> "
					+ "SELECT ?follower ?followee "
					+ "FROM STREAM <http://myexample.org/stream> [RANGE 5s STEP 1s] "
					+ "WHERE { ?follower ex:follows ?followee } ";
			rdfStreamGenerator  = new RDFStreamGenerator("http://myexample.org/stream");
			break;
		
		case HOW_MANY_USERS_FOLLOW_THE_SAME_USER:
			query = "REGISTER QUERY HowManyUsersFollowTheSameUser AS "
					+ "PREFIX ex: <http://myexample.org/> "
					+ "SELECT ?followee (COUNT(?follower) AS ?n) "
					+ "FROM STREAM <http://myexample.org/stream> [RANGE 5s STEP 1s] "
					+ "WHERE {?follower ex:follows ?followee } "
					+ "GROUP BY ?followee";
			rdfStreamGenerator  = new RDFStreamGenerator("http://myexample.org/stream");	
			break;
		
		case MULTI_SOCIAL_NETWORK:
			query = "REGISTER QUERY MultipleSocialNetwork AS "
					+ "PREFIX ex: <http://myexample.org/> "
					+ "SELECT ?followee (COUNT(?follower) AS ?n) "
					+ "FROM STREAM <http://myexample.org/stream1> [RANGE 5s STEP 1s] "
					+ "FROM STREAM <http://myexample.org/stream2> [RANGE 5s STEP 1s] "
					+ "WHERE {?follower ex:follows ?followee } "
					+ "GROUP BY ?followee";
			rdfStreamGenerator  = new RDFStreamGenerator("http://myexample.org/stream1");
			rdfStreamGenerator2  = new RDFStreamGenerator("http://myexample.org/stream2");
			break;
		
		case WHO_HAS_THE_MOST_FOLLOWERS:
			query = "REGISTER QUERY WhoHasTheMostFollowers AS "
					+ "PREFIX ex: <http://myexample.org/> "
					+ "SELECT ?followee (COUNT(?follower) AS ?n) "
					+ "FROM STREAM <http://myexample.org/stream> [RANGE 10s STEP 5s] "
					+ "WHERE { "
					+ "?follower ex:follows ?followee . "
					+ "}"
					+ "GROUP BY ?followee "
					+ "ORDER BY DESC(?n) "
					+ "LIMIT 1";
			rdfStreamGenerator  = new RDFStreamGenerator("http://myexample.org/stream");
			break;
			
		
		case WHICH_MACHINE_OVERUSED:
			query = "REGISTER QUERY WhichMachineOverused AS "
					+ "PREFIX ex: <http://myexample.org/> "
					+ "SELECT ?machine  "
					+ "FROM STREAM <http://myexample.org/stream> [RANGE 10s STEP 5s] "
					+ "WHERE { "
					+ "?machine ex:hasBrand <http://myexample.org/stream/ACER> . "
					+ "?machine ex:memoryUsed ?occupation ; "
					+ "FILTER (?occupation > 70)"
					+ "}";
			rdfStreamGenerator  = new MemoryMonitoringRDFStreamGenerator("http://myexample.org/stream");
			break;
			
		default:
			System.exit(0);
			break;
		}

		// Initialize C-SPARQL Engine
		CsparqlEngine engine = new CsparqlEngineImpl();
		engine.initialize(true);
		
		// Register an RDF Stream
		engine.registerStream(rdfStreamGenerator);
		
		// Start Streaming
		final Thread t = new Thread((Runnable) rdfStreamGenerator);
		t.start();
		if (rdfStreamGenerator2 != null) {
			engine.registerStream(rdfStreamGenerator2);
			final Thread t2 = new Thread((Runnable) rdfStreamGenerator2);
			t2.start();
		}
		
		// Register a C-SPARQL query
		CsparqlQueryResultProxy c = null;
		try {
			c = engine.registerQuery(query);
			System.out.println("Query: " + query);
			System.out.println("Query Start Time : " + System.currentTimeMillis());
		} catch (final ParseException ex) {
			System.out.println("Query syntax error: " + ex.getMessage());
		}

		// Attach a Result Formatter to the query result proxy
		if (c != null) {
			c.addObserver(new ConsoleFormatter());
		}
		
		// leave the system running for a while
		try {
			// define the lifeline of the main thread
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// clean up (i.e., unregister query and stream) 
		engine.unregisterQuery(c.getId());
		((RDFStreamGenerator) rdfStreamGenerator).stop();
		engine.unregisterStream(rdfStreamGenerator.getIRI());
		if (rdfStreamGenerator2 != null) {
			engine.unregisterStream(rdfStreamGenerator2.getIRI());
		}
		
		System.exit(0);
		
	}
}
