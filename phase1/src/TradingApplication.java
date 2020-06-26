import java.io.IOException;

public class TradingApplication {

    public static void main(String[] args) {
        TradingSystem tradingSystem = new TradingSystem();
        try {
            tradingSystem.run();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Caught an Exception");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
