package javaxjparsespeed;

import Domain.Person;

public class JavaXJParseSpeed {

    public static void main(String[] args) {
        
        ParseTests pTests = new ParseTests();
        
        Person p = new Person(100, 100);

//        pTests.serializeObject(p);
//        pTests.jsonParserAsGson(p);
//        pTests.jsonParserAsJackson(p);
        pTests.jsonParserAsBoon(p);
//        pTests.xmlParserAsXpp3XStream(p);
//        pTests.xmlParserAsDomDriverXStream(p);
//        pTests.xmlParserAsStaxDriverXStream(p);

        System.err.println("yea");
    }

}
