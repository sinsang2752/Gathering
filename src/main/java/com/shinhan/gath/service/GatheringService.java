package com.shinhan.gath.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shinhan.gath.dao.GatheringDAO;
import com.shinhan.gath.dto.AttendingMeetDTO;
import com.shinhan.gath.dto.CategoryDTO;
import com.shinhan.gath.dto.DetailGatheringDTO;
import com.shinhan.gath.dto.GatheringCnoDTO;
import com.shinhan.gath.dto.GatheringDTO;
import com.shinhan.gath.dto.GathmemberDTO;
import com.shinhan.gath.dto.HotGathDTO;
import com.shinhan.gath.dto.MeetingDTO;
import com.shinhan.gath.dto.ResAttendGathDTO;

public class GatheringService {
	
	GatheringDAO gathDao = new GatheringDAO();
	MeetingService meetService = new MeetingService();
	
	public void findAllCategory(HttpServletRequest request) {
		List<CategoryDTO> cateList = gathDao.findAllCategory();
		List<CategoryDTO> firstCol = new ArrayList<CategoryDTO>();
		List<CategoryDTO> secondCol = new ArrayList<CategoryDTO>();
		int size = cateList.size();
		if(cateList.size() % 2 == 0) {
			for(int i = 0; i < size / 2; i++) {
				firstCol.add(cateList.get(i));
			}
			for(int i = size / 2; i < size; i++) {
				secondCol.add(cateList.get(i));
			}
		} else {
			for(int i = 0; i < size / 2 + 1; i++) {
				firstCol.add(cateList.get(i));
			}
			for(int i = size / 2 + 1; i < size; i++) {
				secondCol.add(cateList.get(i));
			}
		}
		request.setAttribute("firstCol", firstCol);
		request.setAttribute("secondCol", secondCol);
	}
	
	public List<GatheringCnoDTO> findByCNo(int sel) {
		return gathDao.findByCNo(sel);
	}
	
	public int joinGathering(String id, int gathSel) {
		return gathDao.joinGathering(id, gathSel);
	}
	
	public int createGath(GatheringDTO gathDto) {
		return gathDao.createGath(gathDto);
	}
	public List<ResAttendGathDTO> attendGath(String id) {
		return gathDao.attendGath(id);
	}
	
	public GatheringDTO findByGNo(int gNo) {
		return gathDao.findByGNo(gNo);
	}
	
	public DetailGatheringDTO detailGath(String id,int gNo) {
		DetailGatheringDTO detailGathDto = new DetailGatheringDTO();
		
		GatheringDTO gathDto = findByGNo(gNo);
		
		List<MeetingDTO> meetArr = meetService.findByGNo(id, gNo);
		
		detailGathDto.setGNo(gathDto.getGNo());
		detailGathDto.setGTitle(gathDto.getGTitle());
		detailGathDto.setGIntro(gathDto.getGIntro());
		detailGathDto.setMeetArr(meetArr);
		detailGathDto.setMgrId(gathDto.getMgrId());
		
		return detailGathDto;
	}
	
	public List<GatheringDTO> findByMgrid(String id) {
		return gathDao.findByMgrid(id);
	}
	
	public GatheringDTO selGathDto(List<GatheringDTO> gathArr, int sel) {
		GatheringDTO gathDto = gathArr.get(sel-1);
		return gathDto;
	}
	
	public int delGath(int gNo) {
		return gathDao.delGath(gNo);
	}
	
	public int updateGath(GatheringDTO gathDto) {
		return gathDao.updateGath(gathDto);
	}
	public int updateGathNoImg (GatheringDTO gathDto) {
		return gathDao.updateGathNoImg(gathDto);
	}
	public int withdrawGath(String id, int gNo) {
		return gathDao.withdrawGath(id, gNo);
	}
	
	public List<AttendingMeetDTO> atMet(String id) {
		return gathDao.atMet(id);
	}
	
	public void insCate(String name, String id) {
		int result = gathDao.insCate(name, id);
		String str = result == 0? "카테고리가 생성에 실패했습니다." : "카테고리 생성되었습니다.";
		System.out.println(str);
	}
	
	public void delCate(int cNo) {
		int result = gathDao.delCate(cNo);
		String str = result == 0? "카테고리 삭제에 실패했습니다." : "카테고리가 삭제되었습니다.";
		System.out.println(str);
	}
	
	public List<HotGathDTO> hotGathList() {
		List<HotGathDTO> list = gathDao.hotGathList();
		int listSize = list.size();
		if(list.size() < 7) {
			for(int i = listSize; i < 7; i++) {
				list.add(list.get(i - listSize));
			}
		}
		return list;
	}
	
	public List<GathmemberDTO> gathJoinList(int gNo) {
		return gathDao.gathJoinList(gNo);
	}
	
	public int joinCheck(String id, List<GathmemberDTO> gathList) {
		int result = 0;
		for(GathmemberDTO mem : gathList) {
			if(mem.getId().equals(id)) return 1;
		}
		return result;
	}
	
	public List<CategoryDTO> joinCateList(String id) {
		return gathDao.joinCateList(id);
	}
	
	public List<HotGathDTO> joinGathList(String id, int cNo) {
		return gathDao.joinGathList(id, cNo);
	}
	
	public List<HotGathDTO> joinGathListAll(String id) {
		return gathDao.joinGathListAll(id);
	}
	
	
}
