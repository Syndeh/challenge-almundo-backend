#Dar	alguna	solución	sobre	qué	pasa	con	una	llamada	cuando	no	hay
#ningún	empleado	libre.
en el metodo getEmployee se busca un empleado libre su no hay ninguno se espera 1 segundo y se vuelve a 
consultar el metodo recursivamente teniendo en cuenta que en algun momento va a estar alguno disponible
y siempre buscando primero por operadores -> supervisores -> directores


#Dar	alguna	solución	sobre	qué	pasa	con	una	llamada	cuando	entran
#más	de	10	llamadas	concurrentes.
el metodo dispatchCall tiene el @Async de spring y el proyecto esta configurado para que soporte 10 threads
en el caso de que se llame mas veces spring maneja una cola y ejecuta la llamada cuando esta libre alguno de los
10 Threads

#Agregar	los	tests	unitarios	que	se	crean	convenientes.
tiene test de 10 llamadas
de mas de 10 llamadas al mismo tiempo
un test simple para que un operador atienda la primer llamada
test con todos los operadores ocupados para que atienda un supervisor
test con operadores y supervisores ocupados para que atienda un director
pero no se me ocurrio de forma rapida algun assert

#Agregar	documentación	de	código
Agregue javadoc a los metodos del Dispacher y Employee