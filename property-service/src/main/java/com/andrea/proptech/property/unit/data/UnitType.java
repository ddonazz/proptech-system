package com.andrea.proptech.property.unit.data;

public enum UnitType {
    APARTMENT,      // Appartamento generico
    STUDIO,         // Monolocale
    PENTHOUSE,      // Attico
    VILLA_PORTION,  // Porzione di villa (es. bifamiliare)
    SINGLE_VILLA,   // Villa singola (se la Property è la villa e ha 1 sola unità)
    COMMERCIAL     // Negozio, Ufficio (per espansioni future)
}