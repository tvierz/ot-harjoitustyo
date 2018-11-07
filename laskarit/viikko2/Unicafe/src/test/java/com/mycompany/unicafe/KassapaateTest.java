/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

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
public class KassapaateTest {
    Kassapaate kassa;
    Maksukortti kortti;
    int lounaat;
    int saldo;
    @Before
    public void setUp() {
        kassa = new Kassapaate();                   //luodaan testikassa
        kortti = new Maksukortti(20);               //luodaan testikortti jolla 20 rahaa
        lounaat = 0;
        saldo = 0;
    }
    
    //Tarkistaa alkusaldon oikeaksi = 100000 TEHTAVANANNOSSA SANOTTIIN 1000, MUTTA LÄHDEKOODISSA ERI LUKU?
    @Test
    public void alkusaldoOikein(){
        assertEquals(kassa.kassassaRahaa(), 100000);        //tarkistetaan kassan rahamäärä alussa
    }
    
    //maukkaitaLounaitaMyyty() oikein alussa = 0
    @Test
    public void maukkaitaLounaitaAlussaOikein(){
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 0);        
    }
    
    //edullisiaLounaitaMyyty() oikein alussa = 0
    @Test
    public void edullisiaLounaitaAlussaOikein(){
        assertEquals(kassa.edullisiaLounaitaMyyty(), 0);
    }
    
    //Testaa etta kateismaksu Maukkaalle toimii oikein kun on rahaa
    @Test
    public void kateismaksuMaukkaastiRiittavaKassaSaldo(){
        saldo = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(420);
        //maksaa 420, kassaaan kuitenkin vain 400
        assertEquals(kassa.kassassaRahaa(), saldo+400);        //selkeyden ja toiminnallisuuden varmistamiseksi käyteään lähdekoodin alkusaldoa
        //saldo nousee maukkaan hinnalla
        
    }
    //testaa palautetun vaihtorahan suuruutta
    @Test
    public void kateismaksuMaukkaastiRiittavaKassaVaihtoraha(){
        int vaihtoraha = kassa.syoMaukkaasti(420);
        kassa.syoMaukkaasti(420);
        lounaat = kassa.maukkaitaLounaitaMyyty();
        saldo = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(420);
        //maksaa 420, kassaaan kuitenkin vain 400
        //saldo nousee maukkaan hinnalla
        assertEquals(vaihtoraha, 420-400);//varmistaa että vaihtoraha palautuu oikein
        
    }
    
    @Test
    public void kateismaksuMaukkaastiRiittavaKassaLounaat(){
        int vaihtoraha = kassa.syoMaukkaasti(420);
        lounaat = kassa.maukkaitaLounaitaMyyty();
        saldo = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(420);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), lounaat+1); //varmistaa että lounas myytiin kun ehdot täyttyvät
        
    }
    
    //testaa että liian pienellä käteismaksulla ei voi syödä maukkaasti
    @Test
    public void kateismaksuMaukkaastiLiianVahanLounasMaara(){
        kassa.syoMaukkaasti(420);
        lounaat = kassa.maukkaitaLounaitaMyyty();
        saldo = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(200);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), lounaat);    //varmistaa että lounasta ei myyty ilman maksua
    }
    //testaa että maksu ei mene kassaan
    @Test
    public void kateismaksuMaukkaastiLiianVahanKassaSaldo(){
        kassa.syoMaukkaasti(420);
        lounaat = kassa.maukkaitaLounaitaMyyty();
        saldo = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(200);
        assertEquals(kassa.kassassaRahaa(), saldo);        //varmistaa että liian pieni summa ei mennyt kassaan
    }
    
    
    //testataan että edullisesti maksu toimii
    @Test
    public void kateismaksuEdullisestiRiittavaKassasaldo(){
        saldo = kassa.kassassaRahaa();
        kassa.syoEdullisesti(19045);
        assertEquals(kassa.kassassaRahaa(), saldo+240);//tarkistaa että kassaan menee rahaa
    }
    @Test
    public void kateismaksuEdullisestiRiittavaLounasMaara(){
        lounaat = kassa.edullisiaLounaitaMyyty();
        kassa.syoEdullisesti(19045);
        assertEquals(kassa.edullisiaLounaitaMyyty(), lounaat+1);//tarkistaa että myytyjen lounaiden määrä kasvoi yhdella
    }
    @Test
    public void kateismaksuEdullisestiRiittavaVaihtoraha(){
        int vaihtoraha = kassa.syoEdullisesti(19045);
        
        assertEquals(vaihtoraha, 19045-240);//tarkistaa että vaihtoraha on oikein
    }
    //testataan edullisesti kun liian vähän rahaa
    @Test
    public void kateismaksuEdullisestiLiianVahanKassaSaldo(){
        kassa.syoEdullisesti(0);
        assertEquals(kassa.kassassaRahaa(), 100000);    //tarkistaa kassan rahasumman pysyvän muuttumattomana liian pienellä summalla
        
    }
    @Test
    public void kateismaksuEdullisestiLiianVahanLounasMaara(){
        kassa.syoEdullisesti(0);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 0);//tarkistaa että lounasta ei myyty
        
    }
    
    //testaa korttimaksun edulliselle toimivan oikein
    @Test
    public void korttimaksuEdullisestiRiittavaKorttiSaldo(){
        kortti.lataaRahaa(240);                             //varmistetaan että kortilla on rahaa
        assertTrue(kassa.syoEdullisesti(kortti));           //tarkistaa että kortilla on riittävästi rahaa
        
    }
    @Test
    public void korttimaksuEdullisestiRiittavaKorttiSaldoMuutos(){
        kortti.lataaRahaa(240);                     //varmistetaan että kortilla on rahaa
        saldo = kortti.saldo();                 //kortin saldo alussa
        kassa.syoEdullisesti(kortti);               //Kortin saldo vähenee edullisesti summalla
        assertEquals(kortti.saldo(), saldo-240);      
    }
    @Test
    public void korttimaksuEdullisestiRiittavaLounasMuutos(){
        kortti.lataaRahaa(240);                     //varmistetaan että kortilla on rahaa
        lounaat = kassa.edullisiaLounaitaMyyty();
        kassa.syoEdullisesti(kortti);               //Kortin saldo vähenee edullisesti summalla
        assertEquals(kassa.edullisiaLounaitaMyyty(), lounaat+1);      
    }
    @Test
    public void korttimaksuEdullisestiKassaSaldoEiMuutuLiianPieniKorttiSaldo(){
        saldo = kassa.kassassaRahaa();
        kassa.syoEdullisesti(kortti);
        assertEquals(kassa.kassassaRahaa(), saldo);
    }
    @Test
    public void korttimaksuEdullisestiKassaSaldoEiMuutu(){
        kortti.lataaRahaa(500);
        saldo = kassa.kassassaRahaa();
        kassa.syoEdullisesti(kortti);
        assertEquals(kassa.kassassaRahaa(), saldo);
    }
    
    //Testaa Edullisesti liian pieni saldo kortilla
    @Test
    public void korttimaksuEdullisestiEiRiittavaKorttiSaldo(){//testaa voiko kortin saldolla syödä edullisesti, palauttaa false jos ei, muuten virhe
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    @Test
    public void korttimaksuEdullisestiEiRiittavaLounasMaara(){//testaa ettei lounaiden määrä muutu jos liian pienellä saldolla yritetään ostaa lounas
        lounaat = kassa.edullisiaLounaitaMyyty();
        kassa.syoEdullisesti(kortti);
        assertEquals(kassa.edullisiaLounaitaMyyty(), lounaat);
    }
    @Test
    public void korttimaksuEdullisestiEiRiittavaKorttiSaldoMuutos(){
        saldo = kortti.saldo();                 //kortin saldo alussa
        kassa.syoEdullisesti(kortti);               //Kortin saldon ei piitäisi muuttua
        assertEquals(kortti.saldo(), saldo);    // varmistetaan että saldo ei muuttunut
    }
        //testaa korttimaksun edulliselle toimivan oikein
    @Test
    public void korttimaksuMaukkaastiRiittavaKorttiSaldo(){
        kortti.lataaRahaa(400);                             //varmistetaan että kortilla on rahaa
        assertTrue(kassa.syoMaukkaasti(kortti));           //tarkistaa että kortilla on riittävästi rahaa
        
    }
    @Test
    public void korttimaksuMaukkaastiRiittavaKorttiSaldoMuutos(){
        kortti.lataaRahaa(400);                     //varmistetaan että kortilla on rahaa
        saldo = kortti.saldo();                 //kortin saldo alussa
        kassa.syoMaukkaasti(kortti);               //Kortin saldo vähenee maukkaasti summalla
        assertEquals(kortti.saldo(), saldo-400);      
    }
    @Test
    public void korttimaksuMaukkaastiRiittavaLounasMuutos(){
        kortti.lataaRahaa(400);                     //varmistetaan että kortilla on rahaa
        lounaat = kassa.maukkaitaLounaitaMyyty();
        kassa.syoMaukkaasti(kortti);               //Kortin saldo vähenee maukkaasti summalla
        assertEquals(kassa.maukkaitaLounaitaMyyty(), lounaat+1);      
    }
    @Test
    public void korttimaksuMaukkaastiKassaSaldoEiMuutuLiianPieniKorttiSaldo(){
        saldo = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(kortti);
        assertEquals(kassa.kassassaRahaa(), saldo);
    }
    @Test
    public void korttimaksuMaukkaastiKassaSaldoEiMuutu(){
        kortti.lataaRahaa(500);
        saldo = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(kortti);
        assertEquals(kassa.kassassaRahaa(), saldo);
    }
    
    //Testaa Edullisesti liian pieni saldo kortilla
    @Test
    public void korttimaksuMaukkaastiEiRiittavaKorttiSaldo(){//testaa voiko kortin saldolla syödä maukkaasti, palauttaa false jos ei, muuten virhe
        assertFalse(kassa.syoMaukkaasti(kortti));
    }
    @Test
    public void korttimaksuMaukkaastiEiRiittavaLounasMaara(){//testaa ettei lounaiden määrä muutu jos liian pienellä saldolla yritetään ostaa lounas
        lounaat = kassa.maukkaitaLounaitaMyyty();
        kassa.syoMaukkaasti(kortti);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), lounaat);
    }
    @Test
    public void korttimaksuMaukkaastiEiRiittavaKorttiSaldoMuutos(){
        saldo = kortti.saldo();                 //kortin saldo alussa
        kassa.syoEdullisesti(kortti);               //Kortin saldon ei piitäisi muuttua
        assertEquals(kortti.saldo(), saldo);    // varmistetaan että saldo ei muuttunut
    }

    
    //Testataan kortin lataamista
    @Test
    public void kortinLatausKassaKasvaaKasvamistaansakin(){//testataam että kassaan laitetaan rahaa korttia ladatessa
        saldo = kassa.kassassaRahaa();
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(kassa.kassassaRahaa(), saldo+500);//kassassa alkuperäinen saldo ja kortin lataussumma
    }
    @Test
    public void kortinLatausKortinSaldoKasvaa(){
        saldo = kortti.saldo();
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(kortti.saldo(), saldo+500);//kortilla on alkuperäinen saldo + ladattu summa
    }
    @Test
    public void kortinLatausEiOleNegatiivinen(){
        saldo = kortti.saldo();
        kassa.lataaRahaaKortille(kortti, -10);
        assertEquals(kortti.saldo(), saldo);
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
