//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.17 at 10:47:52 PM MSK 
//


package ru.myt.integration.rsclient.obj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}iata"/>
 *         &lt;element ref="{}icao"/>
 *         &lt;element ref="{}sirena"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "iata",
    "icao",
    "sirena"
})
@XmlRootElement(name = "codes")
public class Codes {

    @XmlElement(required = true)
    protected Iata iata;
    @XmlElement(required = true)
    protected Icao icao;
    @XmlElement(required = true)
    protected Sirena sirena;

    /**
     * Gets the value of the iata property.
     * 
     * @return
     *     possible object is
     *     {@link Iata }
     *     
     */
    public Iata getIata() {
        return iata;
    }

    /**
     * Sets the value of the iata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Iata }
     *     
     */
    public void setIata(Iata value) {
        this.iata = value;
    }

    /**
     * Gets the value of the icao property.
     * 
     * @return
     *     possible object is
     *     {@link Icao }
     *     
     */
    public Icao getIcao() {
        return icao;
    }

    /**
     * Sets the value of the icao property.
     * 
     * @param value
     *     allowed object is
     *     {@link Icao }
     *     
     */
    public void setIcao(Icao value) {
        this.icao = value;
    }

    /**
     * Gets the value of the sirena property.
     * 
     * @return
     *     possible object is
     *     {@link Sirena }
     *     
     */
    public Sirena getSirena() {
        return sirena;
    }

    /**
     * Sets the value of the sirena property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sirena }
     *     
     */
    public void setSirena(Sirena value) {
        this.sirena = value;
    }

}