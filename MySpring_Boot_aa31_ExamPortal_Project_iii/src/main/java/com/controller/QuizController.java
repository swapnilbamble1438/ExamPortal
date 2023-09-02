package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exception.CustomException;
import com.model.exam.Quiz;
import com.service.CategoryService;
import com.service.QuizService;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;


@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins="*")
public class QuizController {
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private QuizService quizServ;
	
	@Autowired
	private CategoryService catServ;

	
//======= add quiz service ==============================================================
	@PostMapping("/create")
	public ResponseEntity<?> add(@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("maxmarks") String maxmarks,
			@RequestParam("numofquestions") String numofquestions,
			@RequestParam(name="active",defaultValue = "false") String active,
			@RequestParam("catid") String catid,
			@RequestParam(name="imagefile",required=false) MultipartFile file) throws Exception
	{

		Quiz quiz = new Quiz();
		
		
		String title2= title.substring(1,title.length()-1);
		String title3 = title2.replaceAll("\\\\", "");
		
		String description2 = description.substring(1,description.length()-1);
		String description3 = description2.replaceAll("\\\\","");
		
		quiz.setTitle(title3);
		quiz.setDescription(description3);
		
		quiz.setMaxmarks(Integer.parseInt(maxmarks));
	
		//String active2 = active.substring(1,active.length()-1);
		if(active.equals("true"))
			quiz.setActive(true);
		if(active.equals("false"))
			quiz.setActive(false);
		
		
		quiz.setNumofquestions(Integer.parseInt(numofquestions));
		
		quiz.setCategory(this.catServ.getCategory(Integer.parseInt(catid)));
		
	
		ArrayList <String> ext=new ArrayList<>();
		ext.add(".jpg");ext.add(".bmp");ext.add(".jpeg");ext.add(".png");ext.add(".webp");
		
		try {
			
			
			if(file == null || file.isEmpty()) 
			{
				System.out.println("File is empty");
				quiz.setImage("Quiz.png");
			}
			else {
				// upload the file to folder and save the name to quiz table
				
				String imageName = file.getOriginalFilename();
				
				String	fileExtension = imageName.substring(imageName.lastIndexOf("."));
				
				
				if(!ext.contains(fileExtension))
				{
					 System.out.println("\n=================================================================================================================\n"
					           + "         Message: Unsupported format, Please try to upload image file with Extensions (jpg,jpeg,png,bmp,webp) only . "+imageName +" " + fileExtension+"  \n"
					           + "==========================================================================================================================");
					
					 throw new CustomException("Exception: Unsupported format, Please try to upload image file with Extensions (jpg,jpeg,png,bmp,webp) only ");
					 		
				}
				
				String newImageName = quiz.getTitle().replaceAll("[^a-zA-Z0-9]","");
				
				String newImgName = newImageName.concat(fileExtension);
				
				System.out.println("newImgName: "+newImgName);
				quiz.setImage(newImgName);
				
			// path
			String path="src/main/resources/static/image";
				
			// Fullpath
			String filePath = path+File.separator+newImgName;
		
			//create folder if not created
			File f = new File(path);
			if(!f.exists())
			{
				f.mkdir();  // make directory or folder
			}
			
			// file copy
			 Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
			 
			 System.out.println("Quiz image is uploaded successfully");
			}
			
			
			
		}catch(Exception e)
		{
			
			 System.out.println("\n=================================================================================================================\n"
			           + "         Message: "+ e +"   \n"
			           + "==========================================================================================================================");
			
			 throw new CustomException("Exception: "+ e +"");
			 		   
		}
		return ResponseEntity.ok(this.quizServ.addQuiz(quiz));
	}
	
//======== update quiz ==========================================================================
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestParam("quizid") String quizid,
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("maxmarks") String maxmarks,
			@RequestParam("numofquestions") String numofquestions,
			@RequestParam(name ="active" ,required=false) boolean active,
			@RequestParam("catid") String catid,
			@RequestParam(name="imagefile",required=false) MultipartFile file) throws Exception
	{
		Quiz quiz = new Quiz();
		
		quiz.setQuizid(Integer.parseInt(quizid));
		
		String title2= title.substring(1,title.length()-1);
		String title3 = title2.replaceAll("\\\\", "");
		
		String description2 = description.substring(1,description.length()-1);
		String description3 = description2.replaceAll("\\\\","");
		
		quiz.setTitle(title3);
		quiz.setDescription(description3);
		
		quiz.setMaxmarks(Integer.parseInt(maxmarks));
	
		if(active== true || active== false)
			quiz.setActive(active);
		
		quiz.setNumofquestions(Integer.parseInt(numofquestions));
		
		quiz.setCategory(this.catServ.getCategory(Integer.parseInt(catid)));
		
		
		Quiz oldQuiz = this.quizServ.getQuiz(quiz.getQuizid());		
		
		ArrayList <String> ext=new ArrayList<>();
		ext.add(".jpg");ext.add(".bmp");ext.add(".jpeg");ext.add(".png");ext.add(".webp");
		
		try {
			
			
			if(file == null || file.isEmpty()) 
			{
				System.out.println("File is empty");
				quiz.setImage(oldQuiz.getImage());
			}
			else {
				// delete the old image and
				// upload the new image file to folder 
				// and update the name to quiz table
				
				// path
				String path="src/main/resources/static/image";
				
				// deleting old image
				
				// Fullpath of old image
					
				if(!oldQuiz.getImage().equals("Quiz.png"))
				{
					try {
						String oldFilePath = path+File.separator+oldQuiz.getImage();
						Files.delete(Paths.get(oldFilePath));
						System.out.println("Old image deleted successfully...");
						
					}catch(Exception e)
					{
	
						 System.out.println("\n=================================================================================================================\n"
						           + "         Message: "+e+"  \n"
						           + "==========================================================================================================================");
						
					}
				}
				// adding new image
				String imageName = file.getOriginalFilename();
				
				String	fileExtension = imageName.substring(imageName.lastIndexOf("."));
				
				
				if(!ext.contains(fileExtension))
				{
					 System.out.println("\n=================================================================================================================\n"
					           + "         Message: Unsupported format, Please try to upload image file with Extensions (jpg,jpeg,png,bmp,webp) only   \n"
					           + "==========================================================================================================================");
					
					 throw new CustomException("Exception: Unsupported format, Please try to upload image file with Extensions (jpg,jpeg,png,bmp,webp) only ");
					 		
				}
				
				
				String newImageName = quiz.getTitle().replaceAll("[^a-zA-Z0-9]","");
				
				String newImgName = newImageName.concat(fileExtension);
				
				System.out.println("newImgName: "+newImgName);
				quiz.setImage(newImgName);
				
			
				
			// Fullpath
			String filePath = path+File.separator+newImgName;
		
			//create folder if not created
			File f = new File(path);
			if(!f.exists())
			{
				f.mkdir();  // make directory or folder
			}
			
			// file copy
			 Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
			 
			 System.out.println("Quiz image is uploaded successfully");
			}
			
			
			
		}catch(Exception e)
		{
			
			 System.out.println("\n=================================================================================================================\n"
			           + "         Message: "+ e +"   \n"
			           + "==========================================================================================================================");
			
			 throw new CustomException("Exception: "+ e +"");
			 		   
		}
		return ResponseEntity.ok(this.quizServ.addQuiz(quiz));
	}
	
	
