package com.andrea.proptech.property.exception;

import com.andrea.proptech.core.exception.ErrorDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PropertyErrorCodes implements ErrorDefinition {

    // --- Property (3000-3099) ---
    PROPERTY_NOT_FOUND(3000, "property.not.found", "Property not found"),

    // --- Unit (3100-3199) ---
    UNIT_NOT_FOUND(3100, "unit.not.found", "Unit not found"),

    // --- Room (3200-3299) ---
    ROOM_NOT_FOUND(3200, "room.not.found", "Room not found for this unit"),

    // --- Cadastral (3300-3399) ---
    CADASTRAL_DATA_NOT_FOUND(3300, "cadastral.data.not.found", "Cadastral data not found for this unit"),

    // --- APE (3400-3499) ---
    ENERGY_CERTIFICATE_NOT_FOUND(3400, "energy.certificate.not.found", "Energy certificate not found for this unit"),
    ENERGY_CERTIFICATE_EXISTS(3401, "energy.certificate.exists", "An energy certificate already exists for this unit");


    private final int code;
    private final String errorCode;
    private final String defaultMessage;
}