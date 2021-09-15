package com.reportes.controllers;

import java.io.FileInputStream;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reportes.services.IServiceClientFeign;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



@Controller
@RequestMapping("/netv")
public class ReporteController {
	
	@Autowired
	private IServiceClientFeign repo;
	
	
	@GetMapping("/reporte")	
	public ResponseEntity<byte[]> generearReporteUsuariosPDF () throws Exception, JRException {
		
		
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(repo.getAll());
			
			JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/java.main/resources/modelo.jrxml"));
			
			HashMap <String,Object> map = new HashMap<>();
			
			JasperPrint fillReport = JasperFillManager.fillReport(compileReport, map,beanCollectionDataSource);
			
			//JasperExportManager.exportReportToPdfFile(fillReport,"modelo.pdf");
			
			byte [] data = JasperExportManager.exportReportToPdf(fillReport);
			
			org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
			
			headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline;filename:reporte.pdf");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
		
		
	
	}
}