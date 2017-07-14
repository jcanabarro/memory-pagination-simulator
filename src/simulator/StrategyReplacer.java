package simulator;

import simulator.Replacer.Replacer;

class StrategyReplacer {

    private Replacer method;

    StrategyReplacer (Replacer method) {
        this.method = method;
    }

    public void changeMethod (Replacer method) {
        this.method = method;
    }

    void run () {
        this.method.run();
    }
}
