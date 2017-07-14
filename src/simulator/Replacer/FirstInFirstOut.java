package simulator.Replacer;

import java.util.ArrayList;

public class FirstInFirstOut extends Replacer {
    public FirstInFirstOut (ArrayList<String> accessList, int pageSize, int framesNumber) {
        super(accessList, pageSize, framesNumber);
    }

    @Override
    public void run () {

    }
}
