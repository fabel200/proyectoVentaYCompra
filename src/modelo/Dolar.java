
package modelo;

    
    import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import org.json.JSONObject;
//import org.json.JSONObject; // añade org.json o usa Gson
public class Dolar {
   
    String precioDolar;

    public Dolar(String precioDolar) {
        this.precioDolar = precioDolar;
    }

    public String getPrecioDolar() {
        return precioDolar;
    }

    public void setPrecioDolar(String precioDolar) {
        this.precioDolar = precioDolar;
    }

    
    public static double obtenerDolarVe() throws Exception {
        
        String urlStr = "https://ve.dolarapi.com/api/dolar";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        int status = conn.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Error al obtener tipo de cambio, HTTP: " + status);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        // parse JSON. Ejemplo de estructura: {"dolar":{"promedio":223.45, ...}, ...}
      //  JSONObject json = new JSONObject(content.toString());
        // Este path depende de la API. Ajusta según la respuesta real.
      //  double valorPromedio = json.getJSONObject("dolar").getDouble("promedio");
     //   return valorPromedio;
        return 0;
    }
    
     public void ActualizarValorDolar() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            String resultado = "";

            @Override
            protected Void doInBackground() {

                try {
                    // String api = "https://bcv-api.rafnixg.dev/rates/";
                   String api = "https://ve.dolarapi.com/v1/dolares/oficial";
                    URL url = new URL(api);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);

                    }
                    in.close();

                    JSONObject obj = new JSONObject(response.toString());

                    double precio = obj.getDouble("promedio");
                    precioDolar = "Dólar BCV: " + precio;

                } catch (Exception e) {
                    precioDolar = "Error: " + e.getMessage();
                }
                return null;
            }

        };
        worker.execute();
    }
}


