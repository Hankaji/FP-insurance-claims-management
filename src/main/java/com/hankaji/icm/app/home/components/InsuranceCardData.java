package com.hankaji.icm.app.home.components;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.googlecode.lanterna.gui2.Border;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.services.InsuranceCardManager;

public class InsuranceCardData extends TableDataPanel<InsuranceCard> {

    public InsuranceCardData(Consumer<Map<String, String>> updateHelperText) {
        super(List.of(
                String.format("%-20s", "Name"),
                String.format("%-12s", "ID"),
                String.format("%-30s", "Dependent"),
                String.format("Claims amount")),
                ph -> new String[] {
                        
                },
                InsuranceCardManager.getInstance(),
                updateHelperText);
    }

    @Override
    public synchronized Border withBorder() {
        // TODO Auto-generated method stub
        return super.withBorder();
    }

    @Override
    protected Map<String, String> useHelperText() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
