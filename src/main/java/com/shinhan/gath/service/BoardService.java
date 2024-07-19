package com.shinhan.gath.service;

import java.util.List;

import com.shinhan.gath.dao.BoardDAO;
import com.shinhan.gath.dto.BoardDTO;
import com.shinhan.gath.dto.BoardfileDTO;
import com.shinhan.gath.util.FileUtil;

public class BoardService {
	
	BoardDAO boardDao = new BoardDAO();
	
	public List<BoardDTO> findByGNo(int gNo) {
		return boardDao.findByGNo(gNo);
	}
	
	public BoardDTO selBoardDto(List<BoardDTO> boardArr, int sel) {
		BoardDTO boardDto = boardArr.get(sel-1);
		return boardDto;
	}
	
	public BoardDTO insBoard(BoardDTO boardDto) {
		int result = boardDao.insBoard(boardDto);
		String str = result == 0? "게시판 생성에 실패했습니다." : "게시판이 생성되었습니다.";
		System.out.println(str);
		boardDto.setBNo(boardDao.maxBno());
		return boardDto;
	}
	
	public void delBoard(BoardDTO boardDto) {
		int result = boardDao.delBoard(boardDto.getBNo());
		String str = result == 0? "게시판 삭제에 실패했습니다." : "게시판이 삭제되었습니다.";
		System.out.println(str);
	}
	
	public void fileUpload(BoardDTO boardDto, String fileName) {
		BoardfileDTO fileDto = new BoardfileDTO();
		fileDto.setBNo(boardDto.getBNo());
		fileDto.setFName(fileName);
		//업로드 구현
		FileUtil.fileUpload(fileName);
		boardDao.insFile(fileDto);	//DAO
		
	}
}
