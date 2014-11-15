package services.implementation;

import org.springframework.stereotype.Service;
import services.interfaces.ICodeGeneratorService;

import java.util.Random;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
@Service
public class CodeGeneratorSpringService implements ICodeGeneratorService {

    private static final char[] highLetterOrNumberChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static Random random = new Random();

    private enum CodePattern {

        SESSSION_TOKEN("HHHHHHHHHHHHHHHHHHHH");

        private final String pattern;

        private CodePattern(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }

    @Override
    public String generateSessionToken() {
        return getCode(CodePattern.SESSSION_TOKEN);
    }

    private String getCode(CodePattern pattern) {
        StringBuilder builder = new StringBuilder();
        for(char symbol : pattern.getPattern().toCharArray()) {
            switch (symbol) {
                case 'H':
                    builder.append(getRandomHighLetterOrNumberChar());
                    break;
            }
        }
        return builder.toString();
    }

    private static char getRandomHighLetterOrNumberChar() {
        int position = random.nextInt(highLetterOrNumberChars.length);
        return highLetterOrNumberChars[position];
    }

}
