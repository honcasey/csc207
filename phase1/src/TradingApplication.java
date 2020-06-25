import java.io.IOException;

public class TradingApplication {
    public static void main(String[] args) {
        TradingSystem tradingSystem = new TradingSystem();
        try {
            tradingSystem.run();
        } catch (IOException e) {
            System.out.println("Caught IOException");
            e.printStackTrace();
        }
    }
}
