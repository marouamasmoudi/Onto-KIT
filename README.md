# Onto-KIT
Onto-KIT is an ontology-based Knowledge hypergraph data Integration and querying Tool. It is composed of two software modules: 
DISERTO (Data Integration Semantic hypERgraph Tool) and HyQ (Hypergraph-based data Querying)
# DISERTO
DISERTO exposes the content of heterogeneous data sources as a knwoledge hypergraph using ontologies and thesaurus. This knwoledge hypergraph is virtual which means that data remains in the data sources and is accessed using mappings.
# HyQ
HyQ implements the hypergraph-based query processing. It rewrites SPARQL queries expressed over the ontology into sub-queries executed by the data sources. It relies on RML mappings to transform
heterogeneous data into RDF format.
# Libraries
Onto-KIT is implemented in Java and integrates several open-source libraries:
* OWL API: to manipulate the ontology.
* RDF4J: to generate RDF quads, manipulate RDF triple stores, and execute the SPARQL queries.
* GDAL: to read multiple raster formats (ENVI, GeoTIFF, etc.).
* RMLMapper: to execute RML rules in order to generate data in forms of RDFtriples.
* JavaFX API:  to create the toolinterfaces.
