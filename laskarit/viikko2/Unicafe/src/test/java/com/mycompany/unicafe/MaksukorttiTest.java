package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    //Test that the card has value it was given on creation
    @Test
    public void kortinAlkusaldoOikein(){
        assertEquals("saldo: 0.10", kortti.toString());
       
    }
    
    //Test that card can be charged and that the charged value is correct
    @Test
    public void kortinLatausToimiiOikein(){
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }
    
    //Test that the card's value can't go into negatives
    @Test
    public void saldoVaheneeJosRahaaRiittavasti(){
        kortti.lataaRahaa(240);
        kortti.otaRahaa(120);
        assertEquals("saldo: 1.30", kortti.toString());
    }
    
    //Test that card's value won't change if there isn't enough value on it
    @Test
    public void saldoEiMuutuJosEiRahaa(){
        kortti.otaRahaa(200);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    //returns true if card has enough value, false otherwise
    @Test
    public void palauttaaTrueJosRahaRiittiMuutenFalse(){
        assertTrue(kortti.otaRahaa(5));
        assertFalse(kortti.otaRahaa(153));
    }
    
    //Test saldo()
    @Test
    public void saldoOikein(){
        assertEquals(kortti.saldo(), 10);
    }
    

}
