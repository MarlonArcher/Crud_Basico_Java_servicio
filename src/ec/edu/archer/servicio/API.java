package ec.edu.archer.servicio;

import ec.edu.archer.entidades.Estudiante;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

public class API {

    public ArrayList<Estudiante> GETDEBER() {
        ArrayList<Estudiante> estudintes = new ArrayList<>();
        String api = "http://ejeplo.com/Quinto/api.php";
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .GET()
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            JSONArray jSONArray = new JSONArray(respuesta.body());
            JSONObject jSONObject;
            Object data[] = new Object[5];
            for (int i = 0; i < jSONArray.length(); i++) {
                jSONObject = jSONArray.getJSONObject(i);
                String cedula = jSONObject.get("cedula").toString();
                String nombre = jSONObject.get("nombre").toString();
                String apellido = jSONObject.get("apellido").toString();
                String direccion = jSONObject.get("direccion").toString();
                String telefono = jSONObject.get("telefono").toString();
                Estudiante e = new Estudiante(cedula, nombre, apellido, direccion, telefono);
                estudintes.add(e);
            }
            return estudintes;
        } catch (IOException | InterruptedException | JSONException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            return estudintes;
        }
    }

    public ArrayList<Estudiante> GET() {
        ArrayList<Estudiante> estudintes = new ArrayList<>();
        try {
            URL url = new URL("http://ejeplo.com/Quinto/api.php");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            StringBuilder datos = new StringBuilder();
            http.setRequestMethod("GET");
            http.connect();
            int response = http.getResponseCode();
            if (response == 200) {
                try (Scanner sc = new Scanner(http.getInputStream())) {
                    while (sc.hasNext()) {
                        datos.append(sc.nextLine());
                    }
                }
            } else {
                System.out.println("Error");
            }
            JSONArray json = new JSONArray(datos.toString());
            JSONObject objeto;
            for (int i = 0; i < json.length(); i++) {
                objeto = json.getJSONObject(i);
                String cedula = objeto.getString("cedula");
                String nombre = objeto.getString("nombre");
                String apellido = objeto.getString("apellido");
                String direccion = objeto.getString("direccion");
                String telefono = objeto.getString("telefono");
                Estudiante e1 = new Estudiante(cedula, nombre, apellido, direccion, telefono);
                estudintes.add(e1);
            }
        } catch (IOException | JSONException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return estudintes;
    }

    public void POST(Estudiante est) {
        try {
            CloseableHttpClient cliente = HttpClientBuilder.create().build();
            String url = ("http://ejeplo.com/Quinto/api.php");
            HttpPost post = new HttpPost(url);
            ArrayList<BasicNameValuePair> parametros = new ArrayList<>();
            parametros.add((new BasicNameValuePair("cedula", est.getCedula())));
            parametros.add((new BasicNameValuePair("nombre", est.getNombre())));
            parametros.add((new BasicNameValuePair("apellido", est.getApellido())));
            parametros.add((new BasicNameValuePair("direccion", est.getDireccion())));
            parametros.add((new BasicNameValuePair("telefono", est.getTelefono())));
            post.setEntity(new UrlEncodedFormEntity(parametros));
            Object a = cliente.execute(post);
            if (a != null) {
                JOptionPane.showMessageDialog(null, "OK");
            }
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void UPDATE(Estudiante est) {
        try {
            String url = "http://ejeplo.com/Quinto/api.php";
            String urlParametros
                    = "?cedula=" + est.getCedula()
                    + "&nombre=" + est.getNombre()
                    + "&apellido=" + est.getApellido()
                    + "&direccion=" + est.getDireccion()
                    + "&telefono=" + est.getTelefono();
            URL urll = new URL(url + urlParametros);
            HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
            conn.setRequestMethod("PUT");
            int respuesta = conn.getResponseCode();
            if (respuesta == 200) {
                JOptionPane.showMessageDialog(null, "OK");
            } else {
                JOptionPane.showMessageDialog(null, "Fallo");

            }
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void DELETE(String cedula) {
        try {
            String api = "http://ejeplo.com/Quinto/api.php";
            URL url = new URL(api + "?cedula=" + cedula);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            int respuesta = con.getResponseCode();
            if (respuesta == 200) {
                JOptionPane.showMessageDialog(null, "OK");
            } else {
                JOptionPane.showMessageDialog(null, "Fallo");

            }
        } catch (Exception e) {

        }
    }
}
