package com.epam.training.bmi.calculator.mapper;

import com.epam.training.bmi.calculator.BodyMassIndexCalculator;
import com.epam.training.bmi.calculator.data.BodyMassIndexCategory;
import com.epam.training.bmi.calculator.data.UnitType;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * Created by tomi9 on 11/6/2017.
 */
public class BodyMassIndexCategoryMapperTest {

    private static final double ANY_HEIGHT = 10.0;
    private static final double ANY_MASS = 12.0;
    private static final UnitType ANY_CATEGORY = UnitType.SI;

    private BodyMassIndexCategoryMapper bodyMassIndexCategoryMapper;

    @Mock
    private BodyMassIndexCalculator bodyMassIndexCalculator;

    @DataProvider
    public static Object[][] dataProvider() {
        return new Object[][]{
            {16.5, BodyMassIndexCategory.MODERATE_THINNESS},
            {-1.0, BodyMassIndexCategory.INVALID_CATEGORY},
            {50.0, BodyMassIndexCategory.OBESE_CLASS_III},
            {1.0, BodyMassIndexCategory.SEVERE_THINNESS}
        };
    }

    @BeforeMethod
    public void init() {
        initMocks(this);
        bodyMassIndexCategoryMapper = new BodyMassIndexCategoryMapper();
        bodyMassIndexCategoryMapper.setBodyMassIndexCalculator(bodyMassIndexCalculator);
    }

    @Test(dataProvider = "dataProvider")
    public void shouldMapBodyMassIndexAccordingly(double bodyMassIndex, BodyMassIndexCategory expectedCategory) {
        when(bodyMassIndexCalculator.calculate(ANY_HEIGHT, ANY_MASS, ANY_CATEGORY)).thenReturn(bodyMassIndex);
        BodyMassIndexCategory actualBodyMassIndexCategory = bodyMassIndexCategoryMapper.map(ANY_HEIGHT, ANY_MASS, ANY_CATEGORY);
        assertEquals(actualBodyMassIndexCategory, expectedCategory);
    }

}