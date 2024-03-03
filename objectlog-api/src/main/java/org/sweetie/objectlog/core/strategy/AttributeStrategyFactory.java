package org.sweetie.objectlog.core.strategy;/*
 * Copyright (C), 2021-2024
 * FileName: AttributeStrategyFactory
 * Author gouhao
 * Date: 2024/2/25 17:54
 * Description:
 */

import org.sweetie.objectlog.core.enums.OperationEnum;
import org.sweetie.objectlog.core.strategy.*;

public class AttributeStrategyFactory {
    private static AddedParseStrategy add;
    private static UpdatedParseStrategy update;
    private static DeletedParseStrategy delete;
    private static ComplexParseStrategy complex;
    private static CommonParseStrategy common;
    public static AttributeParseStrategy getParseStrategy(OperationEnum type){
        if (type == OperationEnum.ADD){
            if (null == add){
                synchronized (AddedParseStrategy.class){
                    if (null == add){
                        add = new AddedParseStrategy();
                    }
                }
            }
            return add;
        }
        if (type == OperationEnum.UPDATE){
            if (null == update){
                synchronized (UpdatedParseStrategy.class){
                    if (null == update){
                        update = new UpdatedParseStrategy();
                    }
                }
            }
            return update;
        }
        if (type == OperationEnum.DEL){
            if (null == delete){
                synchronized (DeletedParseStrategy.class){
                    if (null == delete){
                        delete = new DeletedParseStrategy();
                    }
                }
            }
            return delete;
        }
        if (type == OperationEnum.COMPLEX){
            if (null == complex){
                synchronized (ComplexParseStrategy.class){
                    if (null == complex){
                        complex = new ComplexParseStrategy();
                    }
                }
            }
            return complex;
        }
        if (type == OperationEnum.COMMON){
            if (null == common){
                synchronized (CommonParseStrategy.class){
                    if (null == common){
                        common = new CommonParseStrategy();
                    }
                }
            }
            return common;
        }
        return null;
    }
}
