package com.service;

import java.util.List;

import com.exception.CustomException;
import com.model.outcome.Result;

public interface ResultService {
	
	public Result addResult(Result result) throws CustomException;
	
	public List<Result> getResults();
	
	public Result getResult(int resultid);
	
	public Result getCurrentResult() throws CustomException;
	
	public void deleteResult(int resultid);
	
	public void deleteAllResult();
	
	public List<Result> getResultsofUser(int uid) throws CustomException;
	
	public void deleteAllResultOfUser(int uid);

}
