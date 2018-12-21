/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import silkspinapp.silkspindataobjects.DataSpec;

/**
 *
 * @author tvierine
 */
public class DataSpecTest {

    DataSpec d;
    GregorianCalendar date = new GregorianCalendar();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");      //determines the displayed date
    Date did;
    String dates;

    public DataSpecTest() {
    }

    @Before
    public void setUp() {
        d = new DataSpec(20.0, "kala");
        did = new Date();
        dates = sdf.format(did);
    }

    @Test
    public void getMoWorksCorrect() {
        int i = date.get(Calendar.MONTH) + 1;
        int k = d.getMo();
        assertEquals(k, i);
    }

    @Test
    public void toStringCalledCorrect() {
        assertEquals(d.toString(), "Date of entry: " + dates + " AMOUNT: 20.0€ TYPE OF EXPENSE: kala");
    }

    @Test
    public void valueCorrectlyAssgined() {
        assertEquals(d.getAmount() + "", 20.0 + "");
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
