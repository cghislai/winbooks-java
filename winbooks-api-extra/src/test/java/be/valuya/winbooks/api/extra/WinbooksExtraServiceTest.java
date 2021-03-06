package be.valuya.winbooks.api.extra;

import be.valuya.jbooks.model.WbAccount;
import be.valuya.jbooks.model.WbEntry;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Yannick
 */


public class WinbooksExtraServiceTest {

    private WinbooksExtraService winbooksExtraService;
    private WinbooksFileConfiguration winbooksFileConfiguration;

    @Before
    public void setup() {
        winbooksExtraService = new WinbooksExtraService();

        Path baseFolderPath = Paths.get("C:\\temp\\wbdata\\valuya");

        winbooksFileConfiguration = new WinbooksFileConfiguration();
        winbooksFileConfiguration.setBaseFolderPath(baseFolderPath);
        winbooksFileConfiguration.setBaseName("valuya");
    }

    @Test
    public void testReadDBF() throws Exception {
        winbooksExtraService.dumpDbf(winbooksFileConfiguration, "act");
        winbooksExtraService.dumpDbf(winbooksFileConfiguration, "acf");
        winbooksExtraService.dumpDbf(winbooksFileConfiguration, "csf");
    }

    @Test
    public void testAccountTotal() {
        Date startDate = new Date(2016, 03, 01);
        Date endDate = new Date(2017, 01, 01);
        TreeMap<String, Map<Integer, BigDecimal>> categoryMonthTotalMap = winbooksExtraService.streamAct(winbooksFileConfiguration)
                .filter(wbEntry -> wbEntry.getDate() != null)
                .filter(wbEntry -> !wbEntry.getDate().before(startDate))
                .filter(wbEntry -> wbEntry.getDate().before(endDate))
                .filter(wbEntry -> wbEntry.getAccountGl() != null)
                .collect(
                        Collectors.groupingBy(
                                wbEntry -> wbEntry.getAccountGl().substring(0, 2),
                                TreeMap::new,
                                Collectors.groupingBy(
                                        wbEntry -> wbEntry.getDate().getMonth(),
                                        Collectors.reducing(BigDecimal.ZERO, WbEntry::getAmountEur, BigDecimal::add))
                        )
                );

        categoryMonthTotalMap.forEach((accountNumber, monthTotalMap) -> {
            BigDecimal accountTotal = monthTotalMap.values().stream()
                    .collect(Collectors.reducing(BigDecimal::add))
                    .orElse(BigDecimal.ZERO);
            monthTotalMap.forEach((month, total) -> System.out.println("Account " + accountNumber + ", month " + month + ": " + total));
            System.out.println("Account " + accountNumber + ": " + accountTotal);
        });
    }

    @Test
    public void testGetAccountDescription() {
        String description = winbooksExtraService.streamAcf(winbooksFileConfiguration)
                .filter(wbAccount -> wbAccount.getAccountNumber() != null)
                .filter(wbAccount -> wbAccount.getAccountNumber().equals("700000"))
                .map(WbAccount::getName11)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);

        System.out.println("Account: " + description);
    }

}
