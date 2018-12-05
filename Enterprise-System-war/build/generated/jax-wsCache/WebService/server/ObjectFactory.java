
package server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the server package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IOException_QNAME = new QName("http://server/", "IOException");
    private final static QName _ProtocolException_QNAME = new QName("http://server/", "ProtocolException");
    private final static QName _GoogleMapsDistance_QNAME = new QName("http://server/", "googleMapsDistance");
    private final static QName _GoogleMapsDistanceResponse_QNAME = new QName("http://server/", "googleMapsDistanceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link ProtocolException }
     * 
     */
    public ProtocolException createProtocolException() {
        return new ProtocolException();
    }

    /**
     * Create an instance of {@link GoogleMapsDistance }
     * 
     */
    public GoogleMapsDistance createGoogleMapsDistance() {
        return new GoogleMapsDistance();
    }

    /**
     * Create an instance of {@link GoogleMapsDistanceResponse }
     * 
     */
    public GoogleMapsDistanceResponse createGoogleMapsDistanceResponse() {
        return new GoogleMapsDistanceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "ProtocolException")
    public JAXBElement<ProtocolException> createProtocolException(ProtocolException value) {
        return new JAXBElement<ProtocolException>(_ProtocolException_QNAME, ProtocolException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GoogleMapsDistance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "googleMapsDistance")
    public JAXBElement<GoogleMapsDistance> createGoogleMapsDistance(GoogleMapsDistance value) {
        return new JAXBElement<GoogleMapsDistance>(_GoogleMapsDistance_QNAME, GoogleMapsDistance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GoogleMapsDistanceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "googleMapsDistanceResponse")
    public JAXBElement<GoogleMapsDistanceResponse> createGoogleMapsDistanceResponse(GoogleMapsDistanceResponse value) {
        return new JAXBElement<GoogleMapsDistanceResponse>(_GoogleMapsDistanceResponse_QNAME, GoogleMapsDistanceResponse.class, null, value);
    }

}
