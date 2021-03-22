package org.geektimes.configuration.microprofile.config.converter;

public class CharacterConverter extends AbstractConverter<Character> {

    @Override
    protected Character doConvert(String character) {
        return character.charAt(0);
    }
}
