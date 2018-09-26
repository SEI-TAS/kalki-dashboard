package helper;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NRSerialPort;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SerialReader {
    private NRSerialPort serial;
    private MonitoringValues mv;
    private InputStream inputStream;

    public SerialReader() {
        this.mv = new MonitoringValues(0,"");
    }

    public  SerialReader(MonitoringValues mv) throws Exception {
        this.mv = mv;
        connect();
    }

    public void setInputStream(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public InputStream getInputStream(){
        return this.inputStream;
    }

    public Set<String> getAvailableSerialPorts() {
        return NRSerialPort.getAvailableSerialPorts();
    }

    public void connect() throws Exception{
        this.serial = new NRSerialPort(this.mv.getPort(), this.mv.getBaud());
        this.serial.connect();

        System.out.println("Serial connection status: "+this.serial.isConnected());
        if(!this.serial.isConnected()){
            throw new Exception("Error connecting to serial device");
        }
        this.setInputStream(this.serial.getInputStream());
        DataInputStream ins = new DataInputStream(this.serial.getInputStream());

        System.out.println("Characters available: " + ins.available());

        return;
    }

    public void disconnect() throws IOException {
        this.serial.disconnect();
    }

}