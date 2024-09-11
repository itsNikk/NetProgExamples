package quartaB.RipassoSocket.Es1SerializedVersion.Server;

import java.io.Serial;
import java.io.Serializable;

public class StringAnalysisResults implements Serializable {

    @Serial
    private static final long serialVersionUID = -8675668382871586787L;

    private int totalChars;
    private int vowelsCount;
    private int consonantsCount;

    public StringAnalysisResults(int totalChars, int vowelsCount, int consonantsCount) {
        this.totalChars = totalChars;
        this.vowelsCount = vowelsCount;
        this.consonantsCount = consonantsCount;
    }

    public int getTotalChars() {
        return totalChars;
    }

    public int getVowelsCount() {
        return vowelsCount;
    }

    public int getConsonantsCount() {
        return consonantsCount;
    }

    @Override
    public String toString() {
        return "Vocali: " + vowelsCount + ", Consonanti: " + consonantsCount + ", Caratteri totali: " + totalChars;
    }
}
