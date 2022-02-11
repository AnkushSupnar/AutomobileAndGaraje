package com.ankush.print;

import com.ankush.data.entities.Bill;
import com.ankush.data.entities.Transaction;
import com.ankush.data.service.BillService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.itextpdf.text.*;

import java.io.FileOutputStream;


public class PrintBill {
    private Bill bill;

    public static String filename = "D:\\AutomobileSoftware\\Prints\\bill.pdf";
    public static final String fontname = "D:\\AutomobileSoftware\\Prints\\kiran.ttf";
    Font f1 = FontFactory.getFont(fontname, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 30f, Font.BOLD);//, BaseColor.BLACK);
    Font f2 = FontFactory.getFont(fontname, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 14f, Font.NORMAL, BaseColor.BLUE);
    Font f3 = FontFactory.getFont(fontname, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16f, Font.BOLD, BaseColor.BLACK);
    Font f4 = FontFactory.getFont(fontname, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 12f, Font.BOLD, BaseColor.BLACK);
    Font f5 = FontFactory.getFont(fontname, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 18f, Font.BOLD, BaseColor.BLACK);

    private static Font font = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    //private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    //private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    //private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private static Font smallfont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    float[] columnWidths = new float[]{10f, 10f, 40f, 10f,10f,10f};
    public PrintBill(Bill bill)
    {

        this.bill = bill;
        try{
            float left = 30;
            float right = 0;
            float top = 20;
            float bottom = 0;
            Document doc = new Document(PageSize.A4,left,right,top,bottom);

            PdfWriter.getInstance(doc, new FileOutputStream(filename));
            doc.open();
            addContent(doc);
            doc.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void generateBill()
    {

    }
    private void addContent(Document doc) {
        try{
        PdfPTable table = new PdfPTable(1);

        PdfPCell c1 = new PdfPCell(new Paragraph("maa{]laI Aa^Taomaaobaa[-la",f1));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(0);
        c1.setBorder(PdfPCell.NO_BORDER);
        table.addCell(c1);

        c1 = new PdfPCell(new Paragraph("AMmaLnaor, taa.naovaasaa,ija.Ahmadnagar 414105",f5));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(PdfPCell.NO_BORDER);
        table.addCell(c1);

        c1 = new PdfPCell(new Paragraph("Dist Ahmednagar, State-Maharashtra 414105",smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(PdfPCell.NO_BORDER);
        //table.addCell(c1);

        c1 = new PdfPCell(new Paragraph("saMpak- :",f5));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(PdfPCell.NO_BORDER);
        table.addCell(c1);

        c1 = new PdfPCell(new Paragraph("Cash/Credit",smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

            PdfPTable billnotable = new PdfPTable(2);
            c1 = new PdfPCell(billnotable);
            PdfPCell billCell = new PdfPCell(new Paragraph("Bill No:-"+bill.getId(),smallBold));
            billCell.setBorder(PdfPCell.BOTTOM);
            billnotable.addCell(billCell);

            PdfPCell dateCell = new PdfPCell(new Paragraph("Date:-"+bill.getDate(),smallBold));
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            dateCell.setBorder(PdfPCell.BOTTOM);
            billnotable.addCell(dateCell);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("ga`ahkacao naava: "+bill.getCustomer().getCustomername(),f3));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(0);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("ga`ahkacaa patta: "+
                    bill.getCustomer().getAddressline()+
                    ","+bill.getCustomer().getVillage()+
                    ",taalauka :"+bill.getCustomer().getTaluka()+
                    ",ijalha-"+bill.getCustomer().getDistrict()
                    ,f3));
            c1.setBorder(PdfPCell.BOTTOM);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            //Adding Transactions

            PdfPTable trTableHead = new PdfPTable(6);
            trTableHead.setWidths(columnWidths);
            c1 = new PdfPCell(new Paragraph("Sr.No",smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            trTableHead.addCell(c1);

            c1 = new PdfPCell(new Paragraph("Part No",smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            trTableHead.addCell(c1);

            c1 = new PdfPCell(new Paragraph("Part Name",smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            trTableHead.addCell(c1);

            c1 = new PdfPCell(new Paragraph("Rate",smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            trTableHead.addCell(c1);

            c1 = new PdfPCell(new Paragraph("Quantity",smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            trTableHead.addCell(c1);

            c1 = new PdfPCell(new Paragraph("Amount",smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            trTableHead.addCell(c1);

            c1 = new PdfPCell(trTableHead);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            int srno=0;
            double amount = 0.0f;
            PdfPTable trTable = new PdfPTable(6);
            for(Transaction tr:bill.getTransactions())
            {
                trTable.setWidths(columnWidths);
                c1 = new PdfPCell(new Paragraph(""+(++srno),smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorder(PdfPCell.LEFT);
                trTable.addCell(c1);

                c1 = new PdfPCell(new Paragraph(tr.getPartno(),smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                c1.setBorder(PdfPCell.NO_BORDER);
                c1.setBorder(PdfPCell.LEFT);
                trTable.addCell(c1);

                c1 = new PdfPCell(new Paragraph(""+tr.getPartname(),f3));
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                c1.setBorder(PdfPCell.NO_BORDER);
                c1.setBorder(PdfPCell.LEFT);
                trTable.addCell(c1);

                c1 = new PdfPCell(new Paragraph(""+tr.getRate(),smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorder(PdfPCell.NO_BORDER);
                c1.setBorder(PdfPCell.LEFT);
                trTable.addCell(c1);

                c1 = new PdfPCell(new Paragraph(""+tr.getQuantity(),smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorder(PdfPCell.NO_BORDER);
                c1.setBorder(PdfPCell.LEFT);
                trTable.addCell(c1);

                c1 = new PdfPCell(new Paragraph(""+tr.getAmount(),smallBold));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorder(PdfPCell.NO_BORDER);
                c1.setBorder(PdfPCell.LEFT);
                trTable.addCell(c1);
                amount = amount+tr.getAmount();
            }
            c1 = new PdfPCell(trTable);
            c1.setFixedHeight(400);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setBorder(PdfPCell.BOX);
            table.addCell(c1);

            //Add to Main Table
            c1 = new PdfPCell(addColumnAsTable("Net Total",amount));
            c1.setFixedHeight(20);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setBorder(PdfPCell.BOX);
            table.addCell(c1);

            c1 = new PdfPCell(addColumnAsTable("Other Charges",bill.getOther()));
            c1.setFixedHeight(20);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setBorder(PdfPCell.BOX);
            table.addCell(c1);

            c1 = new PdfPCell(addColumnAsTable("Grand Total",bill.getGrand()));
            c1.setFixedHeight(20);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setBorder(PdfPCell.BOX);
            table.addCell(c1);

            c1 = new PdfPCell(addFooterTable());
            c1.setFixedHeight(50);
            c1.setHorizontalAlignment(Element.ALIGN_BOTTOM);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setBorder(PdfPCell.BOX);
            table.addCell(c1);

            PdfPTable mainBorder = new PdfPTable(1);

            c1 = new PdfPCell(table);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setBorder(PdfPCell.BOX);
            mainBorder.addCell(c1);

            c1 = new PdfPCell(new Paragraph("Software By Ankush Supnar MNo.8329394603",smallfont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBorder(PdfPCell.NO_BORDER);
            //c1.setBorder(PdfPCell.BOX);
            mainBorder.addCell(c1);

            doc.add(mainBorder);

            //doc.add(p);
            System.out.println("Write Done");



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private PdfPTable addColumnAsTable(String note, double amount) {
        try {
            //Adding Total Column In Table
            PdfPTable totalTable = new PdfPTable(6);
            totalTable.setWidths(columnWidths);

            PdfPCell c1 = new PdfPCell(new Paragraph("",smallBold));

            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setColspan(3);
            // c1.setBorder(PdfPCell.LEFT);
            totalTable.addCell(c1);

            c1 = new PdfPCell(new Paragraph(note,smallBold));
            c1.setColspan(2);
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setBorder(PdfPCell.BOX);
            totalTable.addCell(c1);

            c1 = new PdfPCell(new Paragraph(""+amount,smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setBorder(PdfPCell.BOX);
            totalTable.addCell(c1);

            return totalTable;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    private PdfPTable addFooterTable()
    {
        try {
            PdfPTable table = new PdfPTable(6);
            table.setWidths(columnWidths);

            PdfPCell c1 = new PdfPCell(new Paragraph("For Recivers Sign",smallBold));
            c1.setFixedHeight(20);
            c1.setHorizontalAlignment(Element.ALIGN_BOTTOM);
            c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setColspan(3);

            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("For Mauli Automobiles",smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
            c1.setBorder(PdfPCell.NO_BORDER);
            c1.setColspan(3);
            table.addCell(c1);
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
