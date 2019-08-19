package stopandwait;
/****@author MTG */
public class StopAndWait {

    public static final int PACKAGES=10;

    public static void main(String[] args) {
        Sender s1=new Sender();
        Receiver r1=new Receiver();

        s1.setup(r1, PACKAGES);
        r1.setup(s1);

        s1.send();
    }
   }
public class Sender {
    private static int packages;
    private static Receiver r1;
    private int currentPack=1;
    private static final int TIMEOUT=5;


    public void setup(Receiver r1, int packages) {
        this.packages=packages;
        this.r1=r1;
    }

    void send() {
        if(currentPack>packages) System.out.println("S: "+packages+" paket başarıyla gönderildi.");
        else {
            System.out.println("S: Paket"+currentPack+" gönderildi               P"+currentPack+"|\\  |\n                                     | \\ |\n                                     |  \\|");
            r1.receivePack(currentPack++);
        }
    }

    void receiveAck(int acknowledgePack) {
        //System.out.println("S: Paket"+acknowledgePack+" acknowledge alındı");
        send();
    }

    void noAck() {
        for(int i=1;i<TIMEOUT;i++) {
            if(i==3) System.out.println("S: Acknowledge ulaşmadı    Timeout:"+i+" x   |");
            else System.out.println("                           Timeout:"+i+" |   |");
        }
        currentPack--;
        send();
    }
}
import java.util.Random;
public class Receiver {
    private static int wndwSize=4;
    private static Sender s1;
    private int nextPack=1;
    private final int ERRORRATE=10; //percentage of error
    Random rand=new Random();

    public void setup(Sender s1) {
        this.s1=s1;
    }

    void receivePack(int receivedPack) {
        int random=rand.nextInt(100)+1;
        if(random>100-ERRORRATE) {
            System.out.println("                                     |   x R: Paket ulaşmadı");
            s1.noAck();
        }
        else {
            //System.out.println("R: Paket"+receivedPack+" alındı.");
            sendAck(receivedPack);
        }
    }

    void sendAck(int receivedPack) {
        System.out.println("                                     |  /| R: Paket"+receivedPack+" acknowledge gönderildi\n                                     | / |\n                                     |/  |");
        s1.receiveAck(receivedPack);
    }
}
