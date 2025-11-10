package com.andrea.proptech.property.property.mapper.request;

import com.andrea.proptech.property.address.data.Address;
import com.andrea.proptech.property.address.mapper.request.AddressUpdateDtoToAddressMapper;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.web.dto.request.PropertyUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class PropertyUpdateDtoToPropertyMapper implements BiFunction<PropertyUpdateDto, Property, Property> {

    // Iniettiamo il mapper di update per l'indirizzo
    private final AddressUpdateDtoToAddressMapper addressUpdateDtoToAddressMapper;

    @Override
    public Property apply(@NonNull PropertyUpdateDto dto, @NonNull Property property) {

        property.setName(dto.name());
        property.setType(dto.type());
        property.setConstructionYear(dto.constructionYear());

        property.setAmenities(dto.amenities() != null ? dto.amenities() : Collections.emptySet());

        // Gestiamo l'aggiornamento dell'indirizzo nidificato
        if (dto.address() != null) {
            // Se la property non ha ancora un indirizzo, ne crea uno nuovo
            if (property.getAddress() == null) {
                property.setAddress(new Address());
            }
            // Applica l'aggiornamento all'indirizzo esistente o appena creato
            addressUpdateDtoToAddressMapper.apply(dto.address(), property.getAddress());
        } else {
            // Se il DTO passa null, potremmo voler rimuovere l'indirizzo
            // (Attenzione: CascadeType.ALL e orphanRemoval=true sull'entità Property)
            property.setAddress(null);
        }

        return property;
    }
}