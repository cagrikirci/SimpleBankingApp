package com.eteration.simplebanking;

import com.eteration.simplebanking.dto.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {

    @Test
    public void testResponseConstructor() {

        Response response = new Response("OK", "123e4567-e89b-42d3-a456-556642440000");

        assertEquals("OK", response.getStatus());
        assertEquals("123e4567-e89b-42d3-a456-556642440000", response.getApprovalCode());
    }

    @Test
    public void testGettersAndSetter() {
        Response response = new Response("OK", "123e4567-e89b-42d3-a456-556642440000");

        response.setStatus("FAILED");
        assertEquals("FAILED", response.getStatus());

        response.setApprovalCode("new-approval-code");
        assertEquals("new-approval-code", response.getApprovalCode());
    }
}
