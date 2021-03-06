package javaxjparsespeed;

import Domain.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import org.boon.json.JsonFactory;
import utils.PerformanceTest;

public class ParseTests {

    public int forceHotSpot = 1000;

    public final PerformanceTest jacksonTest = new PerformanceTest("jacksonResults", forceHotSpot);
    public final PerformanceTest serialize64bitStringTest = new PerformanceTest("serialize64bitStringTestResults", forceHotSpot);
    public final PerformanceTest gsonTest = new PerformanceTest("gsonResults", forceHotSpot);

    public final PerformanceTest boonTest = new PerformanceTest("boonTestResults", forceHotSpot);
    public final PerformanceTest XPP3xstreamXML = new PerformanceTest("XPP3xstreamXMLResults", forceHotSpot);
    public final PerformanceTest DOMDriverXstreamXML = new PerformanceTest("DOMDriverXstreamXMLResults", forceHotSpot);
    public final PerformanceTest StaxDriverXstreamXML = new PerformanceTest("StaxDriverXstreamXMLResults", forceHotSpot);

    private static ObjectMapper mapper = new ObjectMapper();

    public void jsonParserAsJackson(Person p) {

        for (int i = 0; i < forceHotSpot; i++) {
            jacksonTest.tagStartTime(0);
            try {

                String bookJson = mapper.writeValueAsString(p);
                Person personReborn = mapper.readValue(bookJson, Person.class);

            } catch (IOException iOException) {
                System.err.println(" error");
            }
            jacksonTest.tagEndTime(0);
            jacksonTest.nextMeasurment();
        }
        jacksonTest.saveMeasurmentsToDisk();
    }

    public void serializeObject(Person p) {

        for (int i = 0; i < forceHotSpot; i++) {
            serialize64bitStringTest.tagStartTime(0);
            try {

                String temp = toString(p);
                Person personReborn = (Person) fromString(temp);

            } catch (Exception iOException) {
                System.err.println("Serializing error");
            }

            serialize64bitStringTest.tagEndTime(0);
            serialize64bitStringTest.nextMeasurment();
        }
        serialize64bitStringTest.saveMeasurmentsToDisk();
    }

    private static Object fromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public void jsonParserAsGson(Person p) {

        Gson gson = new GsonBuilder().create();
        for (int i = 0; i < forceHotSpot; i++) {
            gsonTest.tagStartTime(0);
            String temp = gson.toJson(p);
            if (i == 10000) {
                try {
                    System.err.println("Sleep begin 500");
                    Thread.sleep(20000);
                    System.err.println("Sleep end");

                } catch (Exception ex) {
                    System.err.println("thread error");
                }
            }
            Person personReborn = gson.fromJson(temp, Person.class);
            gsonTest.tagEndTime(0);
            gsonTest.nextMeasurment();
        }
        gsonTest.saveMeasurmentsToDisk();
    }

    public void xmlParserAsXpp3XStream(Person p) {
        XStream xstream = new XStream(new Xpp3Driver());
        for (int i = 0; i < forceHotSpot; i++) {
            XPP3xstreamXML.tagStartTime(0);
            String temp = xstream.toXML(p);
            Person personReborn = (Person) xstream.fromXML(temp);
            XPP3xstreamXML.tagEndTime(0);
            XPP3xstreamXML.nextMeasurment();
        }
        XPP3xstreamXML.saveMeasurmentsToDisk();
    }

    public void jsonParserAsBoon(Person p) {

        org.boon.json.ObjectMapper boonMapper = JsonFactory.create();

        for (int i = 0; i < forceHotSpot; i++) {
            boonTest.tagStartTime(0);
            String temp = boonMapper.writeValueAsString(p);
            Person personReborn = (Person) boonMapper.fromJson(temp, Person.class);

            boonTest.tagEndTime(0);
            boonTest.nextMeasurment();
        }
        boonTest.saveMeasurmentsToDisk();
    }

    public void xmlParserAsDomDriverXStream(Person p) {
        XStream xstream = new XStream(new DomDriver());
        for (int i = 0; i < forceHotSpot; i++) {
            DOMDriverXstreamXML.tagStartTime(0);
            String temp = xstream.toXML(p);
            Person personReborn = (Person) xstream.fromXML(temp);
            DOMDriverXstreamXML.tagEndTime(0);
            DOMDriverXstreamXML.nextMeasurment();
        }
        DOMDriverXstreamXML.saveMeasurmentsToDisk();
    }

    public void xmlParserAsStaxDriverXStream(Person p) {

        XStream xstream = new XStream(new StaxDriver());

        for (int i = 0; i < forceHotSpot; i++) {
            StaxDriverXstreamXML.tagStartTime(0);
            String temp = xstream.toXML(p);
            Person personReborn = (Person) xstream.fromXML(temp);
            StaxDriverXstreamXML.tagEndTime(0);
            StaxDriverXstreamXML.nextMeasurment();
        }
        StaxDriverXstreamXML.saveMeasurmentsToDisk();
    }

}
