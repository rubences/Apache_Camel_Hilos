
Apache Camel

DESCRIPCION: 
Se expone un servicio donde se muestra el manejo de hilos en una ruta Camel. El el request se divide en N hilos que procesan en paralelo y luego
sus respuestas individuales son agregadas para conformar la respuesta general del servicio. Además, en la ruta esta el seteo customizable del Pool 
(para poder mejorar rendimiento cuando la carga es muy grande) 

EJECUCION:
Correr el test 'HilosTest' o levantar en Tomcat y con alguna herramienta (ej soapUI) pegarle a http://localhost:8080/camel-thread-aggregation/notificar?wsdl 

AUTOR:
RJ

TECNOLOGIAS:
Apache camel
cxf
Maven
   
 
 
 