package com.ankush.common;


import impl.org.controlsfx.autocompletion.SuggestionProvider;

import java.util.List;

public class CommonData {
    private static SuggestionProvider<String> partyNameProvider;

    public static void setPartyNames(List<String>partyList)
    {
        partyNameProvider = SuggestionProvider.create(partyList);
    }
    public static SuggestionProvider<String>getPartyNameProvider()
    {
        return partyNameProvider;
    }
}
