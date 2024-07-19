package com.shinhan.gath.service;

import java.util.List;

import com.shinhan.gath.dao.MeetingDAO;
import com.shinhan.gath.dto.AttendMeetDTO;
import com.shinhan.gath.dto.MeetingDTO;

public class MeetingService {
	
	MeetingDAO meetDao = new MeetingDAO();
	
	public List<MeetingDTO> findByGNo (String id, int gNo) {
		return meetDao.findByGNo(id, gNo);
	}
	
	public MeetingDTO selMetDto(List<MeetingDTO> metArr, int sel) {
		MeetingDTO metDto = metArr.get(sel-1);
		return metDto;
	}
	
	public AttendMeetDTO selAttMetDto(List<AttendMeetDTO> attMetArr, int sel) {
		AttendMeetDTO attMetDto = attMetArr.get(sel-1);
		return attMetDto;
	}
	
	public int joinMeet(MeetingDTO metDto, String id) {
		int result = 0;
		int joinCnt = meetDao.joinCount(metDto.getMetNo());
		if(metDto.getMetMaxMem() <= joinCnt) {
			result = -1;
			return result;
		}
		result = meetDao.joinMeet(metDto.getMetNo(), id);
		return result;
	}
	
	public int withdrawMeet(int metNo, String id) {
		return meetDao.withdrawMeet(metNo, id);
	}
	
	public List<AttendMeetDTO> attendMet(int gNo, String id) {
		return meetDao.attendMeet(gNo, id);
	}
	
	public int insMeet(MeetingDTO metDto) {
		return meetDao.insMeet(metDto);
	}
	
	public List<MeetingDTO> yetAttMeet(int gNo, String id) {
		return meetDao.yetAttMeet(gNo, id);
	}
	
	public List<MeetingDTO> findByGNoDel (int gNo) {
		return meetDao.findByGNoDel(gNo);
	}
	
	public void delMeet (MeetingDTO metDto) {
		int mNo = metDto.getMetNo();
		int result = meetDao.delMeet(mNo);
		if (result != 0) {
			System.out.println(metDto.getMetTitle() + "의 모임이 삭제되었습니다.");
		} else {
			System.out.println(metDto.getMetTitle() + "의 삭제를 실패했습니다..");
		}
	}
	
	public List<Integer> meetDays(String id, String month) {
		return meetDao.meetDays(id, month);
	}
	
	public List<MeetingDTO> toDoMeetList(String meetDay, String id) {
		return meetDao.toDoMeetList(meetDay, id);
	}
}
