package com.example.api.utils;

import com.example.api.model.Accessory;
import com.example.api.model.NeededMaterials;
import com.example.api.model.Order;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class PdfGenerator {
    public static byte[] createPDF(Order order) {
        try {
            Document document = new Document();

            BaseFont bf = BaseFont.createFont("./fonts/Helvetica.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);

            Font titleFont = new Font(bf, 30, Font.BOLD);
            Font headerFont = new Font(bf, 14, Font.BOLD);
            Font standardFont = new Font(bf, 12, Font.NORMAL);
            Font boldFont = new Font(bf, 12, Font.BOLD);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.addAuthor(order.getUser().getName());
            document.addSubject("Summary for order#" + order.getId() + ".");

            document.open();

            PdfPTable dateTable = new PdfPTable(1);
            var parHead = new Paragraph("Data przyjęcia zamówienia", boldFont);
            parHead.setAlignment(Element.ALIGN_CENTER);
            dateTable.addCell(parHead);
            var paragraphWithDate = new Paragraph(convertDateToPolishLocal(order.getDate()), standardFont);
            paragraphWithDate.setAlignment(Element.ALIGN_CENTER);
            dateTable.addCell(paragraphWithDate);
            dateTable.setWidthPercentage(33);
            dateTable.setSpacingAfter(10);
            dateTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            document.add(dateTable);

            var title = new Paragraph("Podsumowanie zamówienia#" + order.getId(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            var priceHeader = new Paragraph("Cena sumaryczna: " + order.getPrice() + " PLN", new Font(bf, 20, Font.BOLD));
            priceHeader.setAlignment(Element.ALIGN_CENTER);
            priceHeader.setSpacingAfter(40);
            document.add(priceHeader);


            var clientHeader = new Paragraph("Klient:", headerFont);
            clientHeader.setSpacingAfter(5);
            document.add(clientHeader);

            PdfPTable table = new PdfPTable(5);

            table.addCell(new Paragraph("#", boldFont));
            table.addCell(new Paragraph("Imię i nazwisko", boldFont));
            table.addCell(new Paragraph("Email", boldFont));
            table.addCell(new Paragraph("Numer telefonu", boldFont));
            table.addCell(new Paragraph("Adres", boldFont));
            table.addCell(new Paragraph(order.getClient().getId().toString(), standardFont));
            table.addCell(new Paragraph(order.getClient().getName(), standardFont));
            table.addCell(new Paragraph(order.getClient().getEmail(), standardFont));
            table.addCell(new Paragraph(order.getClient().getPhoneNumber(), standardFont));
            table.addCell(new Paragraph(order.getClient().getAddress(), standardFont));
            table.setWidthPercentage(100);
            table.setSpacingAfter(20);

            document.add(table);


            var userHeader = new Paragraph("Sprzedawca:", headerFont);
            userHeader.setSpacingAfter(5);
            document.add(userHeader);

            PdfPTable userTable = new PdfPTable(2);
            userTable.addCell(new Paragraph("Imię i nazwisko", boldFont));
            userTable.addCell(new Paragraph("Email", boldFont));
            userTable.addCell(new Paragraph(order.getUser().getName(), standardFont));
            userTable.addCell(new Paragraph(order.getUser().getEmail(), standardFont));
            userTable.setWidthPercentage(100);
            userTable.setSpacingAfter(20);

            document.add(userTable);



            var modelHeader = new Paragraph("Model:", headerFont);
            modelHeader.setSpacingAfter(5);
            document.add(modelHeader);

            PdfPTable modelTable = new PdfPTable(3);
            modelTable.addCell(new Paragraph("#", boldFont));
            modelTable.addCell(new Paragraph("Nazwa", boldFont));
            modelTable.addCell(new Paragraph("Cena", boldFont));
            modelTable.addCell(new Paragraph(order.getMachine().getModel().getId().toString(), standardFont));
            modelTable.addCell(new Paragraph(order.getMachine().getModel().getName(), standardFont));
            modelTable.addCell(new Paragraph(order.getMachine().getModel().getPrice() + " PLN", standardFont));
            modelTable.setWidthPercentage(100);
            modelTable.setSpacingAfter(20);

            document.add(modelTable);

            if (!order.getMachine().getModel().getComments().isEmpty()) {
                var commentsHeader = new Paragraph("Uwagi do modelu śrutownicy:", headerFont);
                commentsHeader.setSpacingAfter(2);
                document.add(commentsHeader);
                var comments = new Paragraph(order.getMachine().getModel().getComments(), standardFont);
                comments.setSpacingAfter(20);
                document.add(comments);
            }



            if (!order.getMachine().getModel().getNeededMaterials().isEmpty()) {
                var materialsHeader = new Paragraph("Materiały do budowy:", headerFont);
                materialsHeader.setSpacingAfter(5);
                document.add(materialsHeader);

                PdfPTable materialsTable = new PdfPTable(4);
                materialsTable.addCell(new Paragraph("#", boldFont));
                materialsTable.addCell(new Paragraph("Nazwa", boldFont));
                materialsTable.addCell(new Paragraph("Cena", boldFont));
                materialsTable.addCell(new Paragraph("Potrzebna ilość", boldFont));

                for (NeededMaterials n : order.getMachine().getModel().getNeededMaterials()) {
                    materialsTable.addCell(new Paragraph(n.getId().toString(), standardFont));
                    materialsTable.addCell(new Paragraph(n.getMaterial().getName(), standardFont));
                    materialsTable.addCell(new Paragraph(n.getMaterial().getPrice() + " PLN", standardFont));
                    materialsTable.addCell(new Paragraph(n.getAmount().toString(), standardFont));
                }

                materialsTable.setWidthPercentage(100);
                materialsTable.setSpacingAfter(20);

                document.add(materialsTable);
            }


            if (!order.getMachine().getAccessories().isEmpty()) {
                var accesoriesHeader = new Paragraph("Akcesoria:", headerFont);
                accesoriesHeader.setSpacingAfter(5);
                document.add(accesoriesHeader);

                PdfPTable accessoriesTable = new PdfPTable(3);
                accessoriesTable.addCell(new Paragraph("#", boldFont));
                accessoriesTable.addCell(new Paragraph("Nazwa", boldFont));
                accessoriesTable.addCell(new Paragraph("Cena", boldFont));

                for (Accessory a : order.getMachine().getAccessories()) {
                    accessoriesTable.addCell(new Paragraph(a.getId().toString(), standardFont));
                    accessoriesTable.addCell(new Paragraph(a.getName(), standardFont));
                    accessoriesTable.addCell(new Paragraph(a.getPrice() + " PLN", standardFont));
                }

                accessoriesTable.setWidthPercentage(100);
                accessoriesTable.setSpacingAfter(20);

                document.add(accessoriesTable);
            }

            if (!order.getComments().isEmpty()) {
                var orderCommentsHeader = new Paragraph("Uwagi do zamówienia:", headerFont);
                orderCommentsHeader.setSpacingAfter(2);
                document.add(orderCommentsHeader);
                var orderComments = new Paragraph(order.getComments(), standardFont);
                document.add(orderComments);
            }

            document.close();
            return baos.toByteArray();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertDateToPolishLocal(LocalDateTime date) {
        String result = "";
        result += date.getDayOfMonth() + ".";
        result += date.getMonthValue() + ".";
        result += date.getYear() + ", ";
        result += date.getHour() + ":";
        result += date.getMinute() + ":";
        result += date.getSecond();
        return result;
    }
}
