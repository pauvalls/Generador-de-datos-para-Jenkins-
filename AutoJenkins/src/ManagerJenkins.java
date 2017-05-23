import com.sun.webkit.Timer;
import core.CommonActions.Functions;
import core.TestDriver.TestDriver;

import javax.swing.*;
import java.lang.annotation.ElementType;
import java.util.concurrent.TimeUnit;

/**
 * Created by pvalls on 10/04/2017.
 */
public class ManagerJenkins extends Thread {
    /**
     * Clase manejadora de  el programa, se encargara de poder generar automaticamente  todos los test del excel dentro de jenkins
     */
    protected String user = "Pau Valls Moreno";
    protected String pass = "contraseña :)";
    String where;
    //ESTE DATO ES EL QUE ENTRA EN LA CARPETA TEST O SIS HAY QUE CAMBIARLO SI INTERESA
    protected String environment = "";

    leeroyjenkins elements = new leeroyjenkins();//dinamic
    Data data = new Data();
    TestDriver driver;
    FunctionsJenkins FunctionsJ = new FunctionsJenkins(data);
    String[] camposnombre;
    String[] camposjira;
    String[] camposmaven;

    /**
     * This method do the necesary pause for the charges of the Jenkins
     * @param sec how many seconds want to stop the code
     */
    public void timer(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (Exception e) {
            e.getMessage();
            System.out.printf("time out exception");
        }
    }


    protected String getElements(String key) {
        String value = this.elements.getElements().get(key);
        return value;
    }

    /**
     * method for go to the  application of jenkins. Also the method put the login and uses another methods for go to the
     * corresponding package .
     * @return true if is ok/false if it has a problem
     */
    protected boolean login() {
      where = " on  LOGIN JENKINS";
        if (!FunctionsJ.insertInput(driver, new String[]{"login_i_user", getElements("login_i_user")},
                "user", user, where)) {
            return true;
        }

        if (!FunctionsJ.insertInput(driver, new String[]{"login_i_pass", getElements("login_i_pass")},
                "pass", pass, where)) {
            return false;
        }

        if (!FunctionsJ.simpleClick(driver,
                new String[]{"login_b_enter", getElements("login_b_enter")}, //element to click
                where)) {
            return false;
        }

        return true;


    }

    /**
     * constructor of this class. This constructor prepare the Strings  transforming to array with a
     * split
     * @param driver TestDriver for  manage the navigator
     * @param nombretest String with all data of  the  name tests
     * @param jiratest String with all data of the jira data
     * @param maventest String with the data of  if  the test is in maven or not
     */
    public ManagerJenkins(TestDriver driver, String[] nombretest, String[] jiratest, String[] maventest, String environment) {
        this.driver = driver;
        camposnombre = nombretest;
        camposjira = jiratest;
        camposmaven = maventest;
        camposmaven = maventest;
        this.environment =environment;
    }

    /**
     * Method for create all the test in Jenkins.
     * This method will create a  loop using the size of the test array ( camposnombre ),
     * it will generate  the task, will insert the Corresponding parameters
     * @return true if is ok/false if it has a problem
     */
    private boolean process() {

        goToScreen();

        FunctionsJ.zoomOut(driver,5);

        for (int i = 0; i < camposnombre.length; i++) {
                System.out.println("generate: " + camposnombre[i] + " in Jenkins");
                timer(3);
                if (!createtask(i)){return false;}
                if (!detailstask(i)){return false;}

                System.out.println(camposnombre[i]+" status : SUCCESSFUL");
                timer(3);
                if (!FunctionsJ.simpleClick(driver,
                    new String[]{"tasks_b_up", getElements("tasks_b_up")}, //element to click
                    "on GO BACK ")) {
                return false;
            }
            timer(3);
        }



        return true;
    }

    /**
     * Method for go to the path of test or sis ( specified in the variable  environment)
     * @see FunctionsJenkins
     * @return true if is ok/false if it has a problem
     */
    private boolean goToScreen(){
        String where = "on GO TO " + environment;

        timer(4);
        if (!FunctionsJ.simpleClick(driver,
                new String[]{"mainmenu_b_hotelbeds", getElements("mainmenu_b_hotelbeds")}, //element to click
                where)) {
            return false;
        }
        timer(4);
        //se abre la carpeta correspondiente
        if (environment.toLowerCase().equals("test")) {

            if (!FunctionsJ.simpleClick(driver,
                    new String[]{"mainmenu_b_test", getElements("mainmenu_b_test")}, //element to click
                    where)) {
                return false;
            }}else{
            if (!FunctionsJ.simpleClick(driver,
                    new String[]{"mainmenu_b_sis", getElements("mainmenu_b_sis")}, //element to click
                    where)) {
                return false;}
        }
        timer(4);
        return true;


}

