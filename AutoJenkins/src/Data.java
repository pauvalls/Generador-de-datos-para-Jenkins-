import core.FileGestor.DataHarvester;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pvalls on 10/04/2017.
 */
public class Data {
        /**
         * Campo data que generara dinamicamente los datos en un mapa
         */

        Map<String, String> data = new HashMap<>();

        public Data() {

            setData();
        }

        public Map<String, String> getData() {
                return data;
        }

        public void setData() {
                setDefaultData();

        }

        public void setDefaultData() {
        }


}