//========= get all quizzes ==========================================================================
	@GetMapping("/all")
	public ResponseEntity<Set<Quiz>> getquizzes() throws IOException, CustomException
	{
		String filePath = "src/main/resources/static/image/";
	//	String filePath = context.getRealPath("/static/image");
		File fileFolder = new File(filePath);
		
		if(fileFolder != null)
		{
			for(Quiz q : this.quizServ.getQuizzes())
			{
				File file = new File(filePath+q.getImage());
				
					if(file.exists())
					{
				
						String encodeBase64 = null;
						 try {
							 
							 String extension =FilenameUtils.getExtension(q.getImage());
							 FileInputStream fileInputStream = new FileInputStream(file);
							 byte[] bytes = new byte[(int)file.length()];
							 fileInputStream.read(bytes);
							 encodeBase64 = Base64.getEncoder().encodeToString(bytes);
							 q.setImagefile("data:image/"+extension+";base64,"+encodeBase64);
							 fileInputStream.close();
							 
							 
						 }catch (Exception e) {
							 
							 System.out.println("\n=================================================================================================================\n"
							           + "         Message: "+ e +"   \n"
							           + "==========================================================================================================================");
							
							 throw new CustomException("Exception: "+ e +"");
						
						}
					}
				}
			
		}
		
		return ResponseEntity.ok(this.quizServ.getQuizzes());
	}
	
