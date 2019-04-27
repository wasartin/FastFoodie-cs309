package edu.iastate.graysonc.fastfood;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Date;

import edu.iastate.graysonc.fastfood.database.entities.Food;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class FoodTests {

    @Mock
    Food food;

    @Test
    public void testConstructor(){
        food = mock(Food.class);
        doReturn(new Date()).when(food).getLastRefresh();
        food = new Food(2,"a",2,1,3,4,5);
        Mockito.validateMockitoUsage();
    }
}
