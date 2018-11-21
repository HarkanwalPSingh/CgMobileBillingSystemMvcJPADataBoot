package com.cg.mobilebilling.services;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.cg.mobilebilling.beans.Bill;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class GeneratePdfReport {
	public static ByteArrayInputStream billsReport(List<Bill> bills) throws DocumentException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfPTable table = new PdfPTable(16);
		table.setWidthPercentage(100);
		//table.setWidths(new int[]{1, 16, 16});

		Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);

		PdfPCell hcell;
		hcell = new PdfPCell(new Phrase("Bll Id", headFont));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Bill Month", headFont));
		//hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Mobile No.", headFont));
		//hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Internet Data Usage Units", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("No of Local Calls", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("No of Local SMS", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("No of Std Calls", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("No of Std SMS", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Internet Data Usage Amount", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Local Call Amount", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Local SMS Amount", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Std Call Amount", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Std SMS Amount", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Services Tax", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("VAT", headFont));
		table.addCell(hcell);
		hcell = new PdfPCell(new Phrase("Total Bill Amount", headFont));
		table.addCell(hcell);
		
		Font bodyFont = FontFactory.getFont(FontFactory.COURIER, 8);

		for (Bill bill : bills) {

			PdfPCell cell;

			cell = new PdfPCell(new Phrase(String.valueOf(bill.getBillID()),bodyFont));
			/*                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);*/
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(bill.getBillMonth()));
			/*cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);*/
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getPostPaidAccount().getMobileNo())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getInternetDataUsageUnits())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getNoOfLocalCalls())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getNoOfLocalSMS())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getNoOfStdCalls())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getNoOfStdSMS())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getInternetDataUsageAmount())));
			/*cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);*/
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getLocalCallAmount())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getLocalSMSAmount())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getStdCallAmount())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getStdSMSAmount())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getServicesTax())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getVat())));
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bill.getTotalBillAmount())));
			table.addCell(cell);
		}

		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);

		document.close();

		return new ByteArrayInputStream(out.toByteArray());
	}
}
