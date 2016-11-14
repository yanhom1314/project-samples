package demo.data;

import org.apache.geode.DataSerializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class RadiusJava implements DataSerializable {
    private String un;
    private String ip;
    private String area;

    public RadiusJava() {
    }

    public RadiusJava(String un, String ip, String area) {
        this.un = un;
        this.ip = ip;
        this.area = area;
    }

    @Override
    public void toData(DataOutput out) throws IOException {
        out.writeUTF(this.un);
        out.writeUTF(this.ip);
        out.writeUTF(this.un);
    }

    @Override
    public void fromData(DataInput in) throws IOException, ClassNotFoundException {
        this.un = in.readUTF();
        this.ip = in.readUTF();
        this.area = in.readUTF();
    }
}