//======== get single quiz ===================================================================================
	@GetMapping("/quizid/{quizId}")
	public ResponseEntity<?> getQuiz(@PathVariable("quizId") int quizId) throws CustomException
	{
		String filePath = "src/main/resources/static/image/";
		//	String filePath = context.getRealPath("/static/image");
			File fileFolder = new File(filePath);
			
			if(fileFolder != null)
			{
				Quiz q = this.quizServ.getQuiz(quizId);
				
					File file = new File(filePath+q.getImage());
					
					if(file.exists())
					{
					
						String encodeBase64 = null;
						 try {
							 
							 String extension =FilenameUtils.getExtension(q.getImage());
							 FileInputStream fileInputStream = new FileInputStream(file);
							 byte[] bytes = new byte[(int)file.length()];
							 fileInputStream.read(bytes);
							 encodeBase64 = Base64.getEncoder().encodeToString(bytes);
							 q.setImagefile("data:image/"+extension+";base64,"+encodeBase64);
							 fileInputStream.close();
							 
							 
						 }catch (Exception e) {
							 
							 System.out.println("\n=================================================================================================================\n"
							           + "         Message: "+ e +"   \n"
							           + "==========================================================================================================================");
							
							 throw new CustomException("Exception: "+ e +"");
						
						}
						 
					}	 
				}
				
			
		
		return ResponseEntity.ok(this.quizServ.getQuiz(quizId));
	}
	
//========= delete quiz =============================================================================
	@DeleteMapping("/delete/{quizId}")
	public void delete(@PathVariable("quizId") int quizId) throws Exception
	{
		Quiz quiz = this.quizServ.getQuiz(quizId);	
		if(!quiz.getImage().equals("Quiz.png"))
		{
			try {
//				File imagefile = new ClassPathResource("static/image").getFile();
//				Path path = Paths.get(imagefile.getAbsolutePath()+ File.separator+quiz.getImage());
//				Files.delete(path);
				String path="src/main/resources/static/image";
				String FilePath = path+File.separator+quiz.getImage();
				
				File file = new File(FilePath);
				if(file.exists())
				{
					Files.delete(Paths.get(FilePath));
				}
				
			} catch (IOException e) {
				 System.out.println("\n=================================================================================================================\n"
				           + "         Message: "+ e +"   \n"
				           + "==========================================================================================================================");
				
				 throw new CustomException("Exception: "+ e +"");
			}
			
		}
		
		
		this.quizServ.deleteQuiz(quizId);
	}
	
