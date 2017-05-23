# Generador-de-datos-para-Jenkins-
Programa que usa selenium y webdriver ( llamado Testdriver) que permite la generación de datos masivos en threads dentro del servidor de Jenkins  para poder ejecutar datos por parámetros de windows.

Este programa obtiene datos por joptionpanel,  y los divide en 3 arrays, que serán los  datos de parametros para poder ejecutar el test desde jenkins, si esta pasado a maven ( la version antigua no usaba maven) y los datos de jira para poder poner dentro del pase que está correcto.

cuando los obtiene, se hace el calculo que permite dividir todos los datos en arrays que se usarán para usar los threads, que todos estos arrancarán  los servidores de jenkins desde un navegador ( preferiblemente firefox) y arrancando el servidor y entrando con usuario y contraseña,  va a la carpeta seleccionada y  realiza la creacion de items, con los parametros obtenidos del array automaticamente.
