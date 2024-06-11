package kr.or.ddit.member.dao;

import java.util.List;

import kr.or.ddit.member.vo.MemberVO;

/**
 * 실제 DB와 연결해서 SQL 문을 수행하여 결과를 작성해 서비스에 전달하기 위ㅏㄴ
 * DAO Interface
 * @author 6
 *
 */
public interface IMemberDao {
	/**
	 * MemberVO에 담겨진 회원정보를 DB에 Insert하기 위한 메서드
	 * @param mv 회원정보를 담은 MemberVO 객체
	 * @return DB 작업이 성공하면 1, 실패하면 0 반환됨
	 */
	public int insertMember(MemberVO mv);
	
	/**
	 * MemberVO에 담겨진 회원정보를 DB에 Update하기 위한 메서드
	 * @param mv 회원정보를 담은 MemberVO 객체
	 * @return DB 작업이 성공하면 1, 실패하면 0 반환됨
	 */
	public int updateMember(MemberVO mv);
	
	/**
	 * 해당 회원의 존재여부를 확인하기 위한 메서드
	 * @param memId 존재 여부 확인하기 위한 회원 ID
	 * @return 해당 회원이 존재하면 true, 존재하지 않으면 false 리턴함
	 */
	public boolean checkMember(String memId);
	
	/**
	 * 해당 회원정보를 삭제하기 위한 메서드
	 * @param memId 삭제하고자 하는 회원 ID
	 * @return 삭제 처리가 성공하면 1, 실패하면 0 반환됨.
	 */
	public int deleteMember(String memId);
	
	/**
	 * DB에 존재하는 모든 회원정보를 가져오기 위한 메서드
	 * @return 모든 회원정보를 담은 List 객체
	 */
	public List<MemberVO> getAllMember();

	/**
	 * 회원정보를 검색하기 위한 메서드
	 * @param mv 검색할 회원정보를 담은 MemberVO 객체
	 * @return 검색된 회원정보를 담은 List 객체
	 */
	public List<MemberVO> searchMember(MemberVO mv);
}
