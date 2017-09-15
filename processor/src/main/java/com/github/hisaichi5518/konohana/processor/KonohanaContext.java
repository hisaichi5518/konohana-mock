package com.github.hisaichi5518.konohana.processor;

import java.util.List;

class KonohanaContext {
    final List<StoreContext> storeContexts;

    KonohanaContext(List<StoreContext> storeContexts) {
        this.storeContexts = storeContexts;
    }

    String getPackageName() {
        return storeContexts.get(0).getClassName().packageName();
    }
}
