package com.epam.training.bmi.calculator.mapper;

import com.epam.training.bmi.calculator.BodyMassIndexCalculator;
import com.epam.training.bmi.calculator.data.BodyMassIndexCategory;
import com.epam.training.bmi.calculator.data.BodyMassIndexCategoryRange;
import com.epam.training.bmi.calculator.data.UnitType;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Maps a calculated body mass index for a BodyMassIndexCategory.
 * @author Szabó Tamás
 */
public class BodyMassIndexCategoryMapper {

    private static final Map<BodyMassIndexCategoryRange, BodyMassIndexCategory> RANGE_CATEGORY_MAP = ImmutableMap.<BodyMassIndexCategoryRange, BodyMassIndexCategory>builder()
            .put(new BodyMassIndexCategoryRange(0, 16.0), BodyMassIndexCategory.SEVERE_THINNESS)
            .put(new BodyMassIndexCategoryRange(16.0, 17.0), BodyMassIndexCategory.MODERATE_THINNESS)
            .put(new BodyMassIndexCategoryRange(17.0, 18.5), BodyMassIndexCategory.MILD_THINNESS)
            .put(new BodyMassIndexCategoryRange(18.5, 25.0), BodyMassIndexCategory.NORMAL)
            .put(new BodyMassIndexCategoryRange(25.0, 30.0), BodyMassIndexCategory.OVERWEIGHT)
            .put(new BodyMassIndexCategoryRange(30.0, 35.0), BodyMassIndexCategory.OBESE_CLASS_I)
            .put(new BodyMassIndexCategoryRange(35.0, 40.0), BodyMassIndexCategory.OBESE_CLASS_II)
            .put(new BodyMassIndexCategoryRange(40.0, Double.MAX_VALUE), BodyMassIndexCategory.OBESE_CLASS_III)
            .build();

    private BodyMassIndexCalculator bodyMassIndexCalculator = new BodyMassIndexCalculator();

    public BodyMassIndexCategory map(double height, double mass, UnitType unitType) {
        double bodyMassIndex = bodyMassIndexCalculator.calculate(height, mass, unitType);
        return RANGE_CATEGORY_MAP.entrySet().stream()
                .filter(entry -> isInRangeOf(bodyMassIndex, entry.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(BodyMassIndexCategory.INVALID_CATEGORY);
    }

    private boolean isInRangeOf(double bodyMassIndex, BodyMassIndexCategoryRange range) {
        return bodyMassIndex > range.getMinValue() && bodyMassIndex < range.getMaxValue();
    }

    //tesztelésre van ez a annotáció?
    
    @VisibleForTesting
    void setBodyMassIndexCalculator(BodyMassIndexCalculator bodyMassIndexCalculator) {
        this.bodyMassIndexCalculator = bodyMassIndexCalculator;
    }
}
