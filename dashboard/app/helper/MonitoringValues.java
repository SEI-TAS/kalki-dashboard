package helper;

public class MonitoringValues {
    private int baud;
    private String port;

    public MonitoringValues() {
        this.baud = 0;
        this.port = "";
    }

    public MonitoringValues(int baud, String port){
        this.baud = baud;
        this.port = port;
    }

    public int getBaud(){
        return this.baud;
    }
    public void setBaud(int baud){
        this.baud = baud;
    }

    public String getPort(){
        return this.port;
    }
    public void setPort(String port){
        this.port = port;
    }
}