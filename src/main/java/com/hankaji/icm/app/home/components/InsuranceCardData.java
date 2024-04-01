package com.hankaji.icm.app.home.components;

import java.util.Collection;
import java.util.function.Function;

import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.system.DataManager;

public class InsuranceCardData extends TableDataPanel<InsuranceCard> {

    public InsuranceCardData(Collection<String> tableTitles, Function<InsuranceCard, String[]> rowMapper,
            DataManager<InsuranceCard> db) {
        super(tableTitles, rowMapper, db);
        //TODO Auto-generated constructor stub
    }
    
}
