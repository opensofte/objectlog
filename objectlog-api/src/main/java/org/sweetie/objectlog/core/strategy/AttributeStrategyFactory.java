package org.sweetie.objectlog.core.strategy;
/*
 * FileName: AttributeStrategyFactory
 * Author gouhao
 */

import org.sweetie.objectlog.core.enums.OperationEnum;

import java.util.HashMap;

public class AttributeStrategyFactory {
    private static final AddedParseStrategy add = new AddedParseStrategy();
    private static final UpdatedParseStrategy update = new UpdatedParseStrategy();
    private static final DeletedParseStrategy delete = new DeletedParseStrategy();
    private static final ComplexParseStrategy complex = new ComplexParseStrategy();
    private static final CommonParseStrategy common = new CommonParseStrategy();
    private static final BaseParseStrategy base = new BaseParseStrategy();
    private static final HashMap<OperationEnum, AttributeParseStrategy> strategyMap = new HashMap<>(8, 1f);


    static {
        strategyMap.put(OperationEnum.ADD, add);
        strategyMap.put(OperationEnum.UPDATE, update);
        strategyMap.put(OperationEnum.DEL, delete);
        strategyMap.put(OperationEnum.COMPLEX, complex);
        strategyMap.put(OperationEnum.COMMON, common);
        strategyMap.put(OperationEnum.BASE, base);
    }

    public static AttributeParseStrategy getParseStrategy(OperationEnum type) {
        return strategyMap.get(type);
    }
}
