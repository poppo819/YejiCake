package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDao extends SuperDao {
	public Member LoginCheck(String id, String password) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select * from members " ; 
		sql += " where mid = ? and password = ? " ; 
		Member member = null ;
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery() ;
			
			if (rs.next()) {
				member = new Member();
				member.setMpoint( rs.getInt("mpoint") ); 
				member.setId( rs.getString("id") );
				member.setName( rs.getString("name") );
				member.setPassword( rs.getString("password") );
				member.setPhone( rs.getString("phone") ); 
				member.setAddress( rs.getString("address") ); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return member;
	}	
	
	public Member SelectByPk(String id) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select * from members " ; 
		sql += " where mid = ? " ; 
		Member member = null ;
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString(1, id);
			rs = pstmt.executeQuery() ;
			
			if (rs.next()) {
				member = new Member();
				member.setMpoint( rs.getInt("mpoint") ); 
				member.setId( rs.getString("id") );
				member.setName( rs.getString("name") );
				member.setPassword( rs.getString("password") );
				member.setPhone( rs.getString("phone") ); 
				member.setAddress( rs.getString("address") ); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return member;
	}
	
	public List<Member> SelectAll(int beginRow, int endRow) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select mid, name, password, phone, address, mpoint " ; 
		sql += " from ( select mid, name, password, phone, address, mpoint, rank() over(order by mid desc) as ranking from members ) " ; 
		sql += " where ranking between ? and ? " ; 
		
		List<Member> lists = new ArrayList<Member>();
		
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setInt(1, beginRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery() ;
			
			while(rs.next()) {
				Member member = new Member();
				member.setMpoint( rs.getInt("mpoint") ); 
				member.setId( rs.getString("id") );
				member.setName( rs.getString("name") );
				member.setPassword( rs.getString("password") );
				member.setPhone( rs.getString("phone") ); 
				member.setAddress( rs.getString("address") ); 
				
				lists.add(member) ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ){rs.close();}
				if( pstmt != null ){pstmt.close();}
				if( conn != null ){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return lists;
	}
	
	public List<Member> SelectAll() {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select * from members " ;
		List<Member> lists = new ArrayList<Member>();
		
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			rs = pstmt.executeQuery() ;
			
			while(rs.next()) {
				Member member = new Member();
				
				member.setMpoint( rs.getInt("mpoint") ); 
				member.setId( rs.getString("id") );
				member.setName( rs.getString("name") );
				member.setPassword( rs.getString("password") );
				member.setPhone( rs.getString("phone") ); 
				member.setAddress( rs.getString("address") ); 
				
				lists.add(member) ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ){rs.close();}
				if( pstmt != null ){pstmt.close();}
				if( conn != null ){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return lists;
	}
	
	public int GetCount() {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		int cnt = -1; 
		String sql = " select count(*) as cnt from members " ;
		
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			rs = pstmt.executeQuery() ;
			
			if(rs.next()){
				cnt = rs.getInt("cnt") ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt;
	}
	
	public int DeleteData(String id) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		
		String sql = " delete from members " ;
		sql += " where mid = ? " ;
		int cnt = -1 ;
		try {
			conn = super.getConnection() ;
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql) ;
			
			pstmt.setString(1, id);
			
			cnt = pstmt.executeUpdate() ;
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}
	
	public int UpdateData(Member bean) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		String sql = "update members set name=?, password=?, phone=?, address=?, mpoint=?" ;
		sql += " where mid = ? "  ;
		int cnt = -1 ; 
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getPassword());
			pstmt.setString(3, bean.getPhone());
			pstmt.setString(4, bean.getAddress());
			pstmt.setInt(5, bean.getMpoint());
			pstmt.setString(6, bean.getId());
			
			cnt = pstmt.executeUpdate() ;
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}finally {
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}
	
	public int InsertData(Member bean) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		
		String sql = " insert into members(mid, name, password, phone, address, mpoint)" ;
		sql += " values(?, ?, ?, ?, ?, ?)" ;
		int cnt = -1 ;
		try {
			conn = super.getConnection() ;
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql) ;

			pstmt.setString(1, bean.getId()); 
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getPassword());
			pstmt.setString(4, bean.getPhone());
			pstmt.setString(5, bean.getAddress());
			pstmt.setInt(6, bean.getMpoint());
			
			cnt = pstmt.executeUpdate() ;
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}
		
		public MemberDao() {
			
		}
	}
