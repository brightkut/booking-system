package com.brightkut.commonlib.lib.uuid;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.UUID;

public class UuidUtils {

    public static UUID generateUUID(){
        return UuidCreator.getTimeOrderedEpoch();
    }
}
