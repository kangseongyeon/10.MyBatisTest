package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.member.dao.IMemberDao;
import kr.or.ddit.member.dao.MemberDaoImpl;
import kr.or.ddit.member.vo.MemberVO;

public class MemberServiceImpl implements IMemberService{

	private static IMemberService memService = new MemberServiceImpl(); 
	private IMemberDao memDao;
	
	private MemberServiceImpl() {
		memDao = MemberDaoImpl.getInstance();
	}
	
	public static IMemberService getInstance() {
		return memService;
	}
	
	@Override
	public int registerMember(MemberVO mv) {
		int cnt = memDao.insertMember(mv);
		
		// 회원에게 가입 완료 메일 발송하기...
		return cnt;
	}

	@Override
	public int modifyMember(MemberVO mv) {
		int cnt = memDao.updateMember(mv);
		return cnt;
	}

	@Override
	public boolean checkMember(String memId) {
		
		return memDao.checkMember(memId);
	}

	@Override
	public int removeMember(String memId) {
		int cnt = memDao.deleteMember(memId);
		return cnt;
	}

	@Override
	public List<MemberVO> getTotalMember() {
		List<MemberVO> memList = memDao.getAllMember();
		return memList;
	}

	@Override
	public List<MemberVO> searchMember(MemberVO mv) {
		
		return memDao.searchMember(mv);
	}

}
