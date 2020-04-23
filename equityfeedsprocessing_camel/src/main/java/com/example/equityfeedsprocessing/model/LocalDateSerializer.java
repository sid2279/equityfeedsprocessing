package com.example.equityfeedsprocessing.model;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


public class LocalDateSerializer extends StdSerializer<LocalDate> {

    public LocalDateSerializer() {
        super(LocalDate.class);
    }
    
    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider) throws IOException {
    	System.out.println("Inside serialize method");
    	System.out.println("LocalDate Value "+value);
        generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}