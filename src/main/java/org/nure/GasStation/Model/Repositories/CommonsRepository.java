package org.nure.GasStation.Model.Repositories;

import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;

@Service
public class CommonsRepository {
    public String generateStringId(int length) {
        byte[] arr = new byte[length];
        new Random().nextBytes(arr);
        return new String(arr, Charset.forName("UTF-8"));
    }
}
