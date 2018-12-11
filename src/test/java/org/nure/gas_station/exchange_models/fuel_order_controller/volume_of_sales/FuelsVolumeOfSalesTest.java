package org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FuelsVolumeOfSalesTest {

    private FuelsVolumeOfSales fuelsVolumeOfSales;
    private FuelVolumeOfSales fuelVolumeOfSales;
    private FuelVolumeOfSales secondFuelVolumeOfSales;
    private final float volumeOfSales = 300;
    private final float secondVolumeOfSales = 200;

    @Before
    public void setFuelVolumeOfSales() {
        this.fuelVolumeOfSales = new FuelVolumeOfSales("95", volumeOfSales);
        this.secondFuelVolumeOfSales = new FuelVolumeOfSales("92", secondVolumeOfSales);
        this.fuelsVolumeOfSales = new FuelsVolumeOfSales();
    }

    @Test
    public void testSetOverallVolumeOfSalesShouldSetOverallVolumeOfSalesToSumOfAllVolumesOfSalesOfEachFuel() {
        fuelsVolumeOfSales.setOverallVolumeOfSales(Arrays.asList(fuelVolumeOfSales, secondFuelVolumeOfSales));
        assertEquals(volumeOfSales + secondVolumeOfSales, fuelsVolumeOfSales.getOverallVolumeOfSales(), 0);
    }
}
