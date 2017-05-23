import core.TestDriver.TestDriver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by pvalls on 10/04/2017.
 * <p>
 * <p>
 * 1.LOS VALORES  DEL OUPUT SE MEZCLAN ( NO ES UN ERROR YA QUE SE EJECUTAN VARIOS THREADS)
 * 2.NO PUEDE ESTAR REPETIDO  ****** SUPER IMPORTANTE******
 * 3.ESTE PROGRAMA NECESITARA MUCHOS RECURSOS DEL PC (CADA THREAD ES UNA VENTANA DE FIREFOX Y MINIMO SERAN 2)
 * 4.NO SE PUEDEN MOVER LAS PANTALLAS CAMBIANDO EL TAMAÑO ( SI SE MUEVEN PUEDE NO DETECTAR EL VALOR NECESARIO Y PETAR)
 *
 *   //////////////////////IMPORTANTE//////////////////////////////
 *  //LOS XPATH SE GENERAN DINAMICAMENTE en jenkins ASI QUE HAY QUE COMPROBAR QUE  ESTEN CORRECTOS ANTES DE USARLO
 */
public class Main {
    String[] camposnombre;
    String[] camposjira;
    String[] camposmaven;

    public Main(String[] args) {
        generatedata();
        generatecuantities(args);
    }

    public static void main(String args[]) {
        //constructor for dinamic process
        new Main(args);

    }

    /**
     * this method return  the best cuantitiy of threads for use
     * @param numeros
     * @param arraysgenerados
     * @return
     */
    public int searchbestnum(ArrayList<Integer> numeros,ArrayList<Integer> arraysgenerados){
        // aqui se compararán los arrays para poder encontrar coincidencias con el tamaño y el numero de arrays. si coinciden significan que  es el nuemro
        //optimo  de threads que seran utiles, estos se multiplicarán por 2 para referirse en test y en sis
        int num1,num,aux=0;
    for (int i = 0; i < numeros.size(); i++) {
        num=numeros.get(i);
        for (int j = 0; j < arraysgenerados.size(); j++) {
            num1=arraysgenerados.get(j);

            if (num==num1 || num1==aux){
                System.out.println("threads optimos:" +num1*2);
                return num1;
            }else{
                aux=num;
            }
        }
    }
    //error
    System.out.println("threads optimos: 1");
    return 1;
}
    /**
     * This method prepares all  the threads  for running in the same time, all the values  will be generate automaticaly.
     * We create 2 drivers  for 2 aplications  test, and sis ( folders of jenkins
     *
     * @param camposnombre array of the names of test
     * @param camposjira   array of the migration in maven ( only YES or NO)
     * @param camposmaven  array of jira  data in the test
     * @param size         size of the  subarrays
     * @param cant         cuantity of  subarrays generate
     * @param args         necesary for the testdriver
     */
    public void initall(String[] camposnombre, String[] camposjira, String[] camposmaven, int size, int cant, String[] args) {


        for (int i = 0; i < cant; i++) {
            //Se genera el driver de sis
            TestDriver driversis = new TestDriver(args);
            //se pone la url de Jenkins
            driversis.getDriverdetails().setUrl("http://URL DE JENKINS/");
            //se genera el driver de test
            TestDriver drivertest = new TestDriver(args);
            //se pone la url de Jenkins
            drivertest.getDriverdetails().setUrl("http://URL DE JENKINS/");
            //listas generadas para los threads ******  se pueden sacar de aqui sin problemas?????******
            String[] sublistnames = new String[size];
            String[] sublistjira = new String[size];
            String[] sublistmaven = new String[size];

            /*
             en estos 3 for se generaran arrays que contendran una x parte de los valores introducidos, a x es el size
             los valores que se guardan seran array[j(numero autoincremental) +(size*i)-->( que sera la suma de el valor de sizes segun los loops que haya dado)]
             */

            //bucle nombres
            for (int j = 0; j < size; j++) {
                sublistnames[j] = camposnombre[j + (size * i)];
            }
            //bucle jira
            for (int j = 0; j < size; j++) {
                sublistjira[j] = camposjira[j + (size * i)];
            }
            // bucle maven
            for (int j = 0; j < size; j++) {
                sublistmaven[j] = camposmaven[j + (size * i)];
            }
            //INICIACION DE THEADS
            ManagerJenkins managerSIS = new ManagerJenkins(driversis, sublistnames, sublistjira, sublistmaven, "SIS");
            ManagerJenkins managerTest = new ManagerJenkins(drivertest, sublistnames, sublistjira, sublistmaven, "TEST");
            managerSIS.start();
            managerTest.start();
            //FIN DE LOOP ( POR CADA LOOP SE SOBREESCRIBIAN LOS VALORES)
        }


    }

    /**
     * This method generate the cuantities of the arrays
     * @param args
     */
    public void generatecuantities(String args[]) {

        int j=camposnombre.length;
        ArrayList<Integer> numeros= new ArrayList<Integer>();
        ArrayList<Integer> arraysgenerados= new ArrayList<Integer>();
        System.out.println(camposnombre.length);
        for (int i = 0; i < camposnombre.length; i++) {
            /*
            metodo que hara  3 comprobaciones:
            1. que sea un numero con un resto 0 ( es decir la division quedara con resultado entero)
            2. que j sea diferente a 1 ( esto se controla en el  ultimo if)
            3. que j no sea igual al tamaño
             */
            if (((camposnombre.length) % j == 0) && j!=1 && j!=camposnombre.length) {
                System.out.println("tamaño de los arrays: " + ((camposnombre.length) / j) + "| numero de arrays generados: " + j + "| cantidad de Threads que se usaran: " + (2 * j));
                numeros.add(((camposnombre.length) / j));
                arraysgenerados.add(j);

            }
            // control para cuando sea igual a el tamaño  es decier si el tamaño es j = al tamaño maximo
            // se evitará de que se hagan x arrays de 1, se hará 1 array de x
            if (j == 1) {

                System.out.println("tamaño de los arrays: " + ((camposnombre.length) / j) + "| numero de arrays generados: " + j + "| cantidad de Threads que se usaran: " + (2 * j));
                numeros.add(((camposnombre.length) / j));
                arraysgenerados.add(j);
                //se calcurarán los threads optimos
                j = searchbestnum(numeros,arraysgenerados);
                int tamaño = ((camposnombre.length) / j);
                System.out.println("tamaño de los arrays: " + ((camposnombre.length) / j) + "| numero de arrays generados: " + j + "| cantidad de Threads que se usaran: " + (2 * j));

                //////////////////todo descomentar esto para arrancar los threads ///////////////////
             //   initall(camposnombre, camposjira, camposmaven, tamaño, j, args);
                ////////////////////////////////////////////////////////////////////////////////

            }
            j--;
        }

    }

    /**
     * method for generate the arrays  with the data of the user
     */
    public void generatedata() {

        System.out.println("Introduccion de datos");
        // introduccion de datos manual
        String nombreTests = JOptionPane.showInputDialog("Introduce los NOMBRES de los tests que quieres generar en jenkins");
        String mavenTests = JOptionPane.showInputDialog("Introduce los CAMPOS DE MIGRACION de maven de los tests que quieres generar en jenkins");
        String jiraTests = JOptionPane.showInputDialog("Introduce los DATOS DE JIRA de los tests que quieres generar en jenkins");
        // se generan los arrays
        camposnombre = nombreTests.split(" ");
        camposjira = jiraTests.split(" ");
        camposmaven = mavenTests.split(" ");
    }
}
