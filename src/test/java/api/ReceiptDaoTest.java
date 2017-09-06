package api;

import dao.JooqConfig;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class ReceiptDaoTest {

    @Test
    public void testInsertReceipt() {
        org.jooq.Configuration jooqConfig = JooqConfig.setupJooq();
        ReceiptDao receiptDao = new ReceiptDao(jooqConfig);
        receiptDao.insert("KFC", BigDecimal.valueOf(5));
        receiptDao.insert("Burger&lobster", BigDecimal.valueOf(22));
        List<ReceiptsRecord> receipts = receiptDao.getAllReceipts();

        Assert.assertEquals(receipts.get(1).getMerchant(), "Burger&lobster");
        Assert.assertEquals(receipts.size(), 2);
    }
}
