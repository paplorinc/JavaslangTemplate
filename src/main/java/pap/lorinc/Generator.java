package pap.lorinc;

import java.util.concurrent.ThreadLocalRandom;

class Generator {
    static char randomUppercaseChar() { return (char) ThreadLocalRandom.current().nextInt('A', 'Z'); }
}