//===== get quizzes of category ============================================================================================	
	
	@GetMapping("/quizzes/{categoryId}")
	public ResponseEntity<?> getQuizzesOfCategory(@PathVariable("categoryId") int categoryId) throws CustomException
	{
		String filePath = "src/main/resources/static/image/";
		//	String filePath = context.getRealPath("/static/image");
			File fileFolder = new File(filePath);
			
			if(fileFolder != null)
			{
				for(Quiz q : this.quizServ.getQuizzes())
				{
					File file = new File(filePath+q.getImage());
					
					if(file.exists())
					{
					
						String encodeBase64 = null;
						 try {
							 
							 String extension =FilenameUtils.getExtension(q.getImage());
							 FileInputStream fileInputStream = new FileInputStream(file);
							 byte[] bytes = new byte[(int)file.length()];
							 fileInputStream.read(bytes);
							 encodeBase64 = Base64.getEncoder().encodeToString(bytes);
							 q.setImagefile("data:image/"+extension+";base64,"+encodeBase64);
							 fileInputStream.close();
							 
							 
						 }catch (Exception e) {
							 
							 System.out.println("\n=================================================================================================================\n"
							           + "         Message: "+ e +"   \n"
							           + "==========================================================================================================================");
							
							 throw new CustomException("Exception: "+ e +"");
						
						}
						 
						}	 
					}
				
			}
		return ResponseEntity.ok(this.quizServ.getQuizzesOfCategory(categoryId));
	}
	
//========= get all active quizzes ==========================================================================
		@GetMapping("/allactive")
		public ResponseEntity<Set<Quiz>> getAllquizzes() throws IOException, CustomException
		{
			String filePath = "src/main/resources/static/image/";
		//	String filePath = context.getRealPath("/static/image");
			File fileFolder = new File(filePath);
			
			if(fileFolder != null)
			{
				for(Quiz q : this.quizServ.getQuizzes())
				{
					File file = new File(filePath+q.getImage());
					
					if(file.exists())
					{
					
						String encodeBase64 = null;
						 try {
							 
							 String extension =FilenameUtils.getExtension(q.getImage());
							 FileInputStream fileInputStream = new FileInputStream(file);
							 byte[] bytes = new byte[(int)file.length()];
							 fileInputStream.read(bytes);
							 encodeBase64 = Base64.getEncoder().encodeToString(bytes);
							 q.setImagefile("data:image/"+extension+";base64,"+encodeBase64);
							 fileInputStream.close();
							 
							 
						 }catch (Exception e) {
							 
							 System.out.println("\n=================================================================================================================\n"
							           + "         Message: "+ e +"   \n"
							           + "==========================================================================================================================");
							
							 throw new CustomException("Exception: "+ e +"");
						
						}
						 
						}	 
					}
				
			}
			
			return ResponseEntity.ok(this.quizServ.getActiveQuizzes(true));
		}
		
//===== get active quizzes of category ============================================================================================	
		
		@GetMapping("/quizzesactive/{categoryId}")
		public ResponseEntity<?> getActiveQuizzesOfCategory(@PathVariable("categoryId") int categoryId) throws CustomException
		{
			String filePath = "src/main/resources/static/image/";
			//	String filePath = context.getRealPath("/static/image");
				File fileFolder = new File(filePath);
				
				if(fileFolder != null)
				{
					for(Quiz q : this.quizServ.getQuizzes())
					{
						File file = new File(filePath+q.getImage());
						
						if(file.exists())
						{
						
							String encodeBase64 = null;
							 try {
								 
								 String extension =FilenameUtils.getExtension(q.getImage());
								 FileInputStream fileInputStream = new FileInputStream(file);
								 byte[] bytes = new byte[(int)file.length()];
								 fileInputStream.read(bytes);
								 encodeBase64 = Base64.getEncoder().encodeToString(bytes);
								 q.setImagefile("data:image/"+extension+";base64,"+encodeBase64);
								 fileInputStream.close();
								 
								 
							 }catch (Exception e) {
								 
								 System.out.println("\n=================================================================================================================\n"
								           + "         Message: "+ e +"   \n"
								           + "==========================================================================================================================");
								
								 throw new CustomException("Exception: "+ e +"");
							
							}
							 
						  }	 
						}
					
				}
			return ResponseEntity.ok(this.quizServ.getActiveQuizzesOfCategory(categoryId,true));
		}
		

//=======================================================================================================
	
	
}
