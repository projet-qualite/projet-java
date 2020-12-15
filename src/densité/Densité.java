
package densité;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.*;
public class Densité {
 //ajout commentaire
 //hey
   
    private static MqttClient clientDensitePUB;
    private static MqttClient clientDensiteSUB;
    private static MqttMessage clientMessage;
    private static int compteur = 0;
    
    public static void main(String[] args) throws MqttException, InterruptedException {
        
        clientDensitePUB = new MqttClient("tcp://localhost:1883","clientDensitePUB");
        clientDensiteSUB = new MqttClient("tcp://localhost:1883","clientDensiteSUB");
        clientMessage = new MqttMessage();
        
        clientDensiteSUB.setCallback(new MqttCallback(){
            @Override
            public void connectionLost(Throwable thrwbl) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                
                int nb = Integer.valueOf(message.toString());
                compteur += nb;
                
                System.out.println("Nb: "+ compteur);
            
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        
        
        int i = 0;
        
        clientDensiteSUB.connect();
        clientDensitePUB.connect();
        
        clientDensiteSUB.subscribe("voiture");
        
        while(i<5000)
        {
            Thread.sleep(5000);
            String val = Integer.toString(compteur);
            clientMessage.setPayload(val.getBytes());
            
            clientDensitePUB.publish("densité", clientMessage);
            compteur = 0;
            
        }
        
        
    }
    
}
