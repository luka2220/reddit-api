package com.piplica.reddit_api.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class CountryCodeService {
    private final Map<String, String> ALLOWED_COUNTRIES = Map.ofEntries(
            Map.entry("US", "United States"),
            Map.entry("AR", "Argentina"),
            Map.entry("AU", "Australia"),
            Map.entry("BG", "Bulgaria"),
            Map.entry("CL", "Chile"),
            Map.entry("CA", "Canada"),
            Map.entry("CO", "Colombia"),
            Map.entry("HR", "Croatia"),
            Map.entry("CZ", "Czech Republic"),
            Map.entry("FI", "Finland"),
            Map.entry("FR", "France"),
            Map.entry("DE", "Germany"),
            Map.entry("GR", "Greece"),
            Map.entry("HU", "Hungary"),
            Map.entry("IS", "Iceland"),
            Map.entry("IN", "India"),
            Map.entry("IE", "Ireland"),
            Map.entry("IT", "Italy"),
            Map.entry("JP", "Japan"),
            Map.entry("MY", "Malaysia"),
            Map.entry("MX", "Mexico"),
            Map.entry("NZ", "New Zealand"),
            Map.entry("PH", "Philippines"),
            Map.entry("PL", "Poland"),
            Map.entry("PT", "Portugal"),
            Map.entry("PR", "Puerto Rico"),
            Map.entry("RO", "Romania"),
            Map.entry("RS", "Serbia"),
            Map.entry("SG", "Singapore"),
            Map.entry("ES", "Spain"),
            Map.entry("SE", "Sweden"),
            Map.entry("TW", "Taiwan"),
            Map.entry("TH", "Thailand"),
            Map.entry("TR", "Turkey"),
            Map.entry("GB", "United Kingdom")
    );

    private final Set<String> ALLOWED_CODES = ALLOWED_COUNTRIES.keySet();

    public boolean isValidCountryCode(String code) {
        return ALLOWED_CODES.contains(code);
    }
}
