package tasks;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;

import util.ConfigSession;
import beans.CrimeDataEntity;

public class InsertExcelSQL {

	XSSFWorkbook workbook ;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public String loadExcelData(String path) throws FileNotFoundException, IOException{
		try{
			InputStream excelFileToRead = new FileInputStream(path);

			//File file = new File(path);

			//POIFSFileSystem poifs = new POIFSFileSystem(excelFileToRead);

			//OPCPackage opcPackage = OPCPackage.open(file.getAbsolutePath());
			//workbook = new XSSFWorkbook(opcPackage);

			workbook = new XSSFWorkbook(excelFileToRead);

		}

		//		catch(InvalidFormatException e){
		//			System.out.println(e.getMessage());
		//			e.printStackTrace();
		//		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


		String message = insertExcelDataIntoDatabase();
		return message;
	}

	public String insertExcelDataIntoDatabase(){


		String message = "";
		XSSFSheet sheet = workbook.getSheetAt(0);

		Session session = ConfigSession.openSession();

		try{
			lock.writeLock().lock();

			session.getSessionFactory().getCurrentSession();


			for(int i = 1; i <= sheet.getLastRowNum() ; i++){
				CrimeDataEntity cde = new CrimeDataEntity();
				
				//Get all the identifiers in an ArrayList
				String str = "Select identifier from CrimeDataEntity";
				Query query = session.createQuery(str);
				ArrayList<String> idList = new ArrayList<String>();
				Iterator iter = query.iterate();
				while(iter.hasNext()){
					String s = (String)iter.next();
					idList.add(s);
				}

				XSSFRow row = sheet.getRow(i);

				//System.out.println(row.getCell(0).getNumericCellValue());
				String[] identifier = null;
				XSSFCell id = row.getCell(0);
				if(id.getCellType() == id.CELL_TYPE_NUMERIC){
					DataFormatter format = new DataFormatter();
					identifier = format.formatCellValue(id).split("\\.");
					//System.out.println(identifier[0]);
				}
				else{
					identifier = id.getStringCellValue().split("\\s");
				}
				//System.out.println(identifier[0]);

				String crimeType = row.getCell(1).getStringCellValue().trim();

				String dateTime[] = null;
				XSSFCell dt = row.getCell(2);
				if(dt.getCellType() == dt.CELL_TYPE_NUMERIC){
					DataFormatter formatter = new DataFormatter();
					dateTime = formatter.formatCellValue(dt).split("\\s");					
				}
				else{
					dateTime = dt.getStringCellValue().split("\\s");
				}

				String address = row.getCell(3).getStringCellValue().trim();
				//System.out.println(address);

				String agency = row.getCell(4).getStringCellValue().trim(); 

				Random rand = new Random();
				int min = 0, max = 50;
				
				if(!idList.contains(identifier[0])){
					cde.setIdentifier(identifier[0]);
				}
				else{
					int randNum = rand.nextInt((max-min)+1)+min;
//					int len = identifier[0].length()-1;
//					char last = identifier[0].charAt(len);
					cde.setIdentifier(identifier[0]+randNum);
				}
					cde.setCrimeType(crimeType);
					cde.setDateValue(dateTime[0]);
					cde.setTimeValue(dateTime[1]);
					cde.setAddress(address);
					cde.setAgency(agency);

					try{
						session.beginTransaction();
						session.clear();
						session.save(cde);
						session.getTransaction().commit();
						message = "Data Inserted into database";
					}

					catch(Exception e){
						session.getTransaction().rollback();
						System.out.println(e.getMessage());
						e.printStackTrace();
						message = "Data not iserted into database";
						break;
					}
				}
		}

		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = "Data not iserted into database";
		}

		finally{
			session.close();
			lock.writeLock().unlock();
		}

		return message;
	}

}