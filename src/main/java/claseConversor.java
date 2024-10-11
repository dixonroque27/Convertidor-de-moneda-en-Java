import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Scanner;

public class claseConversor {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        try {
            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Analizar el JSON de respuesta
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            // Obtener las tasas de cambio para algunas monedas
            double arsRate = jsonResponse.getAsJsonObject("rates").get("ARS").getAsDouble();
            double brlRate = jsonResponse.getAsJsonObject("rates").get("BRL").getAsDouble();
            double copRate = jsonResponse.getAsJsonObject("rates").get("COP").getAsDouble();

            // Mostrar el menú de monedas
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione la moneda a la que desea convertir desde USD:");
            System.out.println("1. ARS (Peso argentino)");
            System.out.println("2. BRL (Real brasileño)");
            System.out.println("3. COP (Peso colombiano)");

            // Leer la opción del usuario
            int option = scanner.nextInt();

            // Solicitar el monto en USD
            System.out.println("Ingrese la cantidad en USD:");
            double amountInUSD = scanner.nextDouble();

            // Realizar la conversión basada en la opción seleccionada
            double convertedAmount = 0;
            switch (option) {
                case 1:
                    convertedAmount = amountInUSD * arsRate;
                    System.out.println(amountInUSD + " USD equivale a " + convertedAmount + " ARS");
                    break;
                case 2:
                    convertedAmount = amountInUSD * brlRate;
                    System.out.println(amountInUSD + " USD equivale a " + convertedAmount + " BRL");
                    break;
                case 3:
                    convertedAmount = amountInUSD * copRate;
                    System.out.println(amountInUSD + " USD equivale a " + convertedAmount + " COP");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
