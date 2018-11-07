/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tvierine
 */
public class MaksukorttiTest {

    //Defines local variable that can be given value later
    Maksukortti kortti;

    //Creates a new card before a test so there is less copypaste code
    @Before
    public void setUp() {
        kortti = new Maksukortti(10);

    }

    public MaksukorttiTest() {
    }

    @Test
    public void hello() {
    }

    //test whether or not the card can be charged correctly
    //create card with 10 units of value, call card's value and check whether
    //the test card's value is the value it was given on creation
    @Test
    public void konstruktoriAsettaaSaldonOikein() {

        String vastaus = kortti.toString();

        assertEquals("Kortilla on rahaa 10.0 euroa", vastaus);

////alternate way to write above code, without creating a String variable
//    @Test
//public void konstruktoriAsettaaSaldonOikein() {
//    assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());

////Code that should make the test give an error, telling us something is wrong
//    @Test
//    public void konstruktoriAsettaaSaldonOikein() {
//
//        String vastaus = kortti.toString();
//
//        assertEquals("Kortilla on rahaa 1 euroa", vastaus);
    }

////Tests whether or not syoEdullisesti works as intended
    @Test
    public void syoEdullisestiVahentaaSaldoaOikein() {

        kortti.syoEdullisesti();

        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
    }

////Tests whether or not syoMaukkaasti works as intended
    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein() {

        kortti.syoMaukkaasti();

        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
    }

////Makes sure that the card's value can't go negative when using syoEdullisesti()
    @Test
    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {

        for (int i = 0; i < 2; i++) {
            kortti.syoMaukkaasti();
        }
        // nyt kortin saldo on 2
        kortti.syoEdullisesti();

        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }

////Makes sure that the card's value can't go negative when using syoMaukkaasti()
    @Test
    public void syoMaukkaastiEiVieSaldoaNegatiiviseksi() {
        for (int i = 0; i < 2; i++) {
            kortti.syoMaukkaasti();
        }
        //kortin saldo = 2
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }

////Test that the card can be charged with value
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(25);
        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
    }

////Tests that the card can't be charged with over 150 Euros of value
    @Test
    public void kortinSaldoEiYlitaMaksimiarvoa() {
        kortti.lataaRahaa(200);
        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
    }

////Tests that negative values can't be charged on the card
    @Test
    public void kortilleEiVoiLadataNegatiivistaArvoa() {
        kortti.lataaRahaa(-30);
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }

////Tests that 2.5 euros of value allows syoEdullisesti() aka card's value can go to 0
    @Test
    public void syoEdullisestiVoiViedaNollaan() {
        for (int i = 0; i < 3; i++) {
            kortti.syoEdullisesti();
        }
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
    }

////Tests that syoMaukkaasti can make the card's value go to 0
    @Test
    public void syoMaukkaastiVoiViedaNollaan() {
        kortti.lataaRahaa(4);
        //arvo nyt 14 euroa
        for (int i = 0; i < 4; i++) {
            kortti.syoEdullisesti();
        }
        //arvo nyt 4 euroa
        kortti.syoMaukkaasti();
        //arvo pitaisi olla nyt 0
        assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
    }
}
