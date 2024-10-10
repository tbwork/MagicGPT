package com.magicvector.ai.exceptions;

import java.util.Collection;

public class Assert {




    public static void judge(boolean expression, String errorMessage){

        if(!expression){
            throw new MagicGPTGeneralException(errorMessage);
        }

    }



    public static void isNotBlank(String targetString, String targetName){

        judge(targetString != null && !targetString.isEmpty(), targetName + " should not be empty!");

    }


    public static void isNotEmpty(Collection collections, String targetName){
        judge(!collections.isEmpty(), "The collection named '"+targetName + "' should not be empty!");
    }

    public static void isNotEmpty(Object [] array, String targetName){
        judge(array.length > 0, "The array named '"+targetName + "' should not be empty!");
    }

}
