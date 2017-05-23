import java.util.HashMap;
import java.util.Map;

/**
 * Created by pvalls on 10/04/2017.
 */
public class leeroyjenkins {
    /**
     * Clase que contendrá los path estaticos que se usarán
     * para poder generar automaticamente los test dentro de jenkins
     * se guardan en un mapa
     */
    Map<String, String> elements = new HashMap<>();

    public leeroyjenkins() {
        setElements();
    }

    public Map<String, String> getElements() {
        return elements;
    }


    public void setElements() {

        //login
        elements.put("login_i_user","//*[@id='j_username']");
        elements.put("login_i_pass","//*[@id='main-panel']/div/form/table/tbody/tr[2]/td[2]/input");
        elements.put("login_b_enter","//*[@id='yui-gen1-button']");
        //main menu
        elements.put("mainmenu_b_hotelbeds",".//*[@id='job_Hotelbeds']/td[3]/a");
        elements.put("mainmenu_b_sis",".//*[@id='job_SIS']/td[3]/a");
        elements.put("mainmenu_b_test",".//*[@id='job_TEST']/td[3]/a");

        //tasks
        elements.put("tasks_b_new_item",".//*[@id='tasks']/div[4]/a[2]");
        //generate task
        elements.put("tasks_i_name",".//*[@id='name']");
        elements.put("tasks_b_free_projects",".//*[@id='j-add-item-type-standalone-projects']/ul/li[1]");
        elements.put("tasks_b_ok",".//*[@id='ok-button']");
        //details tasks
        //////////////////////IMPORTANTE//////////////////////////////
        //LOS XPATH SE GENERAN DINAMICAMENTE ASI QUE HAY QUE COMPROBAR QUE  ESTEN CORRECTOS ANTES DE USARLO
        elements.put("tasks_checkbox_add_timestamps",".//*[@id='cb26']");
        elements.put("tasks_select_new_task",".//*[@id='yui-gen17-button']");
        elements.put("tasks_select_windows_tasks",".//*[@id='yui-gen42']/a");
        elements.put("tasks_i_windows_tasks",".//*[@id='yui-gen49']/table/tbody/tr[3]/td[3]/textarea");
        elements.put("tasks_b_save",".//*[@id='yui-gen37-button']");

        //GO BACK
        elements.put("tasks_b_up",".//*[@id='tasks']/div[1]/a[2]");
    }
}
