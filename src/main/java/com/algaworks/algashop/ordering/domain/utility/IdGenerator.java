package com.algaworks.algashop.ordering.domain.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;
import io.hypersistence.tsid.TSID;

import java.util.UUID;

public class IdGenerator {

    private static final TimeBasedEpochRandomGenerator timeBaseEpochRandomGererator = Generators.timeBasedEpochRandomGenerator();

    private static final TSID.Factory tsidFactory = TSID.Factory.INSTANCE;

    private IdGenerator() {
    }

    public static UUID generateTimBasedUUID() {
        return timeBaseEpochRandomGererator.generate();
    }

    /*
     * variáveis de ambiente em produção
     * TSID_NODE=0 - qual instância
     * TSID_NODE_COUNT=3 - instâncias de microservices
     *
     */

    public static TSID generateTSID() {
        return tsidFactory.generate();
    }
}

