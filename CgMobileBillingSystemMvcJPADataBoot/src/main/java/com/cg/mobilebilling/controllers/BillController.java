package com.cg.mobilebilling.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.exceptions.BillDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.InvalidBillMonthException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PostpaidAccountNotFoundException;
import com.cg.mobilebilling.services.BillingServices;
import com.cg.mobilebilling.services.GeneratePdfReport;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class BillController {
	private Bill bill;
	private List<Bill> bills;
	@Autowired
	private BillingServices billingServices;

	@RequestMapping("generateMonthlyMobileBill")
	public ModelAndView registerPostpaidAccountAction(@RequestParam("customerID") int customerID,
			@RequestParam("mobileNo") long mobileNo, @RequestParam("billMonth") String billMonth,
			@RequestParam("noOfLocalSMS") int noOfLocalSMS, @RequestParam("noOfStdSMS") int noOfStdSMS,
			@RequestParam("noOfLocalCalls") int noOfLocalCalls, @RequestParam("noOfStdCalls") int noOfStdCalls,
			@RequestParam("internetDataUsageUnits") int internetDataUsageUnits) {
		try {
			bill = billingServices.generateMonthlyMobileBill(customerID, mobileNo, billMonth, noOfLocalSMS, noOfStdSMS,
					noOfLocalCalls, noOfStdCalls, internetDataUsageUnits);
			return new ModelAndView("/generateBillSuccessPage", "bill", bill);
		} catch (CustomerDetailsNotFoundException | PostpaidAccountNotFoundException | InvalidBillMonthException
				| BillingServicesDownException | PlanDetailsNotFoundException e) {
			return new ModelAndView("/generateBillPage", "error", e.getMessage());
		}

	}

	@RequestMapping("getPostpaidAccountBillDetails")
	public ModelAndView postpaidAccountBillDetailsAction(@RequestParam("customerID") int customerID,
			@RequestParam("mobileNo") long mobileNo) {
		try {
			bills = billingServices.getCustomerPostPaidAccountAllBillDetails(customerID, mobileNo);
			return new ModelAndView("/postpaidAccountBillDisplayDetailsPage", "bills", bills);
		} catch (CustomerDetailsNotFoundException | PostpaidAccountNotFoundException | BillingServicesDownException
				| BillDetailsNotFoundException e) {
			return new ModelAndView("/postpaidAccountBillDetailsPage", "error", e.getMessage());
		}

	}

	@RequestMapping("getMobileBillDetails")
	public ModelAndView getMobileBillDetailsAction(@RequestParam("customerID") int customerID,
			@RequestParam("mobileNo") long mobileNo, @RequestParam("billMonth") String billMonth) {
		try {
			bill = billingServices.getMobileBillDetails(customerID, mobileNo, billMonth);
			return new ModelAndView("/mobileBillDiplayDetailsPage", "bill", bill);
		} catch (CustomerDetailsNotFoundException | PostpaidAccountNotFoundException | InvalidBillMonthException
				| BillDetailsNotFoundException | BillingServicesDownException e) {
			return new ModelAndView("/mobileBillDetailsPage", "error", e.getMessage());
		}
	}

	/*@RequestMapping("generatePdf")
	public ModelAndView generatePdfAction(@RequestParam("customerID") int customerID,
			@RequestParam("mobileNo") long mobileNo, @RequestParam("billMonth") String billMonth) {

		try {
			bill = billingServices.getMobileBillDetails(customerID, mobileNo, billMonth);
			String fileName = new String(billMonth + "_" + customerID +"_"+ mobileNo);
			OutputStream file = new FileOutputStream(new File("D:\\MobileBillingBills\\" + fileName+".pdf"));
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			PdfPTable table = new PdfPTable(9);
			if (document.isOpen() == false) {
				document.open();
			}
			PdfPCell cell = new PdfPCell(new Paragraph("Bill"));

			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(10.0f);
			cell.setBackgroundColor(new BaseColor(140, 221, 8));

			table.addCell(cell);
			table.addCell("billID");
			table.addCell("localSMSAmount");
			table.addCell("stdSMSAmount");
			table.addCell("localCallAmount");
			table.addCell("stdCallAmount");
			table.addCell("internetDataUsageAmount");
			table.addCell("servicesTax");
			table.addCell("vat");
			table.addCell("totalBillAmount");

			table.addCell("" + bill.getBillID());
			table.addCell("" + bill.getLocalSMSAmount());
			table.addCell("" + bill.getStdSMSAmount());
			table.addCell("" + bill.getLocalCallAmount());
			table.addCell("" + bill.getStdCallAmount());
			table.addCell("" + bill.getInternetDataUsageAmount());
			table.addCell("" + bill.getServicesTax());
			table.addCell("" + bill.getVat());
			table.addCell("" + bill.getTotalBillAmount());

			document.add(table);
			document.newPage();
			document.close();
			file.close();
			
			return new ModelAndView("mobileBillDetailsSuccessPage","fileName",fileName);
		} catch (CustomerDetailsNotFoundException | PostpaidAccountNotFoundException | InvalidBillMonthException
				| BillDetailsNotFoundException | BillingServicesDownException | DocumentException | IOException e) {
			return new ModelAndView("/mobileBillDetailsPage", "error", e.getMessage());
		}
	}
	@RequestMapping(value = "/getpdf/{pdf}", method = RequestMethod.GET)
	public  void getPdf(@PathVariable("pdf") String fileName, HttpServletResponse response) throws IOException {

	    try {
	        File file = new File("D:\\MobileBillingBills\\" + fileName+".pdf");
	        
	        if (file.exists()) {
	            // here I use Commons IO API to copy this file to the response output stream, I don't know which API you use.
//	           IOUtils.copy(file, response.getOutputStream());
	            // here we define the content of this file to tell the browser how to handle it
	           byte[] bytesArray = new byte[(int) file.length()]; 
	           FileInputStream fis = new FileInputStream(file);
	           fis.read(bytesArray); //read file into bytes[]
	           fis.close();
	            IOUtils.copy(new ByteArrayInputStream(bytesArray), response.getOutputStream());
	            response.setContentType("application/pdf");
	            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
	            response.flushBuffer();
	        } else {
	            System.out.println("File Not Found");
	        }
	    } catch (IOException exception) {
	        System.out.println(exception.getMessage());
	    }
}*/
	
	@RequestMapping(value = "/allBillsPdfReport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE )
    public ResponseEntity<InputStreamResource> getAllBillsPdfReport(@RequestParam("customerID")int customerID,@RequestParam("mobileNo")long mobileNo) throws IOException, CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException, BillDetailsNotFoundException, PlanDetailsNotFoundException, DocumentException {

        List<Bill> bills =  billingServices.getCustomerPostPaidAccountAllBillDetails(customerID, mobileNo);

        ByteArrayInputStream bis = GeneratePdfReport.billsReport(bills);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=billsreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
