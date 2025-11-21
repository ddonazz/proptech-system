package com.andrea.proptech.property.unit.data;

import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.data.PropertyRepository;
import com.andrea.proptech.property.property.data.PropertyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitRepositoryTest {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    @DisplayName("Should find units by property ID with pagination")
    void findByPropertyId_shouldReturnCorrectUnits() {
        // 1. Creiamo e salviamo una Property
        Property property = new Property();
        property.setName("Test Building");
        property.setType(PropertyType.BUILDING);

        // FIX: Usiamo una nuova variabile 'savedProperty' che è effectively final
        Property savedProperty = propertyRepository.save(property);

        // 2. Creiamo e salviamo 2 Units collegate alla Property salvata
        createAndSaveUnit(savedProperty, "Unit 1", "U1");
        createAndSaveUnit(savedProperty, "Unit 2", "U2");

        // 3. Creiamo un'altra Property e Unit per verificare l'isolamento
        Property otherProperty = new Property();
        otherProperty.setName("Other Building");
        otherProperty.setType(PropertyType.VILLA);
        Property savedOtherProperty = propertyRepository.save(otherProperty);
        createAndSaveUnit(savedOtherProperty, "Unit Other", "U3");

        // 4. Eseguiamo la query sotto test usando l'ID della proprietà corretta
        Page<Unit> result = unitRepository.findByPropertyId(savedProperty.getId(), PageRequest.of(0, 10));

        // 5. Verifiche
        assertNotNull(result);
        assertEquals(2, result.getTotalElements(), "Should find exactly 2 units for the first property");

        // Ora 'savedProperty' è effectively final e può essere usata nella lambda
        assertTrue(result.getContent().stream()
                .allMatch(u -> u.getProperty().getId().equals(savedProperty.getId())));
    }

    @Test
    @DisplayName("Should return empty page when property has no units")
    void findByPropertyId_shouldReturnEmpty_whenNoUnits() {
        Property property = new Property();
        property.setName("Empty Building");
        property.setType(PropertyType.BUILDING);
        Property savedProperty = propertyRepository.save(property);

        Page<Unit> result = unitRepository.findByPropertyId(savedProperty.getId(), PageRequest.of(0, 10));

        assertTrue(result.isEmpty());
    }

    // Helper method
    private void createAndSaveUnit(Property prop, String name, String code) {
        Unit unit = new Unit();
        unit.setProperty(prop);
        unit.setInternalName(name);
        unit.setInternalCode(code);
        unit.setType(UnitType.APARTMENT); // Mandatory field
        unit.setAmenities(Set.of());
        unitRepository.save(unit);
    }
}