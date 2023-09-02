package com.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;
import org.springframework.stereotype.Service;

import com.exception.CustomException;
import com.model.exam.Question;
import com.model.exam.Quiz;
import com.model.outcome.Result;
import com.repo.ResultRepo;
import com.service.QuestionService;
import com.service.QuizService;
import com.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService{
	
	@Autowired
	private ResultRepo resultRepo;
	
	@Autowired
	private QuestionService quesServ;
	
	@Autowired
	private QuizService quizServ;

	@Override
	public Result addResult(Result result) throws CustomException {
		
	try{
		
		Quiz quiz = this.quizServ.getQuiz(result.getQuizid());
		
		Question question;
		int correctanswers = 0;
		int length = result.getUserqnas().toArray().length;
		int attempted = 0;
		for(int i = 0; i < length; i++)
		{
			question = this.quesServ.getQuestion(result.getUserqnas().get(i).getQuesid());
			if(question.getAnswer().equals(result.getUserqnas().get(i).getAnswer()))
			{
				correctanswers ++;
			}
			
			if(!result.getUserqnas().get(i).getAnswer().equals(""))
			{
				attempted ++;
			}
		}
		
		
		result.setCorrectanswers(correctanswers);
		
		int marksgot = (int)(correctanswers * quiz.getMaxmarks()/ length);
		result.setMarksgot(marksgot);
		result.setAttempted(attempted);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		result.setDate(formatter.format(new Date()));
		
	}catch(Exception e){
		
		 System.out.println("\n=======================================================================================================================\n"
		           + "         Message: "+e+"\n"
		           + "=================================================================================================================================");
		
		 throw new CustomException("Exception: "+e);
		 
	}
		
		System.out.println("============================================================================================================================================");
		
		System.out.println("uid: " + result.getUid());
		System.out.println("quizid: " + result.getQuizid());
		System.out.println("numofquestions: " + result.getNumofquestions());
		System.out.println("correctanswers: " + result.getCorrectanswers());
		System.out.println("marksgot: " + result.getMarksgot());
		System.out.println("date: " + result.getDate());
		System.out.println("quesid: " + result.getUserqnas().get(0).getQuesid());
		System.out.println("answer: " + result.getUserqnas().get(0).getAnswer());
		System.out.println("============================================================================================================================================");

		
		return this.resultRepo.save(result);
	}

	@Override
	public List<Result> getResults() {
		return new ArrayList<>(this.resultRepo.findAll());
	}

	@Override
	public Result getResult(int resultid) {
		
		return this.resultRepo.findById(resultid).get();
	}

	@Override
	public Result getCurrentResult() throws CustomException {
		
//=====================================================================================================================================
		
		String filePath = "src/main/resources/static/image/";
		//	String filePath = context.getRealPath("/static/image");
			File fileFolder = new File(filePath);
		
		Quiz quiz;
		Result result = this.resultRepo.findCurrentResult();
		
			quiz = this.quizServ.getQuiz(result.getQuizid());
			result.setTitle(quiz.getTitle());
			
			
			File file = new File(filePath+quiz.getImage());
			
			if(file.exists())
			{
		
				String encodeBase64 = null;
				 try {
					 
					 String extension =FilenameUtils.getExtension(quiz.getImage());
					 FileInputStream fileInputStream = new FileInputStream(file);
					 byte[] bytes = new byte[(int)file.length()];
					 fileInputStream.read(bytes);
					 encodeBase64 = Base64.getEncoder().encodeToString(bytes);
					 quiz.setImagefile("data:image/"+extension+";base64,"+encodeBase64);
					 fileInputStream.close();
					 
					 
				 }catch (Exception e) {
					 
					 System.out.println("\n=================================================================================================================\n"
					           + "         Message: "+ e +"   \n"
					           + "==========================================================================================================================");
					
					 throw new CustomException("Exception: "+ e +"");
				 }
			
		
			result.setImagefile(quiz.getImagefile());
		};
		

		
		
//======================================================================================================================================		
		return this.resultRepo.findCurrentResult();
	}

	@Override
	public void deleteResult(int resultid) {
		
		 this.resultRepo.deleteById(resultid);
	}

	@Override
	public void deleteAllResult() {
			
		this.resultRepo.deleteAll();
	}

	@Override
	public List<Result> getResultsofUser(int uid) throws CustomException {
		
		String filePath = "src/main/resources/static/image/";
		//	String filePath = context.getRealPath("/static/image");
			File fileFolder = new File(filePath);
		
		Quiz quiz;
		List<Result> results = this.resultRepo.findByUid(uid);
		int length = results.toArray().length;
		
		for(int i = 0 ; i< length;i++)
		{
			quiz = this.quizServ.getQuiz(results.get(i).getQuizid());
			results.get(i).setTitle(quiz.getTitle());
			
			
			
			
			File file = new File(filePath+quiz.getImage());
			
			if(file.exists())
			{
		
				String encodeBase64 = null;
				 try {
					 
					 String extension =FilenameUtils.getExtension(quiz.getImage());
					 FileInputStream fileInputStream = new FileInputStream(file);
					 byte[] bytes = new byte[(int)file.length()];
					 fileInputStream.read(bytes);
					 encodeBase64 = Base64.getEncoder().encodeToString(bytes);
					 quiz.setImagefile("data:image/"+extension+";base64,"+encodeBase64);
					 fileInputStream.close();
					 
					 
				 }catch (Exception e) {
					 
					 System.out.println("\n=================================================================================================================\n"
					           + "         Message: "+ e +"   \n"
					           + "==========================================================================================================================");
					
					 throw new CustomException("Exception: "+ e +"");
				
				}
			}
			results.get(i).setImagefile(quiz.getImagefile());
		};
		

		
		return results;
	}

	@Override
	public void deleteAllResultOfUser(int uid) {
		
	 this.resultRepo.deleteByUid(uid);
		
	}

}
