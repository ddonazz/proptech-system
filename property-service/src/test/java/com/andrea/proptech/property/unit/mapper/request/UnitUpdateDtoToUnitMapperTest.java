package com.andrea.proptech.property.unit.mapper.request;

import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitType;
import com.andrea.proptech.property.unit.web.dto.request.UnitUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UnitUpdateDtoToUnitMapperTest {

    @InjectMocks
    private UnitUpdateDtoToUnitMapper mapper;

    @Test
    @DisplayName("Should update existing unit with new values")
    void apply_updatesValues() {
        // Given
        Unit existingUnit = new Unit();
        existingUnit.setInternalName("Old Name");
        existingUnit.setTotalAreaMq(50.0);

        UnitUpdateDto dto = new UnitUpdateDto(
                "New Name", "C01", UnitType.APARTMENT, "1", "1A",
                100.0, 90.0, 3, 2, 1, 4, "REG-123", Collections.emptySet()
        );

        // When
        Unit result = mapper.apply(dto, existingUnit);

        // Then
        assertEquals("New Name", result.getInternalName());
        assertEquals(100.0, result.getTotalAreaMq());
        assertEquals(UnitType.APARTMENT, result.getType());
    }
}