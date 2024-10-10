package com.magicvector.ai.wizards.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * The user-defined prompt words, the final system prompt words
 * will be {customHeadPrompt} + {MagicGPT's prompt} + {customTailPrompt}.
 *
 */
@Data
@AllArgsConstructor
public class CustomPrompt {


    private String headPrompt = "";

    private String tailPrompt = "";


    public static CustomPrompt buildHeadPrompt(String headPrompt){

        return new CustomPrompt(headPrompt, "");

    }


    public static CustomPrompt buildTailPrompt(String tailPrompt){

        return new CustomPrompt("", tailPrompt);

    }

}
