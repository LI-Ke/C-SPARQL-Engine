# C-SPARQL-Engine

<p>Inspired by the paper <a href="https://pdfs.semanticscholar.org/1aca/dbf0c0616b4b9bd287ff8d9d164d96778589.pdf">C-SPARQL: A CONTINUOUS QUERY LANGUAGE FOR RDF DATA STREAMS</a> and examples of the C-SPARQL Engine shown in the <a href="http://streamreasoning.org/">Stream Reasoning</a> official website, I've written a complete but easy to understand example of the RDF Stream Processing with a C-SPARQL Engine.</p>

<p>In this package, there is file <a href="./C-SPARQL/src/scparql/SampleCSparql.java">SampleCSparql.java</a> who has the main function in which we have defined 5 C-SPARQL queries that we can choose one each time and the process of C-SPARQL Engine.</p>

<p>The java file <a href="./C-SPARQL/src/scparql/RDFStreamGenerator.java">RDFStreamGenerator.java</a> and <a href="./C-SPARQL/src/scparql/MemoryMonitoringRDFStreamGenerator.java">MemoryMonitoringRDFStreamGenerator.java</a> are two stream generators. When the main file has been executed, the stream generator will produce streams continuously.</p>

<p>The java file <a href="./C-SPARQL/src/scparql/ConsoleFormatter.java">ConsoleFormatter.java</a> will give the result of the C-SPARQL query that we have chosen</p>