    /**
     * Method for create a new task with the corresponding name
     * @param i number of iterations performed
     * @see FunctionsJenkins
     * @return true if is ok/false if it has a problem
     */
    private  boolean createtask(int i){
        where="on GENERATE TASK ";
        if (!FunctionsJ.simpleClick(driver,
                new String[]{"tasks_b_new_item", getElements("tasks_b_new_item")}, //element to click
                where)) { return false;}
        timer(2);
        if (!FunctionsJ.insertInput(driver, new String[]{"tasks_i_name", getElements("tasks_i_name")},
                camposnombre[i],   camposnombre[i], where)) { return false;}

        if (!FunctionsJ.simpleClick(driver,
                new String[]{"tasks_b_free_projects", getElements("tasks_b_free_projects")}, //element to click
                where)) { return false;}
        timer(1);
        if (!FunctionsJ.simpleClick(driver,
                new String[]{"tasks_b_ok", getElements("tasks_b_ok")}, //element to click
                where)) { return false;}
        timer(2);

        return true;
}

    /**
     * Method for activate the textArea: "Run a Windows command" and insert the parameter  inside
     * @param i number of iterations performed
     * @see FunctionsJenkins
     * @return true if is ok/false if it has a problem
     */
    private boolean detailstask(int i){
        where="on DETAILS TASK";
        String parameters=  mavencase(i);


        timer(2);
        if (!FunctionsJ.checkboxValue(driver,
                getElements("tasks_checkbox_add_timestamps"),"checktrue",true, where)){return false;}
        timer(3);
        if (!FunctionsJ.simpleClick(driver,
                new String[]{"tasks_select_new_task", getElements("tasks_select_new_task")}, //element to click
                where)) {
            return false;
        }
        timer(3);
        if (!FunctionsJ.simpleClick(driver,
                new String[]{"tasks_select_windows_tasks", getElements("tasks_select_windows_tasks")}, //element to click
                where)) {
            return false;
        }
        timer(2);
        if (!FunctionsJ.insertInput(driver, new String[]{"tasks_i_windows_tasks", getElements("tasks_i_windows_tasks")},
                "parameters"+i, parameters, where)) {
            return false;
        }
        timer(4);
        if (!FunctionsJ.simpleClick(driver,
                new String[]{"tasks_b_save", getElements("tasks_b_save")}, //element to click
                where)) {
            return false;
        }
        timer(3);

        return true;
    }

    /**
     * Method for create the parameter line, if the result is YES in the camposmaven[i] this create the line
     * with the adf New artifact (ProjectAutotest.jar) if the result is NO  in the camposmaven[i]  this chreate the line
     * with the adf-old  artifact (Autotest.jar). if is none of the two  the method create a imput dialog message
     * for later manual review.
     * @param i number of iterations performed
     * @return the parameter line
     */
    public String  mavencase(int i){
        String parameters="";
        switch (camposmaven[i].toLowerCase()){
            case "yes":
                parameters= "java -jar c:"+'\\'+"ProjectAutotest.jar -e  "+environment+"  -t  "+camposnombre[i]+"  -i "+camposjira[i]+"";
                break;
            case "no":
                parameters= "java -jar c:"+'\\'+"Autotest.jar -e  "+environment+"  -t  "+camposnombre[i]+"  -i "+camposjira[i]+"";
                break;
                //ESTOS CASOS NO FUNCIONAN bien TO DO
            case "wip":
                String data=JOptionPane.showInputDialog("este campo esta wip, "+camposnombre[i]+" se ha migrado en maven? s/n");
                if (data.toLowerCase().equals("s")){
                    parameters= "java -jar c:"+'\\'+"ProjectAutotest.jar -e  "+environment+"  -t  "+camposnombre[i]+"  -i "+camposjira[i]+"";
                }else{ parameters= "java -jar c:"+'\\'+"Autotest.jar -e  "+environment+"  -t  "+camposnombre[i]+"  -i "+camposjira[i]+"";
                }
                 break;
                default:
                    String data2=JOptionPane.showInputDialog("este campo es desconocido, "+camposnombre[i]+ " se ha migrado en maven? s/n");
                    if (data2.toLowerCase().equals("s")){
                        parameters= "java -jar c:"+'\\'+"ProjectAutotest.jar -e  "+environment+"  -t  "+camposnombre[i]+"  -i "+camposjira[i]+"";
                    }else{ parameters= "java -jar c:"+'\\'+"Autotest.jar -e  "+environment+"  -t  "+camposnombre[i]+"  -i "+camposjira[i]+"";
                    }
                    break;
        }
        System.out.println("parameter: ( "+parameters+" )");
        return parameters;
    }

    /**
     *  this method  obtain by Thread starting all the process of the application
     */
    @Override
    public void run() {
        super.run();
        driver.openFFDriver();
        timer(3);
        driver.getDriver().get( driver.getDriverdetails().getUrl());
        timer(3);
        driver.getDriver().manage().timeouts().implicitlyWait(2L, TimeUnit.SECONDS);
        timer(3);
        //start jenkins
        login();
        timer(4);
        //create all the test in jenkins
        process();
        //only close if is OK all because if not okay you can see the problem and solve ( it's a ALFA VERSION)
        driver.closeDriver();

    }
}