# Moteur C-SPARQL
C-SPARQL: UN LANGAGE DE RECHERCHE EN CONTINU POUR LES FLUX DE DONNÉES RDF

<p> Inspiré du document <a href="https://pdfs.semanticscholar.org/1aca/dbf0c0616b4b9bd287ff8d9d164d96778589.pdf"> C-SPARQL: UNE LANGUE DE RECHERCHE CONTINUE POUR LES FLUX DE DONNÉES RDF </a> et des exemples de C -SPARQL Engine présenté sur le site officiel de <a href="http://streamreasoning.org/"> Stream Reasoning </a>, j'ai écrit un exemple complet mais facile à comprendre du traitement de flux RDF avec un C-SPARQL Moteur. </p>

<p> Dans ce package, il y a un fichier <b> SampleCSparql.java </b> qui a la fonction principale dans laquelle nous avons défini 5 requêtes C-SPARQL que nous pouvons en choisir une à chaque fois et le processus de C-SPARQL Moteur. </p>

<p> Le fichier java <b> RDFStreamGenerator.java </b> et <b> MemoryMonitoringRDFStreamGenerator.java </b> sont deux générateurs de flux. Lorsque le fichier principal a été exécuté, le générateur de flux produira des flux en continu. </p>

<p> Le fichier java <b> ConsoleFormatter.java </b> donnera le résultat de la requête C-SPARQL que nous avons choisie </p>