package ru.varnavskii.wholesalecompany.util;

import ru.varnavskii.wholesalecompany.entity.GoodsEntity;
import ru.varnavskii.wholesalecompany.entity.SalesEntity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

public class ReportExcel {
    private final static File file = new File("src\\main\\resources\\.");
    private static final String path = file.getAbsolutePath();
    private static final String fileLocation = path.substring(0, path.length() - 1);

    public void writeGoods(List<GoodsEntity> listGoods, String filename) throws IOException {
        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation + filename));
             Workbook wb = new Workbook(os, "Source Statistic", "1.0")) {
            Worksheet ws = wb.newWorksheet("List goods");
            ws.width(0, 15);
            ws.width(1, 15);

            ws.value(0, 0, "Name");
            ws.value(0, 1, "Priority");

            int i = 1;
            for (GoodsEntity good : listGoods) {
                ws.value(i, 0, good.getName());
                ws.value(i, 1, good.getPriority());
                i++;
            }
        }
    }

    public void writeSales(List<SalesEntity> listSales, String filename) throws IOException {
        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation + filename));
             Workbook wb = new Workbook(os, "Source Statistic", "1.0")) {
            Worksheet ws = wb.newWorksheet("List sales");
            ws.width(0, 15);
            ws.width(1, 15);
            ws.width(2, 15);

            ws.value(0, 0, "Good id");
            ws.value(0, 1, "Create date");

            int i = 1;
            for (SalesEntity sale : listSales) {
                ws.value(i, 0, sale.getGoodId());
                ws.value(i, 1, sale.getCreateDate().toString());
                ws.value(i, 2, sale.getGoodCount());
                i++;
            }
        }
    }
}
